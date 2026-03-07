package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.RepairItemBO;
import lk.ijse.jewelryshop.dto.RepairItemDTO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RepairItemController implements Initializable {

    RepairItemBO repairItemBO = (RepairItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.REPAIRITEM);

    @FXML private TextField txtrepairitemid;
    @FXML private TextField txtrepairerName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtcondition;
    @FXML private TextField txtweights;
    @FXML private TextField txtdescription;
    @FXML private TextField txtadvancepayment;
    @FXML private DatePicker dpdate;
    @FXML private ComboBox<String> cmbmetaltype;
    @FXML private TableView<RepairItemDTO> tbrepairs;
    @FXML private TableColumn<RepairItemDTO, String> colrepairid;
    @FXML private TableColumn<RepairItemDTO, String> colcustomername;
    @FXML private TableColumn<RepairItemDTO, String> colDate;
    @FXML private TableColumn<RepairItemDTO, String> colMetalType;
    @FXML private TableColumn<RepairItemDTO, String> coldes;
    @FXML private TableColumn<RepairItemDTO, Double> colweight;

    private ObservableList<RepairItemDTO> repairitems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbmetaltype.setItems(FXCollections.observableArrayList("Gold", "Diamond", "Silver"));

        LocalDate today = LocalDate.now();
        if (dpdate != null) dpdate.setValue(today);

        colrepairid.setCellValueFactory(new PropertyValueFactory<>("repairItemId"));
        colcustomername.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        colMetalType.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        coldes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colweight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tbrepairs.setItems(repairitems);
        loadAllRepairItem();

        try {
            String nextRepairID = repairItemBO.generateId();
            txtrepairitemid.setText(nextRepairID);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate ID: " + e.getMessage()).show();
        }
    }

    @FXML
    void tableOnMouseClicked(javafx.scene.input.MouseEvent event) {
        RepairItemDTO selectedItem = tbrepairs.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;

        txtrepairitemid.setText(selectedItem.getRepairItemId());
        txtrepairerName.setText(selectedItem.getCustomerName());
        txtPhone.setText(selectedItem.getPhoneNumber() != null ? selectedItem.getPhoneNumber() : "");
        txtcondition.setText(selectedItem.getCurrentCondition() != null ? selectedItem.getCurrentCondition() : "");
        cmbmetaltype.setValue(selectedItem.getMetalType());
        txtweights.setText(String.valueOf(selectedItem.getWeight()));
        txtdescription.setText(selectedItem.getDescription() != null ? selectedItem.getDescription() : "");
        txtadvancepayment.setText(String.valueOf(selectedItem.getAdvancePayment()));

        if (selectedItem.getReceivedDate() != null) {
            try {
                dpdate.setValue(LocalDate.parse(selectedItem.getReceivedDate()));
            } catch (Exception e) {
                dpdate.setValue(LocalDate.now());
            }
        }
    }

    private void loadAllRepairItem() {
        try {
            repairitems.clear();
            repairitems.addAll(repairItemBO.getAll());
            tbrepairs.refresh();
            System.out.println("Total Repair Items loaded: " + repairitems.size());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Repair Items: " + e.getMessage()).show();
        }
    }

    @FXML
    void phoneOnAction() {
        try {
            String phone = txtPhone.getText();
            if (phone == null || phone.trim().isEmpty()) return;

            String customerName = repairItemBO.getCustomerDetails(phone);
            if (customerName != null) {
                txtrepairerName.setText(customerName);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found for phone: " + phone).show();
                txtrepairerName.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddOnAction() {
        try {

            if (txtrepairerName.getText() == null || txtrepairerName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Customer Name!").show();
                return;
            }
            if (txtPhone.getText() == null || txtPhone.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Phone Number!").show();
                return;
            }
            if (cmbmetaltype.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Metal Type!").show();
                return;
            }
            if (txtweights.getText() == null || txtweights.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Weight!").show();
                return;
            }
            if (txtadvancepayment.getText() == null || txtadvancepayment.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Advance Payment!").show();
                return;
            }
            if (dpdate.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a Date!").show();
                return;
            }

            String repairItemId = txtrepairitemid.getText().trim();
            String currentCondition = txtcondition.getText() != null ? txtcondition.getText().trim() : "";
            String metalType = cmbmetaltype.getValue().trim();
            double weightGrams = Double.parseDouble(txtweights.getText().trim());
            String description = txtdescription.getText() != null ? txtdescription.getText().trim() : "";
            String receivedDate = dpdate.getValue().toString();
            double advancePayment = Double.parseDouble(txtadvancepayment.getText().trim());
            String customerName = txtrepairerName.getText().trim();
            String phone = txtPhone.getText().trim();

            RepairItemDTO repairItemDTO = new RepairItemDTO(
                    repairItemId, currentCondition, metalType, weightGrams,
                    description, receivedDate, advancePayment, customerName, phone
            );

            if (repairItemBO.save(repairItemDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "Repair Item Added Successfully!").show();
                cleanFields();
                loadAllRepairItem();


                txtrepairitemid.setText(repairItemBO.generateId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric input!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction() {
        try {
            String repairItemId = txtrepairitemid.getText();
            if (repairItemId == null || repairItemId.trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Enter Repair Item ID to search!").show();
                return;
            }

            RepairItemDTO repairItemDTO = repairItemBO.selectRepairItemById(repairItemId.trim());
            if (repairItemDTO != null) {
                txtcondition.setText(repairItemDTO.getCurrentCondition() != null ? repairItemDTO.getCurrentCondition() : "");
                cmbmetaltype.setValue(repairItemDTO.getMetalType());
                txtweights.setText(String.valueOf(repairItemDTO.getWeight()));
                txtdescription.setText(repairItemDTO.getDescription() != null ? repairItemDTO.getDescription() : "");
                txtadvancepayment.setText(String.valueOf(repairItemDTO.getAdvancePayment()));
                txtrepairerName.setText(repairItemDTO.getCustomerName() != null ? repairItemDTO.getCustomerName() : "");
                txtPhone.setText(repairItemDTO.getPhoneNumber() != null ? repairItemDTO.getPhoneNumber() : "");

                if (repairItemDTO.getReceivedDate() != null) {
                    try {
                        dpdate.setValue(LocalDate.parse(repairItemDTO.getReceivedDate()));
                    } catch (Exception e) {
                        dpdate.setValue(LocalDate.now());
                    }
                }
                new Alert(Alert.AlertType.INFORMATION, "Repair Item Found!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Repair Item Not Found!").show();
                cleanFields();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdate() {
        try {

            if (txtrepairitemid.getText() == null || txtrepairitemid.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select a Repair Item to update!").show();
                return;
            }
            if (txtrepairerName.getText() == null || txtrepairerName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Customer Name!").show();
                return;
            }
            if (txtPhone.getText() == null || txtPhone.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Phone Number!").show();
                return;
            }
            if (cmbmetaltype.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select Metal Type!").show();
                return;
            }
            if (txtweights.getText() == null || txtweights.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Weight!").show();
                return;
            }
            if (txtadvancepayment.getText() == null || txtadvancepayment.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Advance Payment!").show();
                return;
            }
            if (dpdate.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a Date!").show();
                return;
            }

            String repairItemId = txtrepairitemid.getText().trim();
            String currentCondition = txtcondition.getText() != null ? txtcondition.getText().trim() : "";
            String metalType = cmbmetaltype.getValue().trim();
            double weightGrams = Double.parseDouble(txtweights.getText().trim());
            String description = txtdescription.getText() != null ? txtdescription.getText().trim() : "";
            String receivedDate = dpdate.getValue().toString();
            double advancePayment = Double.parseDouble(txtadvancepayment.getText().trim());
            String customerName = txtrepairerName.getText().trim();
            String phone = txtPhone.getText().trim();

            RepairItemDTO repairItemDTO = new RepairItemDTO(
                    repairItemId, currentCondition, metalType, weightGrams,
                    description, receivedDate, advancePayment, customerName, phone
            );

            if (repairItemBO.update(repairItemDTO)) {
                new Alert(Alert.AlertType.INFORMATION, "Repair Item Updated Successfully!").show();
                cleanFields();
                loadAllRepairItem();


                txtrepairitemid.setText(repairItemBO.generateId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric input!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    private void cleanFields() {
        txtrepairitemid.clear();
        txtcondition.clear();
        cmbmetaltype.setValue(null);
        txtweights.clear();
        txtdescription.clear();
        txtadvancepayment.clear();
        dpdate.setValue(LocalDate.now());
        txtrepairerName.clear();
        txtPhone.clear();
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