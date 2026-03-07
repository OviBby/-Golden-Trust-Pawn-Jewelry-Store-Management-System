/*
package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairItemModel {

    public static String generateNextRepairItemId() throws SQLException {
        String sql = "SELECT repair_item_id FROM RepairItem ORDER BY repair_item_id DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            String lastId = rs.getString(1);
            int num = Integer.parseInt(lastId.replace("R", "")) + 1;
            return String.format("R%03d", num);
        }
        return "R001";

    }

    public static String getCustomerDetails(String phone) throws SQLException {

        String customerName = null;

        String sql = "SELECT first_name, last_name FROM Customer WHERE phone_number = ?";
        ResultSet rs = CrudUtil.execute(sql, phone);

        if (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            customerName = firstName + " " + lastName;
        }

        return customerName;
    }

   public static boolean saveRepairItem(RepairItemDTO repairItemDTO) throws SQLException {
        repairItemDTO.setRepairItemId(generateNextRepairItemId());


        String sql = "INSERT INTO RepairItem (repair_item_id, current_condition,metal_type, weight_grams, description, received_date, advance_payment,customer_name ,phone_number ) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

        return CrudUtil.execute(sql,
                repairItemDTO.getRepairItemId(),
                repairItemDTO.getCurrentCondition(),
                repairItemDTO.getMetalType(),
                repairItemDTO.getWeight(),
                repairItemDTO.getDescription(),
                repairItemDTO.getReceivedDate(),
                repairItemDTO.getAdvancePayment(),
                repairItemDTO.getCustomerName(),
                repairItemDTO.getPhoneNumber()



        );

    }

    public static RepairItemDTO selectRepairItemById(String repairItemId) throws SQLException {

        String sql = "SELECT repair_item_id, current_condition, metal_type, weight_grams, description, received_date, advance_payment, customer_name, phone_number FROM RepairItem WHERE repair_item_id = ?";

        ResultSet rs = CrudUtil.execute(sql, repairItemId);

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

    public static boolean updateRepairItem(RepairItemDTO repairItemDTO) throws SQLException {
        String sql = "UPDATE RepairItem SET current_condition = ?, metal_type = ?, weight_grams = ?, description = ?,received_date = ?, advance_payment = ?, customer_name = ?, phone_number = ? WHERE repair_item_id = ?";

        return CrudUtil.execute(sql,
                repairItemDTO.getCurrentCondition(),
                repairItemDTO.getMetalType(),
                repairItemDTO.getWeight(),
                repairItemDTO.getDescription(),
                repairItemDTO.getReceivedDate(),
                repairItemDTO.getAdvancePayment(),
                repairItemDTO.getCustomerName(),
                repairItemDTO.getPhoneNumber(),
                repairItemDTO.getRepairItemId()
        );
    }

    public static ArrayList<RepairItemDTO> getAllRepairItem() throws SQLException {
        ArrayList<RepairItemDTO> repairItemList = new ArrayList<>();
        String sql = "SELECT * FROM RepairItem  ";

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()) {
            repairItemList.add(new RepairItemDTO(
                    rs.getString("repair_item_id"),
                    rs.getString("customer_name"),
                    rs.getString("received_date"),
                    rs.getString("metal_type"),
                    rs.getString("description"),
                    rs.getString("weight_grams")
            ));
        }
        return repairItemList;
    }



}

*/
