package lk.ijse.jewelryshop.dao.custom;

import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.UserDTO;
import lk.ijse.jewelryshop.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {
    public static UserDTO validateLogin(String userName, String password) throws SQLException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM User WHERE user_name = ? AND password = ?",userName,password);

        if (rs.next()) {
            return new UserDTO(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }

        return null;
    }
}
