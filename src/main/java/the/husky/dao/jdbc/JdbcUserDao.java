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
    private static final String SELECT_ALL = "SELECT id, user_name, password, registration_time FROM \"user\"";
    // TODO: 21.06.2023 замінити user to users, щоб забрати лапки
    private static final String INSERT = "INSERT INTO \"user\" (user_name, password, registration_time) VALUES(?, ?, ?)";
    private static final String GET_BY_NAME = "SELECT * FROM \"user\" WHERE user_name = ?";
    private static final String GET_BY_ID = "SELECT * FROM \"user\" WHERE id = ?";
    private static final String UPDATE = "UPDATE \"user\" SET user_name = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM \"user\" WHERE id = ?";


    @Override
    public List<User> findAll() {
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
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findAll;", e);
            throw new DataAccessException("Error retrieving users. Please try again later.", e);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(user.getRegistrationTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: save;", e);
            throw new DataAccessException("Error saving user. Please try again later.", e);
        }
    }
    // todo: замінити User на Optional
    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, login);
            // todo: ??
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(USER_ROW_MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findByLogin;", e);
            throw new DataAccessException("Error finding user by \"Login\". Please try again later.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(USER_ROW_MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: findById;", e);
            throw new DataAccessException(
                    String.format("Error finding user by \"Id\": %d. Please try again later.", id), e);
        }
        return Optional.empty();
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
            log.error("SQL or data connection refused; JdbcUserDao.class, method: update;", e);
            throw new DataAccessException("Error updating user. Please try again later.", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcUserDao.class, method: delete;", e);
            throw new DataAccessException(
                    String.format("Error deleting user by \"Id\": %d. Please try again later.", id), e);
        }
    }

    // TODO: має бути якийсь дата соурс щоб це забрати
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OS", "postgres", "root");
    }
}
