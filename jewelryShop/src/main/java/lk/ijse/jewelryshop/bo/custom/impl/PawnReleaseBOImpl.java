package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.PawnReleaseBo;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.PawnReleaseDAO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.dto.PawnLoanReleaseDTO;
import lk.ijse.jewelryshop.entity.PawnLoan;
import lk.ijse.jewelryshop.entity.PawnLoanRelease;

import java.sql.SQLException;
import java.util.ArrayList;

public class PawnReleaseBOImpl implements PawnReleaseBo {

    PawnReleaseDAO pawnReleaseDAO = (PawnReleaseDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.PAWNRELEASE);

    @Override
    public String generateId() throws SQLException {
        return pawnReleaseDAO.generateId();
    }

    @Override
    public CustomerDTO getCustomerByNic(String nic) throws SQLException {
        return pawnReleaseDAO.getCustomerByNic(nic);
    }

    @Override
    public CustomerDTO getCustomerByPhone(String phone) throws SQLException {
        return pawnReleaseDAO.getCustomerByPhone(phone);
    }

    @Override
    public ArrayList<PawnLoanDTO> getAll(String loanId) throws SQLException {

        ArrayList<PawnLoan> list = pawnReleaseDAO.getAll(loanId);
        ArrayList<PawnLoanDTO> list1 = new ArrayList<>();
        for (PawnLoan pawnLoan : list) {
            list1.add(new PawnLoanDTO(
                    pawnLoan.getLoanId(),
                    pawnLoan.getItemName(),
                    pawnLoan.getWeight(),
                    pawnLoan.getGoldCarat(),
                    pawnLoan.getPricePerGram()
            ));
        }
        return list1;
    }

    @Override
    public boolean isReceiptAlreadyReleased(String receiptId) throws SQLException {
        return pawnReleaseDAO.isReceiptAlreadyReleased(receiptId);
    }

    @Override
    public boolean save(PawnLoanReleaseDTO dto) throws SQLException {
        return pawnReleaseDAO.save(
                new PawnLoanRelease(
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
                )
        );
    }
}