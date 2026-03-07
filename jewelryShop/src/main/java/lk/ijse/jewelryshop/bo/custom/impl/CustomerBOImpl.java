package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.CustomerBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.CustomerDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.entity.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.CUSTOMER);
    @Override
    public  String generateCustomerId() throws SQLException {
            return customerDAO.generateId();
    }
    @Override
    public  boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.save(
                new Customer(
                        dto.getCustomerId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getNicNumber(),
                        dto.getPhoneNumber(),
                        dto.getEmail()
                )
        );
    }

    @Override
    public CustomerDTO searchCustomer(String value) throws SQLException {
        Customer customer = customerDAO.search(value);

        if (customer ==null)
            return null;

        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getNicNumber(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
    }

    @Override
    public  boolean updateCustomer(CustomerDTO dto) throws SQLException {
      return customerDAO.update(
              new Customer(
                      dto.getCustomerId(),
                      dto.getFirstName(),
                      dto.getLastName(),
                      dto.getAddress(),
                      dto.getNicNumber(),
                      dto.getPhoneNumber(),
                      dto.getEmail()
              )
      );
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);

    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {
       ArrayList<Customer>list = customerDAO.getAll();
       ArrayList<CustomerDTO> listDTO = new ArrayList<>();
       for (Customer customer : list) {
           listDTO.add(new CustomerDTO(
                   customer.getCustomerId(),
                   customer.getFirstName(),
                   customer.getLastName(),
                   customer.getNicNumber(),
                   customer.getPhoneNumber(),
                   customer.getEmail()
           ));
       }
        return  listDTO;
    }


    @Override
    public  boolean isCustomerExists(String nicNumber) throws SQLException {
       return customerDAO.isCustomerExists(nicNumber);
    }

    @Override
    public  boolean isPhoneExists(String phoneNumber) throws SQLException {
       return customerDAO.isPhoneExists(phoneNumber);
    }

    @Override
    public  void printCustomerReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerDAOImpl.class.getResourceAsStream("/report/customerDetails.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /Reports/customerDetails.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}
