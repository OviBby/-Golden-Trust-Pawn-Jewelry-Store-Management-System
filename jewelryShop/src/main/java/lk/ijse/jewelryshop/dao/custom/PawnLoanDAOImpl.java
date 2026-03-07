package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.PawnLoanDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PawnLoanDAOImpl implements PawnLoanDAO {

    @Override
    public String generateId(String loanType) throws SQLException {
        String prefix;
        switch (loanType.toUpperCase()) {
            case "A": prefix = "A"; break;
            case "D": prefix = "D"; break;
            default: throw new IllegalArgumentException("Invalid loan type: " + loanType);
        }

        ResultSet resultSet = CrudUtil.execute(
                "SELECT loan_id FROM pawnloan_item_details WHERE " +
                        "loan_id REGEXP '^" + prefix + "[0-9]+$' " +
                        "ORDER BY CAST(SUBSTRING(loan_id, 2) AS UNSIGNED) DESC LIMIT 1"
        );

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

    @Override
    public boolean savePawnLoanItem(PawnLoan pawnLoanDTO) throws SQLException {

        return CrudUtil.execute(
                "INSERT INTO pawnloan_item_details (loan_id, pawn_type, item_name, start_date, weight, price, carat_of_gold, loan_months) VALUES (?,?,?,?,?,?,?,?)",
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

    @Override
    public boolean savePawnLoanDetails(PawnLoan pawnLoanDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO pawnLoanReceipt(receipt_id, customer_name, nic_number, phone_number, address, total_weight, total_amount, stamp_fees, current_interest, net_value) VALUES (?,?,?,?,?,?,?,?,?,?)",
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

    @Override
    public String generateId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(PawnLoan dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PawnLoan dto) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<PawnLoan> getAll() throws SQLException {
        ArrayList<PawnLoan> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM pawnloan_item_details");
        while (rs.next()) {
            PawnLoan pawnLoan = new PawnLoan(
                    rs.getString("pawn_type"),
                    rs.getString("item_name"),
                    rs.getDouble("weight"),
                    rs.getString("carat_of_gold"),
                    rs.getDouble("price")
            );
            list.add(pawnLoan);
        }
        return list;
    }

    @Override
    public CustomerDTO getCustomerDetails(String nic) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT first_name, last_name, phone_number, address FROM Customer WHERE nic_number = ?", nic
        );
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

    @Override
    public CustomerDTO getCustomerDetail(String phone) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT first_name, last_name, nic_number, address FROM Customer WHERE phone_number = ?", phone
        );
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
}