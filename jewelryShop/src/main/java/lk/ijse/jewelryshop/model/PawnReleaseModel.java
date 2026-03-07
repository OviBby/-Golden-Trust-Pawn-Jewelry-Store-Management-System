/*

package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.dto.PawnLoanReleaseDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PawnReleaseModel {


    public static String generateNextReleaseId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT MAX(CAST(SUBSTRING(release_id, 4) AS UNSIGNED)) FROM pawn_loan_release WHERE release_id REGEXP '^REL[0-9]+$'");
        if (rs.next()) {
            int max = rs.getInt(1);
            if (max == 0) {
                return "REL001";
            }
            return "REL" + String.format("%03d", max + 1);
        } else {
            return "REL001";
        }
    }


    public static CustomerDTO getCustomerByNic(String nic) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer WHERE customer_nic = ?", nic);

        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("customer_id"),
                    rs.getString("customer_name"),

                    rs.getString("customer_nic"),
                    rs.getString("customer_phone"),
                    rs.getString("customer_address")
            );
        }
        return null;
    }





    public static CustomerDTO getCustomerByPhone(String phone) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Customer WHERE customer_phone = ?", phone);

        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("customer_id"),
                    rs.getString("customer_name"),
                    "", // last name
                    rs.getString("customer_nic"),
                    rs.getString("customer_phone"),
                    rs.getString("customer_address")
            );
        }
        return null;
    }


    public static ArrayList<PawnLoanDTO> getPawnLoanItems(String loanId) throws SQLException {
        ArrayList<PawnLoanDTO> items = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM pawnloan_item_details WHERE loan_id = ?",
                loanId
        );

        while (rs.next()) {
            PawnLoanDTO dto = new PawnLoanDTO();
            dto.setLoanId(rs.getString("loan_id"));
            dto.setItemName(rs.getString("item_name"));
            dto.setWeight(rs.getDouble("weight"));
            dto.setGoldCarat(rs.getString("carat_of_gold"));
            dto.setPricePerGram(rs.getDouble("price"));

            items.add(dto);
        }

        return items;
    }

    public static boolean isReceiptAlreadyReleased(String receiptId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT release_id FROM pawn_loan_release WHERE receipt_id = ?",
                receiptId
        );
        return rs.next();
    }




    public static boolean savePawnLoanRelease(PawnLoanReleaseDTO dto) throws SQLException {
        String sql = "INSERT INTO pawn_loan_release (\n" +
                "    release_id,\n" +
                "    receipt_id,\n" +
                "    customer_name,\n" +
                "    nic_number,\n" +
                "    phone_number,\n" +
                "    address,\n" +
                "    item_name,\n" +
                "    total_weight,\n" +
                "    total_amount,\n" +
                "    stamp_fees,\n" +
                "    current_interest,\n" +
                "    net_value,\n" +
                "    release_date\n" +
                ") VALUES (\n" +
                "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?\n" +
                ");\n";

        return CrudUtil.execute(sql,
                dto.getReleaseId(),
                dto.getReceiptId(),
                dto.getCustomerName(),
                dto.getNicNumber(),
                dto.getPhoneNumber(),
                dto.getAddress(),
                dto.getItemName(),
                dto.getTotalWeight(),
                dto.getTotalAmount(),
                dto.getStampFees(),
                dto.getCurrentInterest(),
                dto.getNetValue(),
                dto.getReleaseDate()
        );
    }
}*/
