package the.husky.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import the.husky.dao.UserDao;
import the.husky.dao.jdbc.mapper.UserRowMapper;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_ALL = "SELECT id, login, password, registration_time FROM users";
    private static final String INSERT = "INSERT INTO users (login, password, registration_time) VALUES(?, ?, ?)";
    private static final String GET_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String GET_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE = "UPDATE users SET login = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    @Override
    public List<User> findAll() {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findAll;", e);
            throw new DataAccessException("Error retrieving users. Please try again later.", e);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(user.getRegistrationTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: save;", e);
            throw new DataAccessException("Error saving user. Please try again later.", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(USER_ROW_MAPPER.mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findByLogin;", e);
            throw new DataAccessException("Error finding user by \"Login\". Please try again later.", e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(USER_ROW_MAPPER.mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findById;", e);
            throw new DataAccessException(
                    String.format("Error finding user by \"Id\": %d. Please try again later.", id), e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: update;", e);
            throw new DataAccessException("Error updating user. Please try again later.", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: delete;", e);
            throw new DataAccessException(
                    String.format("Error deleting user by \"Id\": %d. Please try again later.", id), e);
        }
    }
}
