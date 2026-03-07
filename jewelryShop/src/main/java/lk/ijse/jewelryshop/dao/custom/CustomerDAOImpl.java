package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.CustomerDAO;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.CustomerDTO;

import lk.ijse.jewelryshop.entity.Customer;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public  String generateId() throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("C", "")) + 1;
            return String.format("C%03d", num);
        }
        return "C001";
    }


    @Override
    public  boolean save(Customer dto) throws SQLException {
        dto.setCustomerId(generateId());
        return CrudUtil.execute("INSERT INTO Customer (customer_id, first_name, last_name, nic_number, " +
                        "phone_number, address, email) VALUES (?,?,?,?,?,?,?)",
                dto.getCustomerId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getNicNumber(),
                dto.getPhoneNumber(),
                dto.getAddress(),
                dto.getEmail()
        );
    }

    @Override
    public Customer search(String value) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer WHERE nic_number = ? OR phone_number = ?"
              ,value);

        if (rs.next()) {
            return new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
        }
        return null;
    }

   @Override
    public  boolean update(Customer dto) throws SQLException {
        return CrudUtil.execute("UPDATE Customer SET first_name=?, last_name=?, nic_number=?, phone_number=?, address=?" +
                        ", email=? WHERE customer_id=?",
                dto.getFirstName(),
                dto.getLastName(),
                dto.getNicNumber(),
                dto.getPhoneNumber(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getCustomerId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Customer WHERE customer_id = ?", id);
    }

    @Override
    public  ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer ORDER BY customer_id");
        ArrayList<Customer> list = new ArrayList<>();

        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
     list.add(customer);
        }
        return list;
    }


    public  Customer getCustomerById(String customerId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer WHERE customer_id = ?", customerId);

        if (rs.next()) {
            return new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
        }
        return null;
    }

     @Override
    public  boolean isCustomerExists(String nicNumber) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM Customer WHERE nic_number = ?", nicNumber);

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public  boolean isPhoneExists(String phoneNumber) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) FROM Customer WHERE phone_number = ?", phoneNumber);

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
     @Override
    public  void printReport() throws SQLException, JRException {
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
