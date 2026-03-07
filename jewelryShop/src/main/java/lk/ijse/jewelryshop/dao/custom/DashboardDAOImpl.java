package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dao.Impl.DashboardDAO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardDAOImpl implements DashboardDAO  {
    @Override
    public  ArrayList<RepairItemDTO> getAllRepairItem() throws SQLException {
        ArrayList<RepairItemDTO> repairItemList = new ArrayList<>();
        String sql = "SELECT * FROM RepairItem  ";

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()) {
            repairItemList.add(new RepairItemDTO(
                    rs.getString("repair_item_id"),
                    rs.getString("received_date"),
                    rs.getString("customer_name"),
                    rs.getString("phone_number"),
                    rs.getString("metal_type"),
                    rs.getString("current_condition"),
                    rs.getString("description"),
                    Double.parseDouble(rs.getString("weight_grams")),
                    rs.getDouble("advance_payment")
            ));
        }
        return repairItemList;
    }


}
