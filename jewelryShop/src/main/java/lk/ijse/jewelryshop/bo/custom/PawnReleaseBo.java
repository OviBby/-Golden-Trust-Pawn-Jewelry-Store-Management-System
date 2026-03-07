package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.dto.PawnLoanReleaseDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PawnReleaseBo extends SuperBO {

    public  String generateId() throws SQLException ;


    public CustomerDTO getCustomerByNic(String nic) throws SQLException ;

    public  CustomerDTO getCustomerByPhone(String phone) throws SQLException ;

    public ArrayList<PawnLoanDTO> getAll(String loanId) throws SQLException;

    public  boolean isReceiptAlreadyReleased(String receiptId) throws SQLException ;

    public  boolean save(PawnLoanReleaseDTO dto) throws SQLException ;
}
