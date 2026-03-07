package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairItem;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairItemDAO extends CrudDAO<RepairItem> {

    public  String getCustomerDetails(String phone) throws SQLException ;

    public  RepairItemDTO selectRepairItemById(String repairItemId) throws SQLException ;

    public  boolean save(RepairItem repairItemDTO) throws SQLException;

    public  boolean update(RepairItem repairItemDTO) throws SQLException ;

    public ArrayList<RepairItem> getAll() throws SQLException ;





}
