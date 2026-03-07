package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.RepairDetailsDAO;
import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairDetails;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairDetailsDAOImpl implements RepairDetailsDAO {

    @Override
    public String generateId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT repair_id FROM RepairDetails ORDER BY repair_id DESC LIMIT 1");
        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("RD", "")) + 1;
            return String.format("RD%03d", num);
        }
        return "RD001";
    }

    @Override
    public RepairItemDTO getRepairItemDetails(String repairId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT customer_name, description FROM RepairItem WHERE repair_item_id=?", repairId);
        if (rs.next()) {
            return new RepairItemDTO(
                    repairId,
                    rs.getString("customer_name"),
                    rs.getString("description")
            );
        }
        return null;
    }

    @Override
    public RepairItemDTO getItemDetails(String itemId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT customer_name, description, advance_payment FROM RepairItem WHERE repair_item_id=?", itemId);
        if (rs.next()) {
            return new RepairItemDTO(
                    itemId,
                    rs.getString("customer_name"),
                    rs.getString("description"),
                    rs.getDouble("advance_payment")
            );
        }
        return null;
    }

    @Override
    public boolean save(RepairDetailsDTO dto) throws SQLException {
        String repairId = generateId();
        return CrudUtil.execute(
                "INSERT INTO RepairDetails(repair_id, repair_item_id, customer_name, repair_description, estimated_cost, submitted_date, status) VALUES(?,?,?,?,?,?,?)",
                repairId,
                dto.getRepairId(),
                dto.getCustomerName(),
                dto.getRepairDescription(),
                dto.getEstimatedCost(),
                dto.getSubmittedDate(),
                dto.getStatus()
        );
    }

    @Override
    public boolean completeRepair(RepairDetails dto) throws SQLException {

        return CrudUtil.execute(
                "UPDATE RepairDetails SET completed_date=?, advance_payment=?, amount=?, status='Completed' " +
                        "WHERE repair_item_id=? ORDER BY repair_id DESC LIMIT 1",
                dto.getCompletedDate(),
                dto.getAdvancepayment(),
                dto.getAmount(),
                dto.getRepairId()
        );
    }

    @Override
    public Double calculateBalance(Double estimatedCost, Double advancePayment) {
        if (estimatedCost == null || advancePayment == null) return 0.0;
        return estimatedCost - advancePayment;
    }

    @Override
    public ArrayList<RepairDetails> getAll() throws SQLException {
        ArrayList<RepairDetails> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM RepairDetails");
        while (rs.next()) {
            list.add(new RepairDetails(
                    rs.getString("repair_id"),
                    rs.getString("customer_name"),
                    rs.getString("repair_description"),
                    rs.getString("submitted_date"),
                    rs.getString("status"),
                    rs.getDouble("estimated_cost")
            ));
        }
        return list;
    }

    @Override
    public RepairDetails search(String repairId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM RepairDetails WHERE repair_item_id=? ORDER BY repair_id DESC LIMIT 1",
                repairId
        );
        if (rs.next()) {
            return new RepairDetails(
                    rs.getString("customer_name"),
                    rs.getString("repair_description"),
                    rs.getString("submitted_date"),
                    rs.getString("status"),
                    rs.getDouble("estimated_cost")
            );
        }
        return null;
    }



    @Override
    public boolean save(RepairDetails dto) throws SQLException { return false; }

    @Override
    public boolean update(RepairDetails dto) throws SQLException { return false; }
}