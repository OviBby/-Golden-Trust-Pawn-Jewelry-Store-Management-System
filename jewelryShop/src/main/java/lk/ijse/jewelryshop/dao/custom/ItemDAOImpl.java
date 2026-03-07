package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.ItemDAO;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.ItemDTO;

import lk.ijse.jewelryshop.entity.Item;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
     @Override
    public String generateId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT item_id FROM JewelryItem ORDER BY item_id DESC LIMIT 1");

        if (rs != null && rs.next()) {
            String lastId = rs.getString(1);
            String numPart = lastId.replaceAll("[^0-9]", ""); 
            int num = Integer.parseInt(numPart) + 1;
            return String.format("I%03d", num);
        }
        return "I001";
    }
    @Override
    public boolean save(Item dto) throws SQLException {
        return (Boolean) CrudUtil.execute("INSERT INTO JewelryItem(item_id,description,metal_type,weight_grams,purity,price,added_date," +
                        "current_stock_status,qty,unit_price) VALUES (?,?,?,?,?,?,?,?,?,?)",
                dto.getItemId(),
                dto.getDescription(),
                dto.getMetalType(),
                dto.getWeightGrams(),
                dto.getPurity(),
                dto.getPrice(),
                dto.getAddedDate(),
                dto.getCurrentStockStatus(),
                dto.getQty(),
                dto.getUnitPrice()
        );
    }

    @Override
    public boolean update(Item dto) throws SQLException {
        return false;
    }
    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM JewelryItem");
        ArrayList<Item> list = new ArrayList<>();
        while (rs.next()) {
         Item item = new Item(

                 rs.getString("item_id"),
                 rs.getString("description"),
                 rs.getString("metal_type"),
                 rs.getDouble("weight_grams"),
                 rs.getString("purity"),
                 rs.getDouble("price"),
                 rs.getString("added_date"),
                 rs.getString("current_stock_status"),
                 rs.getInt("qty"),
                 rs.getDouble("unit_price")
         );

            list.add(item);
        }
        return list;
    }

    public boolean update(ItemDTO dto) throws SQLException {
        return (Boolean) CrudUtil.execute("UPDATE JewelryItem SET description=?, metal_type=?, weight_grams=?, purity=?, price=?," +
                        " added_date=?, current_stock_status=?, qty=?, unit_price=? WHERE item_id=?",
                dto.getDescription(),
                dto.getMetalType(),
                dto.getWeightGrams(),
                dto.getPurity(),
                dto.getPrice(),
                dto.getAddedDate(),
                dto.getCurrentStockStatus(),
                dto.getQty(),
                dto.getUnitPrice(),
                dto.getItemId()
        );
    }


    public boolean search(ItemDTO value) throws SQLException {
        return false;
    }


    @Override
    public boolean delete(String itemId) throws SQLException {
        return (Boolean) CrudUtil.execute("DELETE FROM JewelryItem WHERE item_id=?", itemId);
    }
    @Override
    public ArrayList<Item> search(String query) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM JewelryItem WHERE description LIKE ?", "%" + query + "%");
        ArrayList<Item> list = new ArrayList<>();
        while (rs.next()) {
            Item item = new Item(

                    rs.getString("item_id"),
                    rs.getString("description"),
                    rs.getString("metal_type"),
                    rs.getDouble("weight_grams"),
                    rs.getString("purity"),
                    rs.getDouble("price"),
                    rs.getString("added_date"),
                    rs.getString("current_stock_status"),
                    rs.getInt("qty"),
                    rs.getDouble("unit_price")
            );

            list.add(item);
        }
        return list;

    }

    @Override
    public  void printReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerDAOImpl.class.getResourceAsStream("/report/itemInformation.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /Reports/itemInformation.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}
