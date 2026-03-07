package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.RepairDetailsDAO;
import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairDetailsBO extends SuperBO {


    public  String generateId() throws SQLException ;

    public RepairItemDTO getRepairItemDetails(String repairId) throws SQLException ;

    public  RepairItemDTO getItemDetails(String itemId) throws SQLException ;

    public  boolean save(RepairDetailsDTO dto) throws SQLException ;

    public  Double calculateBalance(Double estimatedCost, Double advancePayment) ;

    public  boolean completeRepair(RepairDetailsDTO dto) throws SQLException ;

    public ArrayList<RepairDetailsDTO> getAll() throws SQLException ;

    public  RepairDetailsDTO search(String repairId) throws SQLException ;
}
