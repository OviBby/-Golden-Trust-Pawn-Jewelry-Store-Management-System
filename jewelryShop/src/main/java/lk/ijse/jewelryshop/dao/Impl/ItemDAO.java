package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
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

public interface ItemDAO extends CrudDAO<Item> {


    public boolean delete(String itemId) throws SQLException ;

    public ArrayList<Item> search(String query) throws SQLException ;

    public  void printReport() throws SQLException, JRException ;

}
