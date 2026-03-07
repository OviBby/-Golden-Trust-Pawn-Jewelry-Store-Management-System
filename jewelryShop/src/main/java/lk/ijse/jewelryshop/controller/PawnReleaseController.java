package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.PawnReleaseBo;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import lk.ijse.jewelryshop.dto.PawnLoanDTO;
import lk.ijse.jewelryshop.dto.PawnLoanReleaseDTO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PawnReleaseController implements Initializable {

    PawnReleaseBo pawnReleaseBo = (PawnReleaseBo) BOFactory.getInstance().getBO(BOFactory.BOType.PAWNRELEASE);

    @FXML private ComboBox<String> cmbpawntype;
    @FXML private TextField txtreceipno;
    @FXML private DatePicker dpdate;
    @FXML private DatePicker dpdate1;
    @FXML private TextField txtcustomername;
    @FXML private TextField txtnic;
    @FXML private TextField txtphone;
    @FXML private TextField txtadress;
    @FXML private TextField txttotal;
    @FXML private TextField txtstampfees;
    @FXML private TextField txtcurrentinterest;
    @FXML private TextField txtnetvalue;
    @FXML private TableView<PawnLoanDTO> tblpawnloanitem;
    @FXML private TableColumn<PawnLoanDTO, String> colid;
    @FXML private TableColumn<PawnLoanDTO, String> colitem;
    @FXML private TableColumn<PawnLoanDTO, Double> colweight;
    @FXML private TableColumn<PawnLoanDTO, String> colCT;
    @FXML private TableColumn<PawnLoanDTO, Double> colprice;

    private static final double DEFAULT_STAMP_FEE = 5.0;
    private static final double INTEREST_RATE = 0.03;

    private String currentCustomerId = null;
    private String currentReleaseId = null;

    ObservableList<PawnLoanDTO> pawnLoanList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (txttotal != null) txttotal.setEditable(false);
        if (txtnetvalue != null) txtnetvalue.setEditable(false);
        if (txtcurrentinterest != null) {
            txtcurrentinterest.setEditable(false);
            txtcurrentinterest.setText("0.00");
        }
        if (txtstampfees != null) {
            txtstampfees.setEditable(true);
            txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
        }

        if (colid != null) colid.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        if (colitem != null) colitem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        if (colweight != null) colweight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        if (colCT != null) colCT.setCellValueFactory(new PropertyValueFactory<>("goldCarat"));
        if (colprice != null) colprice.setCellValueFactory(new PropertyValueFactory<>("pricePerGram"));

        if (tblpawnloanitem != null) tblpawnloanitem.setItems(pawnLoanList);

        if (cmbpawntype != null) {
            cmbpawntype.getItems().addAll("A", "D");
        }

        LocalDate today = LocalDate.now();
        if (dpdate != null) dpdate.setValue(today);
        if (dpdate1 != null) dpdate1.setValue(today);

        setupListeners();
        generateReleaseId();


        if (txtreceipno != null) {
            txtreceipno.setOnAction(event -> loadPawnLoanDetails());
        }
    }

    private void setupListeners() {
        if (txtstampfees != null) {
            txtstampfees.textProperty().addListener((obs, oldVal, newVal) -> recalculateNetValue());
        }
        if (dpdate1 != null) {
            dpdate1.valueProperty().addListener((obs, oldVal, newVal) -> recalculateNetValue());
        }
    }

    private void generateReleaseId() {
        try {
            currentReleaseId = pawnReleaseBo.generateId();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating release ID: " + e.getMessage()).show();
        }
    }

    private void loadPawnLoanDetails() {
        String receiptInput = txtreceipno.getText().trim();
        String pawnType = cmbpawntype.getValue();

        if (receiptInput.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a receipt number!").show();
            return;
        }

        if (pawnType == null || pawnType.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select pawn type (A or D)!").show();
            return;
        }


        String receiptId;
        if (receiptInput.toUpperCase().startsWith(pawnType.toUpperCase())) {
            receiptId = receiptInput.toUpperCase();
        } else {
            receiptId = pawnType.toUpperCase() + receiptInput;
        }

        try {
            if (pawnReleaseBo.isReceiptAlreadyReleased(receiptId)) {
                new Alert(Alert.AlertType.WARNING, "This receipt is already released!").show();
                return;
            }

            ArrayList<PawnLoanDTO> items = pawnReleaseBo.getAll(receiptId);

            if (items.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "No pawn loan found with receipt: " + receiptId).show();
                return;
            }


            try {
                lk.ijse.jewelryshop.util.CrudUtil.execute(
                        "SELECT start_date FROM pawnloan_item_details WHERE loan_id = ? LIMIT 1",
                        receiptId
                );
                java.sql.ResultSet rsItemDate = lk.ijse.jewelryshop.util.CrudUtil.execute(
                        "SELECT start_date FROM pawnloan_item_details WHERE loan_id = ? LIMIT 1",
                        receiptId
                );
                if (rsItemDate.next()) {
                    LocalDate pawnDate = rsItemDate.getDate("start_date").toLocalDate();
                    dpdate.setValue(pawnDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                java.sql.ResultSet rsReceipt = lk.ijse.jewelryshop.util.CrudUtil.execute(
                        "SELECT customer_name, nic_number, phone_number, address FROM pawnLoanReceipt WHERE receipt_id = ?",
                        receiptId
                );
                if (rsReceipt.next()) {
                    String custName = rsReceipt.getString("customer_name");
                    String nic = rsReceipt.getString("nic_number");
                    String phone = rsReceipt.getString("phone_number");
                    String address = rsReceipt.getString("address");

                    if (custName != null) txtcustomername.setText(custName);
                    if (nic != null) txtnic.setText(nic);
                    if (phone != null) txtphone.setText(phone);
                    if (address != null) txtadress.setText(address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            pawnLoanList.clear();
            pawnLoanList.addAll(items);


            double total = items.stream()
                    .mapToDouble(PawnLoanDTO::getPricePerGram)
                    .sum();
            txttotal.setText(String.format("%.2f", total));

            recalculateNetValue();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
    }

    private void recalculateNetValue() {
        if (txttotal != null && !txttotal.getText().isEmpty()) {
            try {
                double total = Double.parseDouble(txttotal.getText());
                calculateNetValue(total);
            } catch (NumberFormatException e) {
                // ignore
            }
        }
    }

    private void calculateNetValue(double totalPrice) {
        if (dpdate.getValue() == null || dpdate1.getValue() == null) {
            if (txtnetvalue != null) txtnetvalue.setText("0.00");
            if (txtcurrentinterest != null) txtcurrentinterest.setText("0.00");
            return;
        }

        long days = ChronoUnit.DAYS.between(dpdate.getValue(), dpdate1.getValue());
        if (days < 0) {
            new Alert(Alert.AlertType.WARNING, "Release date cannot be before pawn date!").show();
            dpdate1.setValue(dpdate.getValue());
            return;
        }

        double months = days / 30.0;

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

        // Compound interest: A = P(1 + r)^n - P
        double totalInterest = totalPrice * (Math.pow(1 + INTEREST_RATE, months) - 1);
        txtcurrentinterest.setText(String.format("%.2f", totalInterest));

        double netValue = totalPrice + stampFee + totalInterest;
        if (txtnetvalue != null) txtnetvalue.setText(String.format("%.2f", netValue));
    }

    @FXML
    void btnSavePawnDetails() {
        try {
            if (txtreceipno.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter receipt number!").show();
                return;
            }

            if (cmbpawntype.getValue() == null || cmbpawntype.getValue().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select pawn type!").show();
                return;
            }

            if (pawnLoanList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "No pawn items loaded!").show();
                return;
            }

            if (txtcustomername.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Customer details not loaded!").show();
                return;
            }


            String receiptInput = txtreceipno.getText().trim();
            String pawnType = cmbpawntype.getValue();
            String receiptId;
            if (receiptInput.toUpperCase().startsWith(pawnType.toUpperCase())) {
                receiptId = receiptInput.toUpperCase();
            } else {
                receiptId = pawnType.toUpperCase() + receiptInput;
            }

            String customerName = txtcustomername.getText().trim();
            String nicNumber = txtnic.getText().trim();
            String phoneNumber = txtphone.getText().trim();
            String address = txtadress.getText().trim();

            StringBuilder itemNames = new StringBuilder();
            double totalWeight = 0.0;
            for (PawnLoanDTO item : pawnLoanList) {
                if (itemNames.length() > 0) itemNames.append(", ");
                itemNames.append(item.getItemName());
                totalWeight += item.getWeight();
            }

            double totalAmount = Double.parseDouble(txttotal.getText().trim());
            double stampFees = Double.parseDouble(txtstampfees.getText().trim());
            double currentInterest = Double.parseDouble(txtcurrentinterest.getText().trim());
            double netValue = Double.parseDouble(txtnetvalue.getText().trim());
            LocalDate releaseDate = dpdate1.getValue();

            PawnLoanReleaseDTO pawnLoanReleaseDTO = new PawnLoanReleaseDTO(
                    currentReleaseId,
                    receiptId,
                    currentCustomerId,
                    customerName,
                    nicNumber,
                    phoneNumber,
                    address,
                    itemNames.toString(),
                    totalWeight,
                    totalAmount,
                    stampFees,
                    currentInterest,
                    netValue,
                    releaseDate
            );

            if (pawnReleaseBo.save(pawnLoanReleaseDTO)) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Pawn release saved successfully!\nRelease ID: " + currentReleaseId).show();
                clearAllFields();
                generateReleaseId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void NicOnAction() {
        try {
            String nic = txtnic.getText().trim();
            if (nic.isEmpty()) return;

            CustomerDTO dto = pawnReleaseBo.getCustomerByNic(nic);
            if (dto != null) {
                currentCustomerId = dto.getCustomerId();
                txtcustomername.setText(dto.getFirstName() + " " + dto.getLastName());
                txtphone.setText(dto.getPhoneNumber());
                txtadress.setText(dto.getAddress());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found with NIC: " + nic).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void PhoneOnAction() {
        try {
            String phone = txtphone.getText().trim();
            if (phone.isEmpty()) return;

            CustomerDTO dto = pawnReleaseBo.getCustomerByPhone(phone);
            if (dto != null) {
                currentCustomerId = dto.getCustomerId();
                txtcustomername.setText(dto.getFirstName() + " " + dto.getLastName());
                txtnic.setText(dto.getNicNumber());
                txtadress.setText(dto.getAddress());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found with phone: " + phone).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void clearAllFields() {
        if (txtcustomername != null) txtcustomername.clear();
        if (txtnic != null) txtnic.clear();
        if (txtphone != null) txtphone.clear();
        if (txtadress != null) txtadress.clear();
        if (txttotal != null) txttotal.clear();
        if (txtnetvalue != null) txtnetvalue.clear();
        if (txtstampfees != null) txtstampfees.setText(String.format("%.2f", DEFAULT_STAMP_FEE));
        if (txtcurrentinterest != null) txtcurrentinterest.setText("0.00");

        pawnLoanList.clear();
        currentCustomerId = null;

        if (cmbpawntype != null) cmbpawntype.setValue(null);
        if (txtreceipno != null) txtreceipno.clear();

        LocalDate today = LocalDate.now();
        if (dpdate != null) dpdate.setValue(today);
        if (dpdate1 != null) dpdate1.setValue(today);
    }

    @FXML void closeOnAction() { App.setRoot("Dashboard"); }
    @FXML void dashbordOnAction() { App.setRoot("Dashboard"); }
    @FXML void customerOnAction() { App.setRoot("customer"); }
    @FXML void orderOnAction() { App.setRoot("order"); }
    @FXML void jewelryItemOnAction() { App.setRoot("item"); }
    @FXML void pawnLoanOnAction() { App.setRoot("pawnloan"); }
    @FXML void pawnItemOnAction() { App.setRoot("pawanreles"); }
    @FXML void purchaseOnAction() { App.setRoot("reprots"); }
    @FXML void repairItemOnAction() { App.setRoot("repairitem"); }
    @FXML void repairDetailsOnAction() { App.setRoot("repairdetails"); }

}