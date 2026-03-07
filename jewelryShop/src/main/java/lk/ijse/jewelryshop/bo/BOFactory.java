package lk.ijse.jewelryshop.bo;

import lk.ijse.jewelryshop.bo.custom.impl.*;


public class BOFactory {
   private static BOFactory instance;
   private BOFactory() {}
    public static BOFactory getInstance() {
       return instance == null ? instance = new BOFactory() : instance;

    }
    public enum  BOType {
        CUSTOMER,
        DASHBOARD,
        ITEM,
        ORDER,
        PAWNLOAN,
        PAWNRELEASE,
        REPAIRDETAILS,
        REPAIRITEM
    }
    public SuperBO getBO(BOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerBOImpl();

            case DASHBOARD:
                return new DashboardBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PAWNLOAN:
                return new PawnLoanBOImpl();
            case PAWNRELEASE:
                return new PawnReleaseBOImpl();
            case REPAIRDETAILS:
                return new RepairDetailsBOImpl();
            case REPAIRITEM:
                return new RepairItemBOImpl();
            default:
                return null;
        }
    }
}
