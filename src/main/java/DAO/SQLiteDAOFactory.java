package DAO;

import org.sqlite.JDBC;

import java.sql.*;

public class SQLiteDAOFactory extends DAOFactory {
    private static final String DBURL= "jdbc:sqlite:C:/databases/myfin.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(DBURL);
        }
        return connection;
    }

    public UserDAO getUserDAO() {
        return new SQLiteUserDAO();
    }
}