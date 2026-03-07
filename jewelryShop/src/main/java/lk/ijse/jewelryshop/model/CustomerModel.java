/*
package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class CustomerModel {


    public static String generateNextCustomerId() throws SQLException {
        String sql = "SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("C", "")) + 1;
            return String.format("C%03d", num);
        }
        return "C001";
    }


    public static boolean saveCustomer(CustomerDTO dto) throws SQLException {
        dto.setCustomerId(generateNextCustomerId());

        String sql = "INSERT INTO Customer (customer_id, first_name, last_name, nic_number, phone_number, address, email) VALUES (?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                dto.getCustomerId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getNicNumber(),
                dto.getPhoneNumber(),
                dto.getAddress(),
                dto.getEmail()
        );
    }


    public static CustomerDTO searchCustomer(String value) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE nic_number = ? OR phone_number = ?";

        ResultSet rs = CrudUtil.execute(sql, value, value);

        if (rs.next()) {
            return new CustomerDTO(
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


    public static boolean updateCustomer(CustomerDTO dto) throws SQLException {
        String sql = "UPDATE Customer SET first_name=?, last_name=?, nic_number=?, phone_number=?, address=?, email=? WHERE customer_id=?";

        return CrudUtil.execute(sql,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getNicNumber(),
                dto.getPhoneNumber(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getCustomerId()
        );
    }


    public static boolean deleteCustomer(String id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        return CrudUtil.execute(sql, id);
    }


    public static ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY customer_id";

        ResultSet rs = CrudUtil.execute(sql);

        while (rs.next()) {
            list.add(new CustomerDTO(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            ));
        }
        return list;
    }


    public static CustomerDTO getCustomerById(String customerId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";

        ResultSet rs = CrudUtil.execute(sql, customerId);

        if (rs.next()) {
            return new CustomerDTO(
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


    public static boolean isCustomerExists(String nicNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE nic_number = ?";
        ResultSet rs = CrudUtil.execute(sql, nicNumber);

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }


    public static boolean isPhoneExists(String phoneNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE phone_number = ?";
        ResultSet rs = CrudUtil.execute(sql, phoneNumber);

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    public static void printCustomerReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerModel.class.getResourceAsStream("/report/customerDetails.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /Reports/customerDetails.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}
*/
