package lk.ijse.jewelryshop.dao;

import lk.ijse.jewelryshop.dao.custom.*;

public class DaoFactoty {
    private static DaoFactoty instance;
    private  DaoFactoty() {}
    public static DaoFactoty getInstance() {
        return instance == null ? instance = new DaoFactoty() : instance;
    }
    public enum DaoTypes {
        CUSTOMER,
        DASHBOARD,
        ITEM,
        ORDER,
        PAWNLOAN,
        PAWNRELEASE,
        REPAIRDETAILS,
        REPAIRITEM

    }
    public SuperDAO getDAO(DaoTypes daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();

            case DASHBOARD:
                 return new DashboardDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case PAWNLOAN:
                return new PawnLoanDAOImpl();
            case PAWNRELEASE:
                return new PawnReleaseDAOImpl();
            case REPAIRDETAILS:
                return new RepairDetailsDAOImpl();
            case REPAIRITEM:
                return new RepairItemDAOImpl();

            default:
                return null;
        }
    }


}
