package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.CustomerBO;
import lk.ijse.jewelryshop.dto.CustomerDTO;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerController {

    //CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtNic;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField searchId;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TableView<CustomerDTO> tblCustomerItems;
    @FXML
    private TableColumn<CustomerDTO, String> colCustomerId;
    @FXML
    private TableColumn<CustomerDTO, String> colFirstName;
    @FXML
    private TableColumn<CustomerDTO, String> colLastName;
    @FXML
    private TableColumn<CustomerDTO, String> colPhone;
    @FXML
    private TableColumn<CustomerDTO, String> colNic;

    private final ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("Controller Initialized");

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nicNumber"));


        tblCustomerItems.setItems(customerList);

        loadAllCustomers();

        System.out.println("Customers loaded: " + customerList.size());
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (!validateInputs()) {
            return;
        }

        try {
            if (customerBO.isCustomerExists(txtNic.getText().trim())) {
                new Alert(Alert.AlertType.WARNING, "Customer with this NIC already exists!").show();
                return;
            }


            if (customerBO.isPhoneExists(txtPhone.getText().trim())) {
                new Alert(Alert.AlertType.WARNING, "Customer with this phone number already exists!").show();
                return;
            }

            CustomerDTO dto = new CustomerDTO(
                    txtFirstName.getText().trim(),
                    txtLastName.getText().trim(),
                    txtAddress.getText().trim(),
                    txtNic.getText().trim(),
                    txtPhone.getText().trim(),
                    txtEmail.getText().trim()
            );

            if (customerBO.saveCustomer(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!\nCustomer ID: " + dto.getCustomerId()).show();
                clearFields();
                loadAllCustomers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save customer!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String value = searchId.getText().trim();

        if (value.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter NIC or Phone number to search!").show();
            return;
        }

        try {
            CustomerDTO dto = customerBO.searchCustomer(value);

            if (dto != null) {
                fillForm(dto);
                new Alert(Alert.AlertType.INFORMATION, "Customer found!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No customer found with that NIC or Phone!").show();
                clearFields();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerDTO selected = tblCustomerItems.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer from the table first!").show();
            return;
        }


        if (!validateInputs()) {
            return;
        }

        CustomerDTO dto = new CustomerDTO(
                selected.getCustomerId(),
                txtFirstName.getText().trim(),
                txtLastName.getText().trim(),
                txtAddress.getText().trim(),
                txtNic.getText().trim(),
                txtPhone.getText().trim(),
                txtEmail.getText().trim()
        );

        try {
            if (customerBO.updateCustomer(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                clearFields();
                loadAllCustomers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update customer!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        CustomerDTO selected = tblCustomerItems.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete!").show();
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete customer: " + selected.getFirstName() + " " + selected.getLastName() + "?",
                ButtonType.YES, ButtonType.NO
        );

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                if (customerBO.deleteCustomer(selected.getCustomerId())) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                    clearFields();
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete customer!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cleanFields(ActionEvent event) {
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtNic.clear();
        txtPhone.clear();
        txtEmail.clear();

    }

    private void loadAllCustomers() {
        try {
            customerList.clear();
            customerList.addAll(customerBO.getAllCustomer());
            tblCustomerItems.refresh();
            System.out.println("Total customers loaded: " + customerList.size());


            if (!customerList.isEmpty()) {
                System.out.println("First customer: " + customerList.get(0).toString());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customers: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void fillForm(CustomerDTO dto) {
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtAddress.setText(dto.getAddress());
        txtNic.setText(dto.getNicNumber());
        txtPhone.setText(dto.getPhoneNumber());
        txtEmail.setText(dto.getEmail());
    }

    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtNic.clear();
        txtPhone.clear();
        txtEmail.clear();
        searchId.clear();
        tblCustomerItems.getSelectionModel().clearSelection();
    }

    @FXML
    private void tableOnMouseClicked() {
        CustomerDTO selected = tblCustomerItems.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fillForm(selected);
        }
    }

    private boolean validateInputs() {
        if (txtFirstName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "First Name is required!").show();
            txtFirstName.requestFocus();
            return false;
        }

        if (txtLastName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Last Name is required!").show();
            txtLastName.requestFocus();
            return false;
        }

        if (txtNic.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "NIC Number is required!").show();
            txtNic.requestFocus();
            return false;
        }

        if (txtPhone.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Phone Number is required!").show();
            txtPhone.requestFocus();
            return false;
        }

        String nic = txtNic.getText().trim();
        if (!nic.matches("^([0-9]{9}[vVxX]|[0-9]{12})$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid NIC format!\nFormat: 123456789V or 123456789012").show();
            txtNic.requestFocus();
            return false;
        }

        String phone = txtPhone.getText().trim();
        if (!phone.matches("^0[0-9]{9}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid phone number!\nFormat: 0XXXXXXXXX (10 digits)").show();
            txtPhone.requestFocus();
            return false;
        }

        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid email format!").show();
            txtEmail.requestFocus();
            return false;
        }

        return true;
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




    public void printOnAction(javafx.event.ActionEvent actionEvent) {
        try{
            customerBO.printCustomerReport();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

