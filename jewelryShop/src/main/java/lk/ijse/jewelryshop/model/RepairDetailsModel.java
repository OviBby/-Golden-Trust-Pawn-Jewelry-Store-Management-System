/*
package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class RepairDetailsModel {


    public static String generateNextRepairId() throws SQLException {
        String sql = "SELECT repair_id FROM RepairDetails ORDER BY repair_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("RD", "")) + 1;
            return String.format("RD%03d", num);
        }
        return "RD001";
    }

    public static RepairItemDTO getRepairItemDetails(String repairId) throws SQLException {
        String sql = "SELECT customer_name, description FROM RepairItem WHERE repair_item_id=?";
        ResultSet rs = CrudUtil.execute(sql, repairId);

        if (rs.next()) {
            return new RepairItemDTO(
                    repairId,
                    rs.getString("customer_name"),
                    rs.getString("description")
            );
        }
        return null;
    }

    public static RepairItemDTO getItemDetails(String itemId) throws SQLException {
        String sql = "SELECT customer_name, description,advance_payment  FROM RepairItem WHERE repair_item_id=?";
        ResultSet rs = CrudUtil.execute(sql, itemId);

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

    public static boolean saveRepairDetails(RepairDetailsDTO dto) throws SQLException {


        String repairId = generateNextRepairId();

        String sql = "INSERT INTO RepairDetails(repair_id, repair_item_id,customer_name   , repair_description, estimated_cost, submitted_date , status) VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                repairId,
                dto.getRepairId(),
                dto.getCustomerName(),
                dto.getRepairDescription(),
                dto.getEstimatedCost(),
                dto.getSubmittedDate(),
                dto.getStatus()
        );
    }

    public static Double calculateBalance(Double estimatedCost, Double advancePayment) {
        if (estimatedCost == null || advancePayment == null) {
            return 0.0;
        }
        return estimatedCost - advancePayment;
    }

    public static boolean completeRepair(RepairDetailsDTO dto) throws SQLException {

        String repairId = generateNextRepairId();

        String sql = "INSERT INTO RepairDetails(repair_id, repair_item_id, customer_name, repair_description, estimated_cost, completed_date, advance_payment, amount) VALUES(?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                repairId,
                dto.getRepairId(),
                dto.getCustomerName(),
                dto.getRepairDescription(),
                dto.getEstimatedCost(),
                dto.getCompletedDate(),
                dto.getAdvancepayment(),
                dto.getAmount()
        );
    }

    public static ArrayList<RepairDetailsDTO> getAllRepairDetails() throws SQLException {
        ArrayList<RepairDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM RepairDetails ";

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()) {
            list.add(new RepairDetailsDTO(
                    rs.getString("repair_item_id"),
                    rs.getString("customer_name"),
                    rs.getString("repair_description"),
                    rs.getString("submitted_date"),
                    rs.getString("status"),
                    rs.getDouble("estimated_cost")
            ));
        }
        return list;
    }

public static RepairDetailsDTO searchRepairDetails(String repairId) throws SQLException {
        String sql = "SELECT * FROM RepairDetails WHERE repair_item_id=?";

        ResultSet rs = CrudUtil.execute(sql, repairId);

        if (rs.next()) {
            return new RepairDetailsDTO(
                    rs.getString("customer_name"),
                    rs.getString("repair_description"),
                    rs.getString("submitted_date"),
                    rs.getString("status"),
                    rs.getDouble("estimated_cost")

            );
        }
        return null;
}

}

*/
