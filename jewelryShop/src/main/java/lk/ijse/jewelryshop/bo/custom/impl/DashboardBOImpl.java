package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.DashboardBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.CustomerDAO;
import lk.ijse.jewelryshop.dao.Impl.DashboardDAO;
import lk.ijse.jewelryshop.dao.Impl.RepairItemDAO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardBOImpl implements DashboardBO {
    DashboardDAO dashboardDAO = (DashboardDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.DASHBOARD);

    @Override
    public ArrayList<RepairItemDTO> getAll() throws SQLException {
        return dashboardDAO.getAllRepairItem();
    }
}
