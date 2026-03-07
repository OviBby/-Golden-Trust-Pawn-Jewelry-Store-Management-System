package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.RepairItemBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.RepairItemDAO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairItemBOImpl implements RepairItemBO {

    RepairItemDAO repairItemDAO = (RepairItemDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.REPAIRITEM);

    @Override
    public String generateId() throws SQLException {
        return repairItemDAO.generateId();
    }

    @Override
    public String getCustomerDetails(String phone) throws SQLException {
        return repairItemDAO.getCustomerDetails(phone);
    }

    @Override
    public boolean save(RepairItemDTO dto) throws SQLException {
        return repairItemDAO.save(
                new RepairItem(
                        dto.getRepairItemId(),
                        dto.getCurrentCondition(),
                        dto.getMetalType(),
                        dto.getWeight(),
                        dto.getDescription(),
                        dto.getReceivedDate(),
                        dto.getAdvancePayment(),
                        dto.getCustomerName(),
                        dto.getPhoneNumber()
                )
        );
    }

    @Override
    public RepairItemDTO selectRepairItemById(String repairItemId) throws SQLException {
        return repairItemDAO.selectRepairItemById(repairItemId);
    }

    @Override
    public boolean update(RepairItemDTO dto) throws SQLException {
        return repairItemDAO.update(
                new RepairItem(
                        dto.getRepairItemId(),
                        dto.getCurrentCondition(),
                        dto.getMetalType(),
                        dto.getWeight(),
                        dto.getDescription(),
                        dto.getReceivedDate(),
                        dto.getAdvancePayment(),
                        dto.getCustomerName(),
                        dto.getPhoneNumber()
                )
        );
    }

    @Override
    public ArrayList<RepairItemDTO> getAll() throws SQLException {
        ArrayList<RepairItem> list = repairItemDAO.getAll();
        ArrayList<RepairItemDTO> list2 = new ArrayList<>();
        for (RepairItem item : list) {
            list2.add(new RepairItemDTO(
                    item.getRepairItemId(),
                    item.getCurrentCondition(),
                    item.getMetalType(),
                    item.getWeight(),
                    item.getDescription(),
                    item.getReceivedDate(),
                    item.getAdvancePayment(),
                    item.getCustomerName(),
                    item.getPhoneNumber()
            ));
        }
        return list2;
    }
}