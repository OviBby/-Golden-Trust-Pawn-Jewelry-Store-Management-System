package lk.ijse.jewelryshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    public static Stage primaryStage;
    private static boolean isFullScreen = false;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        primaryStage.setTitle("Jewelary Shop");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(true);

        // Load the initial FXML
        setRoot("login");

        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);
        primaryStage.centerOnScreen();

        primaryStage.getScene().getRoot().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                toggleFullScreen();
            }
        });

        primaryStage.show();
    }

    public static void setRoot(String fxml) {
        try {
            // Correct way to load FXML from resources
            URL fxmlLocation = App.class.getResource("/lk/ijse/jewelryshop/" + fxml + ".fxml");

            if (fxmlLocation == null) {
                System.err.println("Cannot find FXML file: /lk/ijse/jewelryshop/" + fxml + ".fxml");
                System.err.println("Check your resources folder structure!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            primaryStage.setScene(scene);

            if (isFullScreen) {
                primaryStage.setFullScreen(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleFullScreen() {
        isFullScreen = !isFullScreen;
        primaryStage.setFullScreen(isFullScreen);
    }

    public static void main(String[] args) {
        launch();
    }
}