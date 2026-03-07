package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.entity.Customer;
import net.sf.jasperreports.engine.*;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {


    public boolean delete(String id) throws SQLException ;

    public  Customer search(String value) throws SQLException;

    public  boolean isCustomerExists(String nicNumber) throws SQLException ;

    public  boolean isPhoneExists(String phoneNumber) throws SQLException ;

    public  void printReport() throws SQLException, JRException ;
}
