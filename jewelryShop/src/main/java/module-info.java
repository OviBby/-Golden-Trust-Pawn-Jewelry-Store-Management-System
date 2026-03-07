module lk.ijse.jewelryshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires net.sf.jasperreports.core;


    opens lk.ijse.jewelryshop.dto to javafx.base;
    opens lk.ijse.jewelryshop to javafx.fxml;
    opens lk.ijse.jewelryshop.controller to javafx.fxml;

    exports lk.ijse.jewelryshop;
    exports lk.ijse.jewelryshop.controller;
}
