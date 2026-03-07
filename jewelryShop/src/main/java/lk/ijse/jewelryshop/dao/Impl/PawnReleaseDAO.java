package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.dto.PawnLoanReleaseDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;
import lk.ijse.jewelryshop.entity.PawnLoanRelease;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PawnReleaseDAO extends CrudDAO<PawnLoanRelease> {

    String generateId() throws SQLException;
    CustomerDTO getCustomerByNic(String nic) throws SQLException;
    CustomerDTO getCustomerByPhone(String phone) throws SQLException;
    ArrayList<PawnLoan> getAll(String loanId) throws SQLException;
    boolean isReceiptAlreadyReleased(String receiptId) throws SQLException;
    boolean save(PawnLoanRelease dto) throws SQLException;
}



