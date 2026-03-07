package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.PawnLoanBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.PawnLoanDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;

import java.sql.SQLException;
import java.util.ArrayList;

public class PawnLoanBOImpl implements PawnLoanBO {

    PawnLoanDAO pawnLoanDAO = (PawnLoanDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.PAWNLOAN);

    @Override
    public String generateId(String loanType) throws SQLException {
        return pawnLoanDAO.generateId(loanType);
    }

    @Override
    public boolean savePawnLoanItem(PawnLoanDTO pawnLoanDTO) throws SQLException {
        return pawnLoanDAO.savePawnLoanItem(
                new PawnLoan(
                        pawnLoanDTO.getPawnType(),
                        pawnLoanDTO.getLoanId(),
                        pawnLoanDTO.getItemName(),
                        pawnLoanDTO.getStartDate(),
                        pawnLoanDTO.getWeight(),
                        pawnLoanDTO.getPricePerGram(),
                        pawnLoanDTO.getGoldCarat(),
                        pawnLoanDTO.getLoanMonths()
                )
        );
    }

    @Override
    public boolean savePawnLoanDetails(PawnLoanDTO pawnLoanDTO) throws SQLException {
        return pawnLoanDAO.savePawnLoanDetails(
                new PawnLoan(
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
                )
        );
    }

    @Override
    public ArrayList<PawnLoanDTO> getAll() throws SQLException {
        ArrayList<PawnLoan> list = pawnLoanDAO.getAll();
        ArrayList<PawnLoanDTO> list2 = new ArrayList<>();
        for (PawnLoan pawnLoan : list) {
            list2.add(new PawnLoanDTO(
                    pawnLoan.getPawnType(),
                    pawnLoan.getItemName(),
                    pawnLoan.getWeight(),
                    pawnLoan.getGoldCarat(),
                    pawnLoan.getPricePerGram()
            ));
        }
        return list2;
    }

    @Override
    public CustomerDTO getCustomerDetails(String nic) throws SQLException {
        return pawnLoanDAO.getCustomerDetails(nic);
    }

    @Override
    public CustomerDTO getCustomerDetail(String phone) throws SQLException {
        return pawnLoanDAO.getCustomerDetail(phone);
    }
}