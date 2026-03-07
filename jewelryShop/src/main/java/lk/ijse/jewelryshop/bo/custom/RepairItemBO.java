package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairItemBO  extends SuperBO {

    public  String generateId() throws SQLException ;

    public  String getCustomerDetails(String phone) throws SQLException ;

    public  boolean save(RepairItemDTO repairItemDTO) throws SQLException ;

    public  RepairItemDTO selectRepairItemById(String repairItemId) throws SQLException ;

    public  boolean update(RepairItemDTO repairItemDTO) throws SQLException ;

    public ArrayList<RepairItemDTO> getAll() throws SQLException ;
}
