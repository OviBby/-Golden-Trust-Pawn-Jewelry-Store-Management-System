package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PawnLoanDAO extends CrudDAO<PawnLoan> {


    public  String generateId(String loanType) throws SQLException;

    public  boolean savePawnLoanItem(PawnLoan pawnLoanDTO) throws SQLException ;

    public  boolean savePawnLoanDetails(PawnLoan pawnLoanDTO) throws SQLException ;

    public  ArrayList<PawnLoan> getAll() throws SQLException;

    public  CustomerDTO getCustomerDetails(String Nic) throws SQLException ;

    public  CustomerDTO getCustomerDetail(String phone) throws SQLException ;
}
