/*
package lk.ijse.jewelryshop.model;

import javafx.scene.control.Alert;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.util.CrudUtil;
import lk.ijse.jewelryshop.dto.ItemDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemModel {
    public static String generateNextItemId() throws SQLException {
        String sql = "SELECT item_id FROM JewelryItem ORDER BY item_id DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("I", "")) + 1;
            return String.format("I%03d", num);
        }
        return "I001";
    }

    public boolean addItem(ItemDTO dto) throws SQLException {
        String sql = "INSERT INTO JewelryItem(item_id,description,metal_type,weight_grams,purity,price,added_date,current_stock_status,qty,unit_price) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return (Boolean) CrudUtil.execute(sql,
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

    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        String sql = "SELECT * FROM JewelryItem";
        ResultSet rs = CrudUtil.execute(sql);
        ArrayList<ItemDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new ItemDTO(
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
            ));
        }
        return list;
    }

    public boolean updateItem(ItemDTO dto) throws SQLException {
        String sql = "UPDATE JewelryItem SET description=?, metal_type=?, weight_grams=?, purity=?, price=?, added_date=?, current_stock_status=?, qty=?, unit_price=? WHERE item_id=?";
        return (Boolean) CrudUtil.execute(sql,
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

    public boolean deleteItem(String itemId) throws SQLException {
        String sql = "DELETE FROM JewelryItem WHERE item_id=?";
        return (Boolean) CrudUtil.execute(sql, itemId);
    }

    public ArrayList<ItemDTO> searchItems(String query) throws SQLException {
        String sql = "SELECT * FROM JewelryItem WHERE description LIKE ?";
        ResultSet rs = CrudUtil.execute(sql, "%" + query + "%");
        ArrayList<ItemDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new ItemDTO(
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
            ));
        }
        return list;


    }


    public static void printItemReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerModel.class.getResourceAsStream("/report/itemInformation.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /Reports/itemInformation.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}
*/
