package lk.ijse.jewelryshop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.CustomerBO;
import lk.ijse.jewelryshop.bo.custom.ItemBO;
import lk.ijse.jewelryshop.bo.custom.OrderBO;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;

public class ReportsConroller {
   // CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    //ItemDAOImpl itemDAO = new ItemDAOImpl();
    //OrderDAOImpl orderDAO = new OrderDAOImpl();
   CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
   ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);




    public void printOnAction(javafx.event.ActionEvent actionEvent) {
        try{
            customerBO.printCustomerReport();


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void printitemOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            System.out.println("Print button clicked!");
            itemBO.printReport();
            System.out.println("Report completed!");

        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Report Generation Error!\n\n" + e.getMessage()).showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Database Error!\n\n" + e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Unexpected Error!\n\n" + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void printOrderOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            System.out.println("Print button clicked!");
orderBO.printReport();
            System.out.println("Report completed!");

        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Report Generation Error!\n\n" + e.getMessage()).showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Database Error!\n\n" + e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Unexpected Error!\n\n" + e.getMessage()).showAndWait();
        }
    }


    @FXML
    void dashbordOnAction() {
        System.out.println("Dashbord Page Loaded");
        App.setRoot("Dashboard");

    }

    @FXML
    void customerOnAction() {
        System.out.println("Customer Page Loaded");
        App.setRoot("customer");

    }

    @FXML
    void orderOnAction() {
        System.out.println("Order Page Loaded");
        App.setRoot("order");
    }

    @FXML
    void jewelryItemOnAction() {
        System.out.println("Jewelry Item Page Loaded");
        App.setRoot("item");
    }

    @FXML
    void pawnLoanOnAction() {
        System.out.println("Pawn Loan Page Loaded");
        App.setRoot("pawnloan");
    }

    @FXML
    void pawnItemOnAction() {
        System.out.println("Pawn Item Page Loaded");
        App.setRoot("pawanreles");
    }

    @FXML
    void purchaseOnAction() {
        System.out.println("Reprot Loaded");
        App.setRoot("reprots");
    }

    @FXML
    void repairItemOnAction() {
        System.out.println("Repair Item Loaded");
        App.setRoot("repairitem");
    }

    @FXML
    void repairDetailsOnAction() {
        System.out.println("Repair Details Loaded");
        App.setRoot("repairdetails");
    }



}

