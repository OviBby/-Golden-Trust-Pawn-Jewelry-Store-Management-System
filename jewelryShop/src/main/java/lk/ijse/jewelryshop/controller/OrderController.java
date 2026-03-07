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
import lk.ijse.jewelryshop.dto.ItemDTO;
import lk.ijse.jewelryshop.dto.OrderDTO;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);

    @FXML
    private TextField txtNic;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<String> cmbTransactionType;
    @FXML
    private TextField txtOrderId;
    @FXML
    private TextField txtItemCode;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtWeight;
    @FXML
    private TextField txtPurity;
    @FXML
    private TextField txtSubTotal;
    @FXML
    private TextField txtOrderqty;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TableView<OrderDTO> tblOrderItems;
    @FXML
    private TableColumn<OrderDTO, String> colOrderId;
    @FXML
    private TableColumn<OrderDTO, String> colitemDescription;
    @FXML
    private TableColumn<OrderDTO, String> colMetalType;
    @FXML
    private TableColumn<OrderDTO, Double> colWeight;
    @FXML
    private TableColumn<OrderDTO, Integer> colqty;
    @FXML
    private TableColumn<OrderDTO, Double> colUprice;
    @FXML
    private TableColumn<OrderDTO, Double> colprice;
    @FXML
    private Label lblTotal;

    ObservableList<OrderDTO> ordersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTransactionType.setItems(FXCollections.observableArrayList("CASH", "CARD"));

        LocalDate today = LocalDate.now();
        if (dpdate != null) dpdate.setValue(today);

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colitemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMetalType.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUprice.setCellValueFactory(new PropertyValueFactory<>("uprice"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblOrderItems.setItems(ordersList);

        try {
            txtOrderId.setText(orderBO.generateId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddOnAction() {
        try {

            if (txtItemCode.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Item Code!").show();
                return;
            }
            if (txtOrderqty.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Quantity!").show();
                return;
            }
            if (txtNic.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Customer NIC!").show();
                return;
            }

            String orderId = txtOrderId.getText().trim();
            String itemCode = txtItemCode.getText().trim();
            String description = txtDescription.getText().trim();
            double weight = Double.parseDouble(txtWeight.getText().trim());
            int qty = Integer.parseInt(txtOrderqty.getText().trim());
            double uprice = Double.parseDouble(txtUnitPrice.getText().trim());
            double price = qty * uprice;
            String date = dpdate.getValue().toString();
            String customerName = txtCustomerName.getText().trim();
            String nic = txtNic.getText().trim();


            OrderDTO dto = new OrderDTO(
                    orderId, itemCode, description, weight, qty, uprice, price
            );
            dto.setDate(date);
            dto.setCustomername(customerName);
            dto.setNic(nic);

            if (orderBO.save(dto)) {
                ordersList.add(dto);
                calculateTotal();
                new Alert(Alert.AlertType.INFORMATION, "Order Item Added Successfully!").show();
                clearItemFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Item Addition Failed!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void calculateTotal() {
        double total = 0.0;
        for (OrderDTO order : ordersList) {
            total += order.getPrice();
        }
        if (lblTotal != null) {
            lblTotal.setText(String.format("Rs. %.2f", total));
        }
    }

    private void clearItemFields() {
        txtItemCode.clear();
        txtDescription.clear();
        txtWeight.clear();
        txtQty.clear();
        txtOrderqty.clear();
        txtUnitPrice.clear();
        txtPurity.clear();
        txtSubTotal.clear();
    }

    private void clearFields() {
        clearItemFields();
        txtNic.clear();
        txtCustomerName.clear();
        ordersList.clear();
        calculateTotal();
        dpdate.setValue(LocalDate.now());

        try {
            txtOrderId.setText(orderBO.generateId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void nicOnAction() {
        try {
            String nic = txtNic.getText().trim();
            if (nic.isEmpty()) return;

            String customerName = orderBO.getCustomer(nic);
            if (customerName != null) {
                txtCustomerName.setText(customerName);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!").show();
                txtCustomerName.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void ItemIdOnAction() {
        try {
            String itemCode = txtItemCode.getText().trim();
            if (itemCode.isEmpty()) return;

            List<ItemDTO> itemList = orderBO.getItem(itemCode);
            if (!itemList.isEmpty()) {
                ItemDTO item = itemList.get(0);
                txtDescription.setText(item.getDescription());
                txtQty.setText(String.valueOf(item.getQty()));
                txtWeight.setText(String.valueOf(item.getWeightGrams()));
                txtPurity.setText(item.getPurity());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Not Found!").show();
                txtDescription.clear();
                txtQty.clear();
                txtWeight.clear();
                txtPurity.clear();
                txtUnitPrice.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void qtyOnAction() {
        try {
            int qty = Integer.parseInt(txtOrderqty.getText().trim());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText().trim());
            txtSubTotal.setText(String.format("%.2f", qty * unitPrice));
        } catch (NumberFormatException e) {
            txtSubTotal.setText("0.00");
        }
    }

    @FXML
    void btnPlaceOrderOnAction() {
        if (ordersList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please add items to the order!").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to place this order?\nTotal: " + lblTotal.getText(),
                ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Order placed successfully!\nReceipt No: " + txtOrderId.getText() +
                            "\nTotal: " + lblTotal.getText()).show();
            clearFields();
        }
    }

    @FXML
    void btnClearOnAction() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to clear all fields?",
                ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.YES) {
            clearFields();
        }
    }

    @FXML
    public void printOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            orderBO.printReport();
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Report Error: " + e.getMessage()).showAndWait();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void closeOnAction() {
        App.setRoot("Dashboard");
    }

    @FXML
    void dashbordOnAction() {
        App.setRoot("Dashboard");
    }

    @FXML
    void customerOnAction() {
        App.setRoot("customer");
    }

    @FXML
    void orderOnAction() {
        App.setRoot("order");
    }

    @FXML
    void jewelryItemOnAction() {
        App.setRoot("item");
    }

    @FXML
    void pawnLoanOnAction() {
        App.setRoot("pawnloan");
    }

    @FXML
    void pawnItemOnAction() {
        App.setRoot("pawanreles");
    }

    @FXML
    void purchaseOnAction() {
        App.setRoot("reprots");
    }

    @FXML
    void repairItemOnAction() {
        App.setRoot("repairitem");
    }

    @FXML
    void repairDetailsOnAction() {
        App.setRoot("repairdetails");
    }
}
