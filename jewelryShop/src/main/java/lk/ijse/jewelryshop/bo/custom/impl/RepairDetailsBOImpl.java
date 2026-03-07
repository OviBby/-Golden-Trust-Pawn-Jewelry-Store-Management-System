package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.RepairDetailsBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.RepairDetailsDAO;
import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairDetailsBOImpl implements RepairDetailsBO {

    RepairDetailsDAO repairDetailsDAO = (RepairDetailsDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.REPAIRDETAILS);

    @Override
    public String generateId() throws SQLException {
        return repairDetailsDAO.generateId();
    }

    @Override
    public RepairItemDTO getRepairItemDetails(String repairId) throws SQLException {
        return repairDetailsDAO.getRepairItemDetails(repairId);
    }

    @Override
    public RepairItemDTO getItemDetails(String itemId) throws SQLException {
        return repairDetailsDAO.getItemDetails(itemId);
    }

    @Override
    public boolean save(RepairDetailsDTO dto) throws SQLException {
        return repairDetailsDAO.save(dto);
    }

    @Override
    public Double calculateBalance(Double estimatedCost, Double advancePayment) {
        return repairDetailsDAO.calculateBalance(estimatedCost, advancePayment);
    }

    @Override
    public boolean completeRepair(RepairDetailsDTO dto) throws SQLException {
        return repairDetailsDAO.completeRepair(
                new RepairDetails(
                        dto.getRepairId(),
                        dto.getCustomerName(),
                        dto.getCompletedDate(),
                        dto.getRepairDescription(),
                        dto.getEstimatedCost(),
                        dto.getAdvancepayment(),
                        dto.getAmount()
                )
        );
    }

    @Override
    public ArrayList<RepairDetailsDTO> getAll() throws SQLException {
        ArrayList<RepairDetails> list = repairDetailsDAO.getAll();
        ArrayList<RepairDetailsDTO> list2 = new ArrayList<>();
        for (RepairDetails rd : list) {
            list2.add(new RepairDetailsDTO(
                    rd.getRepairId(),
                    rd.getCustomerName(),
                    rd.getRepairDescription(),
                    rd.getSubmittedDate(),
                    rd.getStatus(),
                    rd.getEstimatedCost()
            ));
        }
        return list2;
    }

    @Override
    public RepairDetailsDTO search(String repairId) throws SQLException {
        RepairDetails rd = repairDetailsDAO.search(repairId);
        if (rd == null) return null;
        return new RepairDetailsDTO(
                rd.getCustomerName(),
                rd.getRepairDescription(),
                rd.getSubmittedDate(),
                rd.getStatus(),
                rd.getEstimatedCost()
        );
    }
}