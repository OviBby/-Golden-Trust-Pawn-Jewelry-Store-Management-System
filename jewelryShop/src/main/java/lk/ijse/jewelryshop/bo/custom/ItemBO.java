package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
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

public interface ItemBO extends SuperBO {

    public  String generateId() throws SQLException ;

    public boolean save(ItemDTO dto) throws SQLException ;

    public ArrayList<ItemDTO> getAll() throws SQLException ;

    public boolean update(ItemDTO dto) throws SQLException ;


    public boolean search(ItemDTO value) throws SQLException ;


    public boolean delete(String itemId) throws SQLException ;

    public ArrayList<ItemDTO> search(String query) throws SQLException ;


    public  void printReport() throws SQLException, JRException ;
}
