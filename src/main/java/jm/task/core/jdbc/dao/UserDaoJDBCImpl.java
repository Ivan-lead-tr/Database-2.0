package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(40),
                lastname VARCHAR(40), age TINYINT)""";

        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement pr = connection.prepareStatement(sql)) {
                    pr.execute();
                    connection.commit();
                    System.out.println("Таблица создана");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS users
                """;

        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement pr = connection.prepareStatement(sql)) {
                    pr.execute();
                    connection.commit();
                    System.out.println("База удалена");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = """
                INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)
                """;

        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement pr = connection.prepareStatement(sql)) {

                    pr.setString(1, name);
                    pr.setString(2, lastName);
                    pr.setByte(3, age);
                    pr.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        String sql = """
                DELETE FROM users WHERE id = ?
                """;

        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement pr = connection.prepareStatement(sql)) {
                    pr.setLong(1, id);
                    pr.executeUpdate();
                    connection.commit();
                    System.out.println("Пользователь по ID успешно удален");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sql = """
                SELECT id, name, lastname, age FROM users
                """;

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User users = new User();
                users.setId(resultSet.getLong("id"));
                users.setName(resultSet.getString("name"));
                users.setLastName(resultSet.getString("lastname"));
                users.setAge(resultSet.getByte("age"));
                usersList.add(users);
            }
            System.out.println("Пользователи успешно получены");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {

        String sql = """
                TRUNCATE TABLE  users
                """;

        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement pr = connection.prepareStatement(sql)) {
                    pr.execute();
                    connection.commit();
                    System.out.println("БД успешно очищена");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
