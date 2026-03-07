package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    public  String generateCustomerId() throws SQLException ;

    public  boolean saveCustomer(CustomerDTO dto) throws SQLException ;


    public  CustomerDTO searchCustomer(String value) throws SQLException ;


    public  boolean updateCustomer(CustomerDTO dto) throws SQLException ;


    public boolean deleteCustomer(String id) throws SQLException ;

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException ;


    public  boolean isCustomerExists(String nicNumber) throws SQLException ;


    public  boolean isPhoneExists(String phoneNumber) throws SQLException ;

    public  void printCustomerReport() throws SQLException, JRException ;
}

