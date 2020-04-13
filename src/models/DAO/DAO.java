package models.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO<T> {
    protected Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/AsteriskLocation","root","");
    //Constructor
    public DAO(Connection conn) throws SQLException {
        this.connect = conn;
    }

    public abstract boolean create(T obj);
    public abstract boolean delete(T obj);
    public abstract boolean update(T obj,int id);
    public abstract T find(int id);
}