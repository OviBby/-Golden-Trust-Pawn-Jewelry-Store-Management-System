package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
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

public interface OrderDAO extends CrudDAO<Order> {

    public String getCustomer(String nic) throws SQLException ;

    public List<ItemDTO> getItem(String itemId) throws SQLException ;
    public  boolean save(OrderDTO dto) throws SQLException;
    public  ArrayList<Order> getAll() throws SQLException;
    public  void printReport() throws SQLException, JRException ;


}
