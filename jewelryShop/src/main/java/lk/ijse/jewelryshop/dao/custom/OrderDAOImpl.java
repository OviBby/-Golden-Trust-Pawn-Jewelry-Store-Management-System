package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.OrderDAO;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.ItemDTO;
import lk.ijse.jewelryshop.dto.OrderDTO;
import lk.ijse.jewelryshop.entity.Order;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String generateId() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT receipt_no FROM Orders ORDER BY receipt_no DESC LIMIT 1");
        if (rs.next()) {
            String lastId = rs.getString(1);
            String numericPart = lastId.replaceAll("\\D", "");
            int num = 1;
            if (!numericPart.isEmpty()) {
                num = Integer.parseInt(numericPart) + 1;
            }
            String prefix = lastId.replaceAll("\\d", "");
            return String.format("%s%03d", prefix, num);
        }
        return "RCP001";
    }

    @Override
    public String getCustomer(String nic) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT first_name, last_name FROM Customer WHERE nic_number = ?", nic);
        if (rs.next()) {
            return rs.getString("first_name") + " " + rs.getString("last_name");
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItem(String itemId) throws SQLException {
        List<ItemDTO> itemList = new ArrayList<>();
        ResultSet rs = CrudUtil.execute(
                "SELECT description, qty, weight_grams, purity, unit_price FROM JewelryItem WHERE item_id = ?", itemId);
        while (rs.next()) {
            itemList.add(new ItemDTO(
                    rs.getString("description"),
                    rs.getInt("qty"),
                    rs.getDouble("weight_grams"),
                    rs.getString("purity"),
                    rs.getDouble("unit_price")
            ));
        }
        return itemList;
    }

    @Override
    public boolean save(OrderDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean save(Order dto) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT qty FROM JewelryItem WHERE item_id = ?", dto.getItem_id());

        if (rs.next()) {
            int currentStock = rs.getInt("qty");
            if (currentStock < dto.getQty()) {
                throw new SQLException(
                        "Insufficient stock! Available: " + currentStock +
                                ", Requested: " + dto.getQty()
                );
            }
        } else {
            throw new SQLException("Item not found: " + dto.getItem_id());
        }

        boolean orderSaved = CrudUtil.execute(
                "INSERT INTO Orders (receipt_no, item_id, item_name, weight, qty, unit_price, subtotal, order_date, customer_name, nic_number) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                dto.getOrderId(),
                dto.getItem_id(),
                dto.getDescription(),
                dto.getWeight(),
                dto.getQty(),
                dto.getUprice(),
                dto.getPrice(),
                dto.getDate(),
                dto.getCustomername(),
                dto.getNic()
        );

        // ✅ Stock අඩු කරන්න
        if (orderSaved) {
            CrudUtil.execute(
                    "UPDATE JewelryItem SET qty = qty - ? WHERE item_id = ?",
                    dto.getQty(),
                    dto.getItem_id()
            );
        }

        return orderSaved;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ArrayList<Order> orderList = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders");
        while (rs.next()) {
            orderList.add(new Order(
                    rs.getString("receipt_no"),
                    rs.getString("item_id"),
                    rs.getString("item_name"),
                    rs.getDouble("weight"),
                    rs.getInt("qty"),
                    rs.getDouble("unit_price"),
                    rs.getDouble("subtotal")
            ));
        }
        return orderList;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }

    @Override
    public void printReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerDAOImpl.class.getResourceAsStream("/report/order_A4.jrxml");
        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /report/order_A4.jrxml");
        }
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}