package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.BOFactory;
import lk.ijse.jewelryshop.bo.custom.ItemBO;
import lk.ijse.jewelryshop.dto.ItemDTO;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    //ItemDAOImpl  itemDAO = new ItemDAOImpl();
    ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtWeigth;

    @FXML
    private TextField txtItemPrice;

    @FXML
    private TextField txtStatus;


    @FXML
    private TextField txtItemPrice1;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbPurity;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<ItemDTO> tblItems;

    @FXML
    private TableColumn<ItemDTO, String> colItemId;

    @FXML
    private TableColumn<ItemDTO, String> colDate;

    @FXML
    private TableColumn<ItemDTO, String> colDescription;

    @FXML
    private TableColumn<ItemDTO, String> colMetalType;

    @FXML
    private TableColumn<ItemDTO, String> colPurity;

    @FXML
    private TableColumn<ItemDTO, Double> colWeight;

    @FXML
    private TableColumn<ItemDTO, Integer> colQty;

    @FXML
    private TableColumn<ItemDTO, Double> colPrice;

    private ObservableList<ItemDTO> items = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCategory.setItems(FXCollections.observableArrayList("Gold","Diamond","Silver"));
        cmbPurity.setItems(FXCollections.observableArrayList("24K","22K","21K","18K","14K","10K"));


        LocalDate today = LocalDate.now();
        if (dpDate != null) {
            dpDate.setValue(today);
        }

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        colMetalType.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        colPurity.setCellValueFactory(new PropertyValueFactory<>("purity"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weightGrams"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblItems.setItems(items);

        loadItems();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String desc = newValue.getDescription();
                String[] parts = desc.split(" - ", 2);
                txtItemName.setText(parts[0]);
                txtItemPrice1.setText(parts.length > 1 ? parts[1] : "");
                cmbCategory.setValue(newValue.getMetalType());
                cmbPurity.setValue(newValue.getPurity());
                txtWeigth.setText(String.valueOf(newValue.getWeightGrams()));
                txtQuantity.setText(String.valueOf(newValue.getQty()));
                txtItemPrice.setText(String.valueOf(newValue.getUnitPrice()));
                dpDate.setValue(LocalDate.parse(newValue.getAddedDate()));
                txtStatus.setText(newValue.getCurrentStockStatus());
            }
        });
    }

    private void loadItems() {
        try {
            ArrayList<ItemDTO> all = itemBO.getAll();
            items.clear();
            items.addAll(all);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading items: " + e.getMessage()).show();
        }
    }

    @FXML
    void buttonAddItemOnAction(ActionEvent event) {
        if (cmbCategory.getValue() == null || cmbPurity.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select category and purity.").show();
            return;
        }

        try {
            String itemId = itemBO.generateId();
            String itemName = txtItemName.getText().trim();
            String descDetail = txtItemPrice1.getText().trim();
            String description = itemName + (descDetail.isEmpty() ? "" : " - " + descDetail);
            String metalType = cmbCategory.getValue().trim();
            double weightGrams = Double.parseDouble(txtWeigth.getText().trim());
            String purity = cmbPurity.getValue().trim();
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double unitPrice = Double.parseDouble(txtItemPrice.getText().trim());
            double price = unitPrice * qty;
            String addedDate = dpDate.getValue() != null ? dpDate.getValue().toString() : LocalDate.now().toString();
            String currentStockStatus = txtStatus.getText().trim();

            ItemDTO dto = new ItemDTO(itemId, description, metalType, weightGrams, purity, price, addedDate, currentStockStatus, qty, unitPrice);

            boolean added = itemBO.save(dto);
            if (added) {
                items.add(dto);
                new Alert(Alert.AlertType.INFORMATION, "Item added successfully.").show();
                clearFields();
                loadItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add item.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format: " + e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error adding item: " + e.getMessage()).show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        ItemDTO selected = tblItems.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item to update.").show();
            return;
        }

        try {
            String itemId = selected.getItemId();
            String itemName = txtItemName.getText().trim();
            String descDetail = txtItemPrice1.getText().trim();
            String description = itemName + (descDetail.isEmpty() ? "" : " - " + descDetail);
            String metalType = cmbCategory.getValue().trim();
            double weightGrams = Double.parseDouble(txtWeigth.getText().trim());
            String purity = cmbPurity.getValue().trim();
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double unitPrice = Double.parseDouble(txtItemPrice.getText().trim());
            double price = unitPrice * qty;
            String addedDate = dpDate.getValue() != null ? dpDate.getValue().toString() : LocalDate.now().toString();
            String currentStockStatus = txtStatus.getText().trim();

            ItemDTO dto = new ItemDTO(itemId, description, metalType, weightGrams, purity, price, addedDate,
                    currentStockStatus, qty, unitPrice);

            boolean updated = itemBO.update(dto);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Item updated successfully.").show();
                loadItems();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update item.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format: " + e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String query = txtItemName.getText().trim(); // Using txtItemName as search query for description
        try {
            ArrayList<ItemDTO> searched = itemBO.search(query);
            items.clear();
            items.addAll(searched);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error searching items: " + e.getMessage()).show();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        ItemDTO selected = tblItems.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item to delete.").show();
            return;
        }

        try {
            boolean deleted = itemBO.delete(selected.getItemId());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully.").show();
                loadItems();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete item.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtItemName.clear();
        txtQuantity.clear();
        txtWeigth.clear();
        txtItemPrice.clear();
        txtStatus.clear();
        txtItemPrice1.clear();
        cmbCategory.setValue(null);
        cmbPurity.setValue(null);
        dpDate.setValue(null);
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



    @FXML
    public void printOnAction(javafx.event.ActionEvent actionEvent) {
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
}