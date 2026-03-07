package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.RepairDetailsBO;
import lk.ijse.jewelryshop.dto.RepairDetailsDTO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RepairDetailsController implements Initializable {

    RepairDetailsBO repairDetailsBO = (RepairDetailsBO) BOFactory.getInstance().getBO(BOFactory.BOType.REPAIRDETAILS);

    @FXML private TextField txtrepairId;
    @FXML private TextField txtcustomerName;
    @FXML private TextField txtdesc;
    @FXML private TextField txtstatus;
    @FXML private TextField txtestimatedCost1;
    @FXML private TextField txtestimatedCost;
    @FXML private DatePicker datepick;
    @FXML private TextField txtid;
    @FXML private TextField txtname;
    @FXML private TextField txtdescription;
    @FXML private TextField txtadpayment;
    @FXML private TextField txtbalance;
    @FXML private DatePicker dpdate;
    @FXML private TableView<RepairDetailsDTO> repairDetailsTable;
    @FXML private TableColumn<RepairDetailsDTO, String> colId;
    @FXML private TableColumn<RepairDetailsDTO, String> cloname;
    @FXML private TableColumn<RepairDetailsDTO, String> coldesc;
    @FXML private TableColumn<RepairDetailsDTO, String> colsumitdate;
    @FXML private TableColumn<RepairDetailsDTO, String> colstatus;
    @FXML private TableColumn<RepairDetailsDTO, Integer> colCost;

    private ObservableList<RepairDetailsDTO> repairDetailsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LocalDate today = LocalDate.now();
        if (dpdate != null) dpdate.setValue(today);
        if (datepick != null) datepick.setValue(today);

        colId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        cloname.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("repairDescription"));
        colsumitdate.setCellValueFactory(new PropertyValueFactory<>("submittedDate"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("estimatedCost"));

        repairDetailsTable.setItems(repairDetailsList);
        loadAllRepairItems();

        txtname.setEditable(false);
        txtdescription.setEditable(false);
        txtadpayment.setEditable(false);
        txtbalance.setEditable(false);

        txtestimatedCost.textProperty().addListener((observable, oldValue, newValue) -> calculateBalance());
    }

    private void loadAllRepairItems() {
        try {
            repairDetailsList.clear();
            repairDetailsList.addAll(repairDetailsBO.getAll());
            repairDetailsTable.refresh();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()).show();
        }
    }

    @FXML
    private void calculateBalance() {
        try {
            String estimatedCostStr = txtestimatedCost.getText().trim();
            String advancePaymentStr = txtadpayment.getText().trim();

            if (!estimatedCostStr.isEmpty() && !advancePaymentStr.isEmpty()) {
                Double estimatedCost = Double.parseDouble(estimatedCostStr);
                Double advancePayment = Double.parseDouble(advancePaymentStr);

                Double balance = repairDetailsBO.calculateBalance(estimatedCost, advancePayment);
                txtbalance.setText(String.format("%.2f", balance));

                if (balance < 0) {
                    txtbalance.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                } else if (balance == 0) {
                    txtbalance.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    txtbalance.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                }
            } else {
                txtbalance.setText("0.00");
                txtbalance.setStyle("-fx-text-fill: black;");
            }
        } catch (NumberFormatException e) {
            txtbalance.setText("0.00");
            txtbalance.setStyle("-fx-text-fill: black;");
        }
    }

    @FXML
    void repairIdOnAction() {
        try {
            String repairId = txtrepairId.getText().trim();
            if (repairId.isEmpty()) return;

            RepairItemDTO item = repairDetailsBO.getRepairItemDetails(repairId);
            if (item != null) {
                txtcustomerName.setText(item.getCustomerName());
                txtdesc.setText(item.getDescription());
            } else {
                new Alert(Alert.AlertType.WARNING, "No item found for ID: " + repairId).show();
                txtcustomerName.clear();
                txtdesc.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void itemDetailsOnAction() {
        try {
            String itemId = txtid.getText().trim();
            if (itemId.isEmpty()) return;

            RepairItemDTO item = repairDetailsBO.getItemDetails(itemId);
            if (item != null) {
                txtname.setText(item.getCustomerName());
                txtdescription.setText(item.getDescription());
                txtadpayment.setText(String.valueOf(item.getAdvancePayment()));
                calculateBalance();
            } else {
                new Alert(Alert.AlertType.WARNING, "No item found for ID: " + itemId).show();
                txtname.clear();
                txtdescription.clear();
                txtadpayment.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveRepairDetailsAction() {
        try {
            if (txtrepairId.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Repair Item ID!").show();
                return;
            }
            if (txtdesc.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Repair Description!").show();
                return;
            }
            if (txtestimatedCost1.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Estimated Cost!").show();
                return;
            }

            String repairItemId = txtrepairId.getText().trim();
            String customerName = txtcustomerName.getText().trim();
            String submittedDate = datepick.getValue().toString();
            String description = txtdesc.getText().trim();
            String status = txtstatus.getText().trim();
            Double estimatedCost = Double.parseDouble(txtestimatedCost1.getText().trim());

            RepairDetailsDTO dto = new RepairDetailsDTO(
                    repairItemId, customerName, description, submittedDate, status, estimatedCost
            );

            if (repairDetailsBO.save(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Repair Details Saved Successfully!").show();
                cleanFields();
                loadAllRepairItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Repair Details!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Estimated Cost!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchRepairDetailsAction() {
        String repairId = txtid.getText().trim();

        if (repairId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Enter Repair Item ID!").show();
            return;
        }

        try {

            RepairItemDTO itemDTO = repairDetailsBO.getItemDetails(repairId);
            if (itemDTO != null) {
                txtname.setText(itemDTO.getCustomerName());
                txtdescription.setText(itemDTO.getDescription());
                txtadpayment.setText(String.valueOf(itemDTO.getAdvancePayment()));
            } else {
                new Alert(Alert.AlertType.WARNING, "No item found for ID: " + repairId).show();
                return;
            }


            RepairDetailsDTO repairDetailsDTO = repairDetailsBO.search(repairId);
            if (repairDetailsDTO != null) {


                if ("Completed".equalsIgnoreCase(repairDetailsDTO.getStatus())) {
                    new Alert(Alert.AlertType.WARNING,
                            "Repair [" + repairId + "] is already completed! Cannot pay again.").show();
                    txtestimatedCost.clear();
                    txtbalance.clear();
                    return;
                }

                txtestimatedCost.setText(String.valueOf(repairDetailsDTO.getEstimatedCost()));
                calculateBalance();
                new Alert(Alert.AlertType.INFORMATION, "Repair Details Found! Ready for payment.").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "No Repair Details found for ID: " + repairId).show();
                txtestimatedCost.clear();
                txtbalance.clear();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnCompletOnAction() {
        try {
            if (txtid.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Repair Item ID!").show();
                return;
            }
            if (txtestimatedCost.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please search for repair details first!").show();
                return;
            }
            if (dpdate.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a completed date!").show();
                return;
            }
            if (txtadpayment.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please search for item details first!").show();
                return;
            }

            String repairItemId = txtid.getText().trim();
            String customerName = txtname.getText().trim();
            String completedDate = dpdate.getValue().toString();
            String description = txtdescription.getText().trim();
            Double advancePayment = Double.parseDouble(txtadpayment.getText().trim());
            Double estimatedCost = Double.parseDouble(txtestimatedCost.getText().trim());
            Double amount = Double.parseDouble(txtbalance.getText().trim());

            RepairDetailsDTO dto = new RepairDetailsDTO(
                    repairItemId, customerName, completedDate, description,
                    estimatedCost, advancePayment, amount
            );

            if (repairDetailsBO.completeRepair(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Repair Payment Completed Successfully!").show();
                cleanFields();
                loadAllRepairItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to complete Repair Payment!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric input!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void cleanFields() {
        txtrepairId.clear();
        txtcustomerName.clear();
        txtdesc.clear();
        txtestimatedCost1.clear();
        txtstatus.clear();
        txtestimatedCost.clear();
        txtbalance.clear();
        txtadpayment.clear();
        txtid.clear();
        txtname.clear();
        txtdescription.clear();
        txtbalance.setStyle("-fx-text-fill: black;");

        LocalDate today = LocalDate.now();
        datepick.setValue(today);
        dpdate.setValue(today);
    }

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