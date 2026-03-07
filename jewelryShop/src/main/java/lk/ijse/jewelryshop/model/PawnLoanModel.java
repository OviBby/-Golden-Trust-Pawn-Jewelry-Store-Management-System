/*

package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.util.CrudUtil;
import net.sf.jasperreports.engine.JRResultSetDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PawnLoanModel {

    public static String generateNextPawnLoanId(String loanType) throws SQLException {
        String prefix;
        switch (loanType.toUpperCase()) {
            case "A": prefix = "A"; break;
            case "D": prefix = "D"; break;
            default: throw new IllegalArgumentException("Invalid loan type: " + loanType);
        }

        String sql = "SELECT loan_id FROM pawnloan_item_details WHERE loan_id REGEXP '^" + prefix + "[0-9]+$' ORDER BY loan_id DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);

            if (lastId != null && lastId.length() > 1) {
                try {
                    int numericPart = Integer.parseInt(lastId.substring(1));
                    numericPart++;
                    return String.format(prefix + "%03d", numericPart);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid ID format in database: " + lastId);
                }
            }
        }

        return prefix + "001";
    }

    public static boolean savePawnLoanItem(PawnLoanDTO pawnLoanDTO) throws SQLException {
        pawnLoanDTO.setLoanId(generateNextPawnLoanId(pawnLoanDTO.getPawnType()));

        String sql = "INSERT INTO pawnloan_item_details (loan_id, pawn_type, item_name, start_date, weight, price, carat_of_gold, loan_months) VALUES (?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                pawnLoanDTO.getLoanId(),
                pawnLoanDTO.getPawnType(),
                pawnLoanDTO.getItemName(),
                pawnLoanDTO.getStartDate(),
                pawnLoanDTO.getWeight(),
                pawnLoanDTO.getPricePerGram(),
                pawnLoanDTO.getGoldCarat(),
                pawnLoanDTO.getLoanMonths()
        );
    }

    public static boolean savePawnLoanDetails(PawnLoanDTO pawnLoanDTO) throws SQLException {
        String sql = "INSERT INTO pawnLoanReceipt(receipt_id,customer_name,nic_number,phone_number,address,total_weight,total_amount,stamp_fees,current_interest,net_value) VALUES (?,?,?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                pawnLoanDTO.getLoanId(),
                pawnLoanDTO.getCustomerName(),
                pawnLoanDTO.getCustomerNic(),
                pawnLoanDTO.getCustomerPhone(),
                pawnLoanDTO.getCustomerAddress(),
                pawnLoanDTO.getTotalWeight(),
                pawnLoanDTO.getLoanAmount(),
                pawnLoanDTO.getStampFee(),
                pawnLoanDTO.getInterestRate(),
                pawnLoanDTO.getNetValue()
        );
    }

    public static ArrayList<PawnLoanDTO> getAllPawnLoanItems() throws SQLException {
        ArrayList<PawnLoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM pawnloan_item_details";

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()) {
            list.add(new PawnLoanDTO(
                    rs.getString("pawn_type"),
                    rs.getString("item_name"),
                    rs.getDouble("weight"),
                    rs.getString("carat_of_gold"),
                    rs.getDouble("price")
            ));
        }
        return list;
    }

    public static CustomerDTO getCustomerDetails(String Nic) throws SQLException {
        String sql = "SELECT first_name,last_name,phone_number,address FROM Customer WHERE nic_number= ?";
        ResultSet rs = CrudUtil.execute(sql, Nic);

        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("address")
            );
        }
        return null;
    }

    public static CustomerDTO getCustomerDetail(String phone) throws SQLException {
        String sql = "SELECT first_name,last_name,nic_number,address FROM Customer WHERE  phone_number= ? ";
        ResultSet rs = CrudUtil.execute(sql, phone);

        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("nic_number"),
                    rs.getString("address")
            );
        }
        return null;
    }



}*/
