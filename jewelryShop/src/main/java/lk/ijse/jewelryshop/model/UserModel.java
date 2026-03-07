/*
package lk.ijse.jewelryshop.model;

import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.UserDTO;
import lk.ijse.jewelryshop.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

*
     * Validate user login credentials


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

*
     * Get user by username only


    public static UserDTO getUserByUsername(String userName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM User WHERE user_name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userName);

        ResultSet rs = pstm.executeQuery();

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

*
     * Add new user


    public static boolean saveUser(UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO User (user_id, user_name, password, role) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userDTO.getUserId());
        pstm.setString(2, userDTO.getUserName());
        pstm.setString(3, userDTO.getPassword());
        pstm.setString(4, userDTO.getRole());

        return pstm.executeUpdate() > 0;
    }

*
     * Update user


    public static boolean updateUser(UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE User SET user_name = ?, password = ?, role = ? WHERE user_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userDTO.getUserName());
        pstm.setString(2, userDTO.getPassword());
        pstm.setString(3, userDTO.getRole());
        pstm.setString(4, userDTO.getUserId());

        return pstm.executeUpdate() > 0;
    }

*
     * Delete user


    public static boolean deleteUser(String userId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM User WHERE user_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userId);

        return pstm.executeUpdate() > 0;
    }
}
*/
