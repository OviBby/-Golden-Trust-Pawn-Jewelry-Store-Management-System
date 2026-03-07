/*
package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.ItemDTO;
import lk.ijse.jewelryshop.dto.OrderDTO;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT receipt_no FROM Orders ORDER BY receipt_no DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

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

    public String getCustomerName(String nic) throws SQLException {
        String customerName = null;

        String sql = "SELECT first_name, last_name FROM Customer WHERE nic_number = ?";

        ResultSet rs = CrudUtil.execute(sql, nic);

        if (rs.next()) {
            customerName = rs.getString("first_name") + " " + rs.getString("last_name");
        }

        return customerName;
    }

    public List<ItemDTO> getItemName(String itemId) throws SQLException {
        List<ItemDTO> itemList = new ArrayList<>();

        String sql = "SELECT description, qty, weight_grams, purity, unit_price " +
                "FROM JewelryItem WHERE item_id = ?";

        ResultSet rs = CrudUtil.execute(sql, itemId);

        while (rs.next()) {
            ItemDTO dto = new ItemDTO(
                    rs.getString("description"),
                    rs.getInt("qty"),
                    rs.getDouble("weight_grams"),
                    rs.getString("purity"),
                    rs.getDouble("unit_price")
            );

            itemList.add(dto);
        }

        return itemList;
    }

    public static boolean addOrder(OrderDTO dto) throws SQLException {
        String sql = "INSERT INTO Orders (item_id, item_name, weight, qty, unit_price, subtotal) VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                dto.getItem_id(),
                dto.getDescription(),
                dto.getWeight(),
                dto.getQty(),
                dto.getUprice(),
                dto.getPrice()
        );
    }

    public static ArrayList<OrderDTO> getOrders() throws SQLException {
        ArrayList<OrderDTO> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        ResultSet rs = CrudUtil.execute(sql);

        while (rs.next()) {
            orderList.add(new OrderDTO(
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

   */
/* public boolean SaveOrder(OrderDTO dto) throws SQLException {
        String sql ="INSERT INTO Orders (\n" +
                "    item_id,        -- Item code\n" +
                "    receipt_no,     -- Order ID (RCP001)\n" +
                "    qty,            -- Quantity\n" +
                "    unit_price,     -- Price per item\n" +
                "    subtotal,       -- qty * unit_price\n" +
                "    item_name,      -- Item description\n" +
                "    weight,         -- Weight in grams\n" +
                "    customer_name,  -- Customer's full name\n" +
                "    nic_number,     -- Customer's NIC\n" +
                "    order_date      -- Order date\n" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,

                dto.getOrderId(),
                dto.getItem_id(),
                dto.getQty(),
                dto.getUprice(),
                dto.getPrice(),
                dto.getDescription(),
                dto.getWeight(),
                dto.getCustomername(),
                dto.getNic(),
                dto.getDate()
                );
    }*//*


    public static void printItemReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerModel.class.getResourceAsStream("/report/order_A4.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /Reports/order_A4.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}*/
