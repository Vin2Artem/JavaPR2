package DAO;

import models.User;

import java.sql.*;
import java.util.*;

public class SQLiteUserDAO implements UserDAO {
    public SQLiteUserDAO() {
        // инициализация
    }

    public int insertUser(User user) {
        try {
            Connection connection = SQLiteDAOFactory.getConnection();

            PreparedStatement pStatement = connection.prepareStatement(
                    "INSERT INTO users(`surname`, `name`, `patronymic`, `sex`, `birth`, `email`," +
                            "`password`, `phone`, `city`, `street`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pStatement.setObject(1, user.getSurname());
            pStatement.setObject(2, user.getName());
            pStatement.setObject(3, user.getPatronymic());
            pStatement.setObject(4, user.isSex());
            pStatement.setObject(5, user.getBirth());
            pStatement.setObject(6, user.getEmail());
            pStatement.setObject(7, user.getPassword());
            pStatement.setObject(8, user.getPhone());
            pStatement.setObject(9, user.getCity());
            pStatement.setObject(10, user.getStreet());
            pStatement.execute();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) AS mx FROM users");
            if (resultSet.next()) {
                return resultSet.getInt("mx");
            }
            return INSERT_ERROR;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg.contains("UNIQUE") && msg.contains("email")) {
                return UNIQUE_EMAIL;
            }
            e.printStackTrace();
            return INSERT_ERROR;
        }
    }

    public boolean deleteUser(int id) {
        try {
            Connection connection = SQLiteDAOFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setObject(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        // Реализовать здесь операцию обновления записи,
        // используя данные из UserData Transfer Object
        // Возвратить true при успешном выполнении, false при ошибке
        return false;
    }

    public ArrayList<User> getAllUsers() {
        try {
            ArrayList<User> lst = new ArrayList<>();
            Connection connection = SQLiteDAOFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getBoolean("sex"),
                        resultSet.getString("birth"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("city"),
                        resultSet.getString("street")
                );
                lst.add(user);
            }
            return lst;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}