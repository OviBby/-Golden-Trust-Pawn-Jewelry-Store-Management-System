package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PawnLoanBO extends SuperBO {

    public String generateId(String loanType) throws SQLException;

    public boolean savePawnLoanItem(PawnLoanDTO pawnLoanDTO) throws SQLException;

    public boolean savePawnLoanDetails(PawnLoanDTO pawnLoanDTO) throws SQLException;

    public ArrayList<PawnLoanDTO> getAll() throws SQLException;

    public CustomerDTO getCustomerDetails(String Nic) throws SQLException;

    public CustomerDTO getCustomerDetail(String phone) throws SQLException;
}
