package lk.ijse.jewelryshop.util;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.jewelryshop.dbconnection.DBConnection;


public class CrudUtil {
    public static <T> T execute(String sql ,Object... obj) throws SQLException{
        Connection conn = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = conn.prepareStatement(sql);

        for(int i = 0;i<obj.length;i++){
            pstm.setObject(i+1,obj[i]);
        }
        if(sql.startsWith("select")||sql.startsWith("SELECT")){
            ResultSet  rs = pstm.executeQuery();
            return(T)rs;
        }else{
            int result = pstm.executeUpdate();
            boolean rs= result>0;
            return(T)(Boolean)rs;
        }


    }

}

