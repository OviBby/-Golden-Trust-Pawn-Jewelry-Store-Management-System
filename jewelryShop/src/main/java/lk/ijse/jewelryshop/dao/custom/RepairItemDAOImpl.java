package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.RepairItemDAO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairItem;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairItemDAOImpl implements RepairItemDAO {

    @Override
    public String generateId() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT repair_item_id FROM RepairItem ORDER BY repair_item_id DESC LIMIT 1");
        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("R", "")) + 1;
            return String.format("R%03d", num);
        }
        return "R001";
    }

    @Override
    public String getCustomerDetails(String phone) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT first_name, last_name FROM Customer WHERE phone_number = ?", phone);
        if (rs.next()) {
            return rs.getString("first_name") + " " + rs.getString("last_name");
        }
        return null;
    }

    @Override
    public boolean save(RepairItem item) throws SQLException {
        item.setRepairItemId(generateId());
        return CrudUtil.execute(
                "INSERT INTO RepairItem (repair_item_id, current_condition, metal_type, weight_grams, " +
                        "description, received_date, advance_payment, customer_name, phone_number) VALUES (?,?,?,?,?,?,?,?,?)",
                item.getRepairItemId(),
                item.getCurrentCondition(),
                item.getMetalType(),
                item.getWeight(),
                item.getDescription(),
                item.getReceivedDate(),
                item.getAdvancePayment(),
                item.getCustomerName(),
                item.getPhoneNumber()
        );
    }

    @Override
    public RepairItemDTO selectRepairItemById(String repairItemId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM RepairItem WHERE repair_item_id = ?", repairItemId);
        if (rs.next()) {
            return new RepairItemDTO(
                    rs.getString("repair_item_id"),
                    rs.getString("current_condition"),
                    rs.getString("metal_type"),
                    rs.getDouble("weight_grams"),
                    rs.getString("description"),
                    rs.getString("received_date"),
                    rs.getDouble("advance_payment"),
                    rs.getString("customer_name"),
                    rs.getString("phone_number")
            );
        }
        return null;
    }

    @Override
    public boolean update(RepairItem item) throws SQLException {
        return CrudUtil.execute(
                "UPDATE RepairItem SET current_condition=?, metal_type=?, weight_grams=?, " +
                        "description=?, received_date=?, advance_payment=?, customer_name=?, phone_number=? " +
                        "WHERE repair_item_id=?",
                item.getCurrentCondition(),
                item.getMetalType(),
                item.getWeight(),
                item.getDescription(),
                item.getReceivedDate(),
                item.getAdvancePayment(),
                item.getCustomerName(),
                item.getPhoneNumber(),
                item.getRepairItemId()
        );
    }

    @Override
    public ArrayList<RepairItem> getAll() throws SQLException {
        ArrayList<RepairItem> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM RepairItem");
        while (rs.next()) {
            list.add(new RepairItem(
                    rs.getString("repair_item_id"),
                    rs.getString("current_condition"),
                    rs.getString("metal_type"),
                    rs.getDouble("weight_grams"),
                    rs.getString("description"),
                    rs.getString("received_date"),
                    rs.getDouble("advance_payment"),
                    rs.getString("customer_name"),
                    rs.getString("phone_number")
            ));
        }
        return list;
    }


}