package the.husky.dao.jdbc;

import the.husky.dao.UserDao;
import the.husky.dao.jdbc.mapper.UserRowMapper;
import the.husky.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_ALL = "SELECT id, user_name, password, registration_time FROM \"user\"";
    private static final String INSERT = "INSERT INTO \"user\" (user_name, password, registration_time) VALUES(?, ?, ?)";
    private static final String GET_BY_NAME = "SELECT * FROM \"user\" WHERE user_name = ?";
    private static final String GET_BY_ID = "SELECT * FROM \"user\" WHERE id = ?";
    private static final String UPDATE = "UPDATE \"user\" SET user_name = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM \"user\" WHERE id = ?";


    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void add(User user) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(user.getRegistrationTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByName(String name) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name); // Додано передачу значення name
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return USER_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getById(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id); // Додано передачу значення id
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return USER_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OS", "postgres", "root");
    }
}
