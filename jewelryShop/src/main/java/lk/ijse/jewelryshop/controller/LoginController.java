package lk.ijse.jewelryshop.controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lk.ijse.jewelryshop.App;
import lk.ijse.jewelryshop.dao.custom.UserDAOImpl;
import lk.ijse.jewelryshop.dto.UserDTO;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public static String loggedUserId;
    public static String loggedUserName;
    public static String loggedUserRole;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showWarningAlert("Empty Fields", "Please enter both username and password!");
            return;
        }

        try {

            UserDTO user = UserDAOImpl.validateLogin(username, password);

            if (user != null) {

                loggedUserId = user.getUserId();
                loggedUserName = user.getUserName();
                loggedUserRole = user.getRole();


                navigateBasedOnRole(user.getRole());

            } else {

                showErrorAlert();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "Failed to connect to database!\n" + e.getMessage());
        }
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        new Timeline(new KeyFrame(Duration.seconds(2), e -> alert.close())).play();
    }

    private void showWarningAlert(String title, String message) {
        usernameField.setStyle("-fx-border-color: #f39c12; -fx-border-width: 2; -fx-border-radius: 8;");
        passwordField.setStyle("-fx-border-color: #f39c12; -fx-border-width: 2; -fx-border-radius: 8;");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();


        new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            usernameField.setStyle("");
            passwordField.setStyle("");
        })).play();
    }

    private void showErrorAlert() {
        usernameField.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 8;");
        passwordField.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 8;");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Access Denied");
        alert.setContentText("Incorrect Username or Password!\nPlease try again.");
        alert.show();

        passwordField.clear();


        new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            usernameField.setStyle("");
            passwordField.setStyle("");
        })).play();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleKeyPress(javafx.scene.input.KeyEvent event) throws IOException {
        if (event.getCode().toString().equals("ENTER")) {
            login();
        }
    }


    private void navigateBasedOnRole(String role) throws IOException {
        switch (role.toLowerCase()) {
            case "admin":

                App.setRoot("dashboard");
                break;

            case "cashier":

                App.setRoot("cashierDashboard");
                break;

            default:

                App.setRoot("order");
                System.out.println("Unknown role: " + role + ", redirecting to order page");
                break;
        }
    }
}