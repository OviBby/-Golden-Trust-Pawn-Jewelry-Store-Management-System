package lk.ijse.jewelryshop.dao;

import lk.ijse.jewelryshop.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    public  String generateId() throws SQLException;

    public  boolean save(T dto) throws SQLException ;

    public  boolean update(T dto) throws SQLException ;


    public ArrayList<T> getAll() throws SQLException ;

}
