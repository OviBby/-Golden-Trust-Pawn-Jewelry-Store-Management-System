package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.PawnReleaseDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;
import lk.ijse.jewelryshop.entity.PawnLoanRelease;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PawnReleaseDAOImpl implements PawnReleaseDAO {

    @Override
    public String generateId() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT MAX(CAST(SUBSTRING(release_id, 4) AS UNSIGNED)) FROM pawn_loan_release WHERE release_id REGEXP '^REL[0-9]+$'"
        );
        if (rs.next()) {
            int max = rs.getInt(1);
            return max == 0 ? "REL001" : "REL" + String.format("%03d", max + 1);
        }
        return "REL001";
    }

    @Override
    public CustomerDTO getCustomerByNic(String nic) throws SQLException {

        ResultSet rs = CrudUtil.execute(
                "SELECT customer_id, first_name, last_name, nic_number, phone_number, address FROM Customer WHERE nic_number = ?", nic
        );
        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("address")
            );
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomerByPhone(String phone) throws SQLException {

        ResultSet rs = CrudUtil.execute(
                "SELECT customer_id, first_name, last_name, nic_number, phone_number, address FROM Customer WHERE phone_number = ?", phone
        );
        if (rs.next()) {
            return new CustomerDTO(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("nic_number"),
                    rs.getString("phone_number"),
                    rs.getString("address")
            );
        }
        return null;
    }

    @Override
    public ArrayList<PawnLoan> getAll(String loanId) throws SQLException {
        ArrayList<PawnLoan> items = new ArrayList<>();


        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM pawnloan_item_details WHERE loan_id = ?", loanId
        );

        while (rs.next()) {
            PawnLoan pawnLoan = new PawnLoan();
            pawnLoan.setLoanId(rs.getString("loan_id"));
            pawnLoan.setItemName(rs.getString("item_name"));
            pawnLoan.setWeight(rs.getDouble("weight"));
            pawnLoan.setGoldCarat(rs.getString("carat_of_gold"));
            pawnLoan.setPricePerGram(rs.getDouble("price"));
            items.add(pawnLoan);
        }
        return items;
    }

    @Override
    public boolean isReceiptAlreadyReleased(String receiptId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT release_id FROM pawn_loan_release WHERE receipt_id = ?", receiptId
        );
        return rs.next();
    }

    @Override
    public boolean save(PawnLoanRelease dto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO pawn_loan_release (release_id, receipt_id, customer_name, nic_number, phone_number, address, item_name, total_weight, total_amount, stamp_fees, current_interest, net_value, release_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
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

    @Override
    public boolean update(PawnLoanRelease dto) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<PawnLoanRelease> getAll() throws SQLException {
        return null;
    }
}