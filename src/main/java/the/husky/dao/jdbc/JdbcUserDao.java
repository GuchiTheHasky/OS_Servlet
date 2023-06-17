package the.husky.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import the.husky.dao.UserDao;
import the.husky.dao.jdbc.mapper.UserRowMapper;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_ALL = "SELECT id, user_name, password, registration_time FROM \"user\"";
    private static final String INSERT = "INSERT INTO \"user\" (user_name, password, registration_time) VALUES(?, ?, ?)";
    private static final String GET_BY_NAME = "SELECT * FROM \"user\" WHERE user_name = ?";
    private static final String GET_BY_ID = "SELECT * FROM \"user\" WHERE id = ?";
    private static final String UPDATE = "UPDATE \"user\" SET user_name = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM \"user\" WHERE id = ?";


    @Override
    public List<User> findAll() throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't find users.", e);
        }
    }

    @Override
    public void add(User user) throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(user.getRegistrationTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't add user: " + user, e);
        }
    }

    @Override
    public User findUserByName(String name) throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return USER_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't find a user with this name: " + name, e);
        }
        return null;
    }

    @Override
    public User findById(int id) throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return USER_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't find a user with this Id: " + id, e);
        }
        return null;
    }

    @Override
    public void update(User user) throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't update user: " + user, e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Data access exception.");
            throw new DataAccessException("Couldn't delete user with thouse id: " + id, e);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OS", "postgres", "root");
    }
}
