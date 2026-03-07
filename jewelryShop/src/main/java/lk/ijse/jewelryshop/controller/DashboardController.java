package lk.ijse.jewelryshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.bo.custom.impl.DashboardBOImpl;
import lk.ijse.jewelryshop.dto.RepairItemDTO;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

  //  DashboardDAOImpl dao = new DashboardDAOImpl();
/*
    RepairItemDAOImpl repairItemDAO = new RepairItemDAOImpl();
*/
   DashboardBOImpl dashboardBO = new DashboardBOImpl();


    @FXML
    private TableView<RepairItemDTO> repairItemList;
    @FXML
    private TableColumn<RepairItemDTO,String> colrepairid;
    @FXML
    private TableColumn<RepairItemDTO,String> colDate;
    @FXML
    private TableColumn<RepairItemDTO,String> colcustomername;
    @FXML
    private TableColumn<RepairItemDTO,String> colcontact;
    @FXML
    private TableColumn<RepairItemDTO,String> coltyp;
    @FXML
    private TableColumn<RepairItemDTO,String> colcon;
    @FXML
    private TableColumn<RepairItemDTO,String> coldes;
    @FXML
    private TableColumn<RepairItemDTO,Double> colweight;
    @FXML
    private TableColumn<RepairItemDTO,Double> colpayment;

    @FXML
    private LineChart<String, Number> goldRateChart;
    @FXML
    private PieChart customerPieChart;
    @FXML
    private BarChart<String, Number> monthlyRevenueChart;

    @FXML
    private Label userInfoLabel;

    private ObservableList<RepairItemDTO> repairitems = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUserInfo();


        colrepairid.setCellValueFactory(new PropertyValueFactory<>("repairItemId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        colcustomername.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        coltyp.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        colcon.setCellValueFactory(new PropertyValueFactory<>("currentCondition"));
        coldes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colweight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colpayment.setCellValueFactory(new PropertyValueFactory<>("advancePayment"));

        repairItemList.setItems(repairitems);


        loadAllRepairItem();
        loadGoldRateChart();
        loadCustomerPieChart();
        loadMonthlyRevenueChart();

        System.out.println("Repairs loaded: " + repairitems.size());
    }


    private void setUserInfo() {
        if (LoginController.loggedUserName != null && LoginController.loggedUserRole != null) {
            userInfoLabel.setText(LoginController.loggedUserRole + ": " + LoginController.loggedUserName);
            System.out.println("Welcome " + LoginController.loggedUserName + " (" + LoginController.loggedUserRole + ")");
        } else {
            userInfoLabel.setText("Guest User");
        }
    }

    private void loadAllRepairItem() {
        try{
            repairitems.clear();
            repairitems.addAll(dashboardBO.getAll());
            repairItemList.refresh();
            System.out.println("Total Repair Items loaded : " + repairitems.size());

            if(!repairitems.isEmpty()){
                System.out.println ("First Repair Item :"+ repairitems.get(0).toString());
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"failed to load Repair Item"+ e.getMessage()).show();
        }
    }

    private void loadGoldRateChart() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Gold Rate");

            series.getData().add(new XYChart.Data<>("Jan", 15500));
            series.getData().add(new XYChart.Data<>("Feb", 15800));
            series.getData().add(new XYChart.Data<>("Mar", 16200));
            series.getData().add(new XYChart.Data<>("Apr", 16000));
            series.getData().add(new XYChart.Data<>("May", 16500));
            series.getData().add(new XYChart.Data<>("Jun", 17000));

            goldRateChart.getData().clear();
            goldRateChart.getData().add(series);
            goldRateChart.setLegendVisible(false);
            goldRateChart.setAnimated(true);
            goldRateChart.setCreateSymbols(true);

        } catch (Exception e) {
            System.err.println("Error loading gold rate chart: " + e.getMessage());
        }
    }

    private void loadCustomerPieChart() {
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Regular", 45),
                    new PieChart.Data("New", 25),
                    new PieChart.Data("VIP", 30)
            );

            customerPieChart.setData(pieChartData);
            customerPieChart.setLegendVisible(true);
            customerPieChart.setLabelsVisible(true);
            customerPieChart.setStartAngle(90);
            customerPieChart.setAnimated(true);


            pieChartData.forEach(data -> {
                data.nameProperty().set(data.getName() + " (" + (int)data.getPieValue() + "%)");
            });

        } catch (Exception e) {
            System.err.println("Error loading customer pie chart: " + e.getMessage());
        }
    }

    private void loadMonthlyRevenueChart() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Revenue");

            series.getData().add(new XYChart.Data<>("Jan", 8.5));
            series.getData().add(new XYChart.Data<>("Feb", 9.2));
            series.getData().add(new XYChart.Data<>("Mar", 10.5));
            series.getData().add(new XYChart.Data<>("Apr", 9.8));
            series.getData().add(new XYChart.Data<>("May", 11.2));
            series.getData().add(new XYChart.Data<>("Jun", 12.4));

            monthlyRevenueChart.getData().clear();
            monthlyRevenueChart.getData().add(series);
            monthlyRevenueChart.setLegendVisible(false);
            monthlyRevenueChart.setAnimated(true);
            monthlyRevenueChart.setBarGap(3);
            monthlyRevenueChart.setCategoryGap(10);

        } catch (Exception e) {
            System.err.println("Error loading monthly revenue chart: " + e.getMessage());
        }
    }

    @FXML
    void logoutOnAction() {
        System.out.println("Logging out...");


        LoginController.loggedUserId = null;
        LoginController.loggedUserName = null;
        LoginController.loggedUserRole = null;


        App.setRoot("login");
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