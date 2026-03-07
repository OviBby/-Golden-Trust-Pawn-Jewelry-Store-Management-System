
package lk.ijse.jewelryshop.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/JewelryShop";
    private static final String USER = "root";
    private static final String PASS = "mysql";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}