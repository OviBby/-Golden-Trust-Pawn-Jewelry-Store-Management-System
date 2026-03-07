package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.ItemBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.ItemDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.ItemDTO;
import lk.ijse.jewelryshop.entity.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

      ItemDAO itemDAO  = (ItemDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.ITEM);
    @Override
    public  String generateId() throws SQLException {
        return itemDAO.generateId();
    }

    @Override
    public boolean save(ItemDTO dto) throws SQLException {
        return itemDAO.save(
                new Item(
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
                )
        );
    }

    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException {
        ArrayList<Item> list = itemDAO.getAll();
        ArrayList<ItemDTO> listDTO = new ArrayList<>();

        for (Item item : list) {
            listDTO.add(new ItemDTO(
                    item.getItemId(),
                    item.getDescription(),
                    item.getMetalType(),
                    item.getWeightGrams(),
                    item.getPurity(),
                    item.getPrice(),
                    item.getAddedDate(),
                    item.getCurrentStockStatus(),
                    item.getQty(),
                    item.getUnitPrice()
            ));
        }
        return listDTO;
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException {
        return itemDAO.update(
                new Item(
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
                )
        );
    }

    @Override
    public boolean search(ItemDTO value) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String itemId) throws SQLException {
        return itemDAO.delete(itemId);
    }

    @Override
    public ArrayList<ItemDTO> search(String query) throws SQLException {
        ArrayList<Item> list = itemDAO.search(query);
        ArrayList<ItemDTO> listDTO = new ArrayList<>();

        for (Item item : list) {
            listDTO.add(new ItemDTO(
                    item.getItemId(),
                    item.getDescription(),
                    item.getMetalType(),
                    item.getWeightGrams(),
                    item.getPurity(),
                    item.getPrice(),
                    item.getAddedDate(),
                    item.getCurrentStockStatus(),
                    item.getQty(),
                    item.getUnitPrice()
            ));
        }
        return listDTO;
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
