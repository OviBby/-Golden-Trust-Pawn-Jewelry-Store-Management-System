package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.OrderBO;
import lk.ijse.jewelryshop.bo.custom.PawnLoanBO;
import lk.ijse.jewelryshop.dao.custom.PawnLoanDAOImpl;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PawnLoanController implements Initializable {
    //PawnLoanDAOImpl  pawnLoanDAO = new PawnLoanDAOImpl();
    PawnLoanBO pawnLoanBO = (PawnLoanBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAWNLOAN);


    @FXML
    private ComboBox<String> cmbpawntype;
    @FXML
    private TextField txtreceipno;
    @FXML
    private ComboBox<String> cmbitem;
    @FXML
    private DatePicker dpdate;
    @FXML
    private ComboBox<String> cmbmonth;
    @FXML
    private TextField txtweight;
    @FXML
    private TextField txtpriec;
    @FXML
    private ComboBox<String> cmbCT;
    @FXML
    private TextField txtcustomername;
    @FXML
    private TextField txtnic;
    @FXML
    private TextField txtphone;
    @FXML
    private TextField txtadress;
    @FXML
    private TextField txttotalweight;
    @FXML
    private TextField txtasessesvalue;
    @FXML
    private TextField txttotal;
    @FXML
    private TextField txtstampfees;
    @FXML
    private TextField txtcurrentinterest;
    @FXML
    private TextField txtnetvalue;
    @FXML
    private TextField txtmarketvalue;
    @FXML
    private TableView<PawnLoanDTO> tblpawnloanitem;
    @FXML
    private TableColumn<PawnLoanDTO,String> colid;
    @FXML
    private TableColumn<PawnLoanDTO,String> colitem;
    @FXML
    private TableColumn<PawnLoanDTO,Double> colweight;
    @FXML
    private TableColumn<PawnLoanDTO,String>colCT;
    @FXML
    private TableColumn<PawnLoanDTO,Double> colprice;


    private static final double DEFAULT_STAMP_FEE = 5.0;
    private static final double DEFAULT_INTEREST_RATE = 0.03;


    ObservableList<PawnLoanDTO>pawnLoanList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        txtreceipno.setEditable(false);
        txttotalweight.setEditable(false);
        txttotal.setEditable(false);
        txtnetvalue.setEditable(false);


        txtstampfees.setEditable(true);
        txtcurrentinterest.setEditable(true);

        colid.setCellValueFactory(new PropertyValueFactory<>("pawnType"));
        colitem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colweight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colCT.setCellValueFactory(new PropertyValueFactory<>("goldCarat"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("pricePerGram"));

        tblpawnloanitem.setItems(pawnLoanList);

        cmbitem.setItems(FXCollections.observableArrayList("Gold Ring", "Gold Necklace", "Gold Bracelet", "Gold Chain", "Gold Bangle", "Gold Earring"));
        cmbmonth.setItems(FXCollections.observableArrayList("THREE MONTH","SIX MONTH","ONE YEAR"));
        cmbCT.setItems(FXCollections.observableArrayList("10K","14K","16K","18K","22K","24K"));

        cmbpawntype.getItems().addAll("A", "D");

        LocalDate today = LocalDate.now();
        if (dpdate != null) {
            dpdate.setValue(today);
        }



        txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
        txtcurrentinterest.setText(String.format("%.2f", DEFAULT_INTEREST_RATE * 100));


        txtstampfees.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!txttotal.getText().isEmpty()) {
                try {
                    double total = Double.parseDouble(txttotal.getText());
                    calculateNetValue(total);
                } catch (NumberFormatException e) {

                }
            }
        });

        txtcurrentinterest.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!txttotal.getText().isEmpty()) {
                try {
                    double total = Double.parseDouble(txttotal.getText());
                    calculateNetValue(total);
                } catch (NumberFormatException e) {

                }
            }
        });


        cmbmonth.setOnAction(event -> {
            if (!txttotal.getText().isEmpty()) {
                try {
                    double total = Double.parseDouble(txttotal.getText());
                    calculateNetValue(total);
                } catch (NumberFormatException e) {

                }
            }
        });

        cmbpawntype.setOnAction(event -> {
            try {
                String loanType = cmbpawntype.getValue();
                if (loanType != null) {
                    String nextLoanId = pawnLoanBO.generateId(loanType);
                    txtreceipno.setText(nextLoanId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            }
        });
    }

    private void calculateTotals() {
        double totalWeight = 0.0;
        double totalPrice = 0.0;

        for (PawnLoanDTO item : pawnLoanList) {
            totalWeight += item.getWeight();
            totalPrice += item.getPricePerGram();
        }

        txttotalweight.setText(String.format("%.2f", totalWeight));
        txttotal.setText(String.format("%.2f", totalPrice));

        calculateNetValue(totalPrice);
    }

    private void calculateNetValue(double totalPrice) {
        String loanDuration = cmbmonth.getValue();

        if (loanDuration == null || loanDuration.isEmpty()) {
            txtnetvalue.setText("0.00");
            return;
        }


        double stampFee;
        try {
            stampFee = Double.parseDouble(txtstampfees.getText().trim());
            if (stampFee < 0) {
                new Alert(Alert.AlertType.WARNING, "Stamp fee cannot be negative!").show();
                txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
                stampFee = DEFAULT_STAMP_FEE;
            }
        } catch (NumberFormatException e) {
            stampFee = DEFAULT_STAMP_FEE;
            txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
        }


        double interestRate;
        try {
            String interestText = txtcurrentinterest.getText().trim();
            interestRate = Double.parseDouble(interestText) / 100.0;
            if (interestRate < 0 || interestRate > 1) {
                new Alert(Alert.AlertType.WARNING, "Interest rate must be between 0 and 100!").show();
                txtcurrentinterest.setText(String.format("%.2f", DEFAULT_INTEREST_RATE * 100));
                interestRate = DEFAULT_INTEREST_RATE;
            }
        } catch (NumberFormatException e) {
            interestRate = DEFAULT_INTEREST_RATE;
            txtcurrentinterest.setText(String.format("%.2f", DEFAULT_INTEREST_RATE * 100));
        }


        int months = 0;
        switch (loanDuration) {
            case "THREE MONTH":
                months = 3;
                break;
            case "SIX MONTH":
                months = 6;
                break;
            case "ONE YEAR":
                months = 12;
                break;
            default:
                months = 1;
        }

        double interestPerMonth = totalPrice * interestRate;
        double totalInterest = interestPerMonth * months;
        double netValue = totalPrice + stampFee + totalInterest;

        txtnetvalue.setText(String.format("%.2f", netValue));
    }

    @FXML
    void btnAddPawnItem() {
        try {

            if (cmbpawntype.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Pawn Type!").show();
                return;
            }

            if (cmbitem.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Item!").show();
                return;
            }

            if (dpdate.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Date!").show();
                return;
            }

            if (txtweight.getText() == null || txtweight.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Weight!").show();
                return;
            }

            if (txtpriec.getText() == null || txtpriec.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Price!").show();
                return;
            }

            if (cmbCT.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Gold Carat!").show();
                return;
            }

            if (cmbmonth.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Loan Duration!").show();
                return;
            }

            double weight;
            try {
                weight = Double.parseDouble(txtweight.getText().trim());
                if (weight <= 0) {
                    new Alert(Alert.AlertType.WARNING, "Weight must be greater than 0!").show();
                    return;
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid number for Weight!").show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(txtpriec.getText().trim());
                if (price <= 0) {
                    new Alert(Alert.AlertType.WARNING, "Price must be greater than 0!").show();
                    return;
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid number for Price!").show();
                return;
            }

            String pawnType = cmbpawntype.getValue().trim();
            String loanId = txtreceipno.getText().trim();
            String item = cmbitem.getValue().trim();
            String startdate = dpdate.getValue().toString();
            String CT = cmbCT.getValue().trim();
            String loanMonth = cmbmonth.getValue().trim();

            PawnLoanDTO pawnLoanDTO = new PawnLoanDTO(
                    pawnType, loanId, item, startdate, weight, price, CT, loanMonth
            );

            if (pawnLoanBO.savePawnLoanItem(pawnLoanDTO)) {
                pawnLoanList.add(pawnLoanDTO);
                tblpawnloanitem.refresh();
                calculateTotals();
                clearItemFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Pawn Loan").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSavePawnDetails(){
        try {

            if (txtreceipno.getText() == null || txtreceipno.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Receipt number is required!").show();
                return;
            }

            if (txtcustomername.getText() == null || txtcustomername.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Customer name is required!").show();
                return;
            }

            if (txtnic.getText() == null || txtnic.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "NIC is required!").show();
                return;
            }

            if (pawnLoanList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please add at least one item before saving!").show();
                return;
            }

            String loanId = txtreceipno.getText().trim();
            String CustomeName = txtcustomername.getText().trim();
            String nic = txtnic.getText().trim();
            String phone = txtphone.getText().trim();
            String Adress = txtadress.getText().trim();
            double weight = Double.parseDouble(txttotalweight.getText().trim());
            double price = Double.parseDouble(txttotal.getText().trim());
            double satmpfee = Double.parseDouble(txtstampfees.getText().trim());
            double interest = Double.parseDouble(txtcurrentinterest.getText().trim());
            double netvalue = Double.parseDouble(txtnetvalue.getText().trim());

            PawnLoanDTO pawnLoanDTO = new PawnLoanDTO(
                    loanId, CustomeName, nic, phone, Adress, weight, price, satmpfee, interest, netvalue
            );

            if (pawnLoanBO.savePawnLoanDetails(pawnLoanDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "Pawn Loan Details Saved Successfully!").show();
                clearAllFields();

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Pawn Loan Details").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }





    private void clearItemFields() {
        txtweight.clear();
        txtpriec.clear();
        cmbitem.setValue(null);
        dpdate.setValue(null);
        cmbCT.setValue(null);
    }

    private void clearAllFields() {

        clearItemFields();


        txtcustomername.clear();
        txtnic.clear();
        txtphone.clear();
        txtadress.clear();


        txttotalweight.clear();
        txttotal.clear();
        txtnetvalue.clear();
        txtmarketvalue.clear();


        txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
        txtcurrentinterest.setText(String.format("%.2f", DEFAULT_INTEREST_RATE * 100));


        pawnLoanList.clear();


        cmbpawntype.setValue(null);
        cmbmonth.setValue(null);
        txtreceipno.clear();
    }

    @FXML
    void NicOnAction(){
        try{
            String Nic = txtnic.getText();

            if (Nic == null || Nic.trim().isEmpty()) {
                return;
            }

            CustomerDTO dto = pawnLoanBO.getCustomerDetails(Nic);

            if (dto != null) {
                txtcustomername.setText(dto.getFirstName() + " " + dto.getLastName());
                txtphone.setText(dto.getPhoneNumber());
                txtadress.setText(dto.getAddress());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found with NIC: " + Nic).show();
            }
        } catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void PhoneOnAction(){
        try{
            String phone = txtphone.getText();

            if (phone == null || phone.trim().isEmpty()) {
                return;
            }

            CustomerDTO dto = pawnLoanBO.getCustomerDetail(phone);

            if (dto != null) {
                txtcustomername.setText(dto.getFirstName() + " " + dto.getLastName());
                txtnic.setText(dto.getNicNumber());
                txtadress.setText(dto.getAddress());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found with phone: " + phone).show();
            }
        } catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void closeOnAction(){
        System.out.println("Dashboard Page Loaded");
        App.setRoot("Dashboard");
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