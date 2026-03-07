package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dao.SuperDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
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

public interface OrderBO extends SuperBO {

    public String generateId() throws SQLException ;

    public String getCustomer(String nic) throws SQLException ;

    public List<ItemDTO> getItem(String itemId) throws SQLException ;

    public  boolean save(OrderDTO dto) throws SQLException ;

    public ArrayList<OrderDTO> getAll() throws SQLException ;

    public  void printReport() throws SQLException, JRException ;
}
