package lk.ijse.jewelryshop.bo.custom;

import lk.ijse.jewelryshop.bo.SuperBO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DashboardBO extends SuperBO {

    public ArrayList<RepairItemDTO> getAll() throws SQLException;
}
