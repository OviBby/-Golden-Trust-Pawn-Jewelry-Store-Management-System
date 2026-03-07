package lk.ijse.jewelryshop.dao.Impl;

import lk.ijse.jewelryshop.dao.CrudDAO;
import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.entity.RepairDetails;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairDetailsDAO extends CrudDAO<RepairDetails> {


    public  RepairItemDTO getRepairItemDetails(String repairId) throws SQLException ;

    public  RepairItemDTO getItemDetails(String itemId) throws SQLException ;

    public  Double calculateBalance(Double estimatedCost, Double advancePayment) ;

    public  boolean completeRepair(RepairDetails dto) throws SQLException ;

    public  boolean save(RepairDetailsDTO dto) throws SQLException;

    public  ArrayList<RepairDetails> getAll() throws SQLException;

    public  RepairDetails search(String repairId) throws SQLException;
}
