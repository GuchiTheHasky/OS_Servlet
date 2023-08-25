package the.husky.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import the.husky.entity.User;
import the.husky.repository.UserDao;
import the.husky.repository.jdbc.mapper.UserRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_ALL = "SELECT id, login, password, registration_time FROM users";
    private static final String INSERT = "INSERT INTO users (login, password, registration_time) VALUES(?, ?, ?)";
    private static final String GET_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String GET_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE = "UPDATE users SET login = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM users";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL, USER_ROW_MAPPER);
    }

    @Override
    public synchronized int save(User user) {
        return jdbcTemplate.update(INSERT, user.getLogin(), user.getPassword(), user.getRegistrationTime());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_BY_ID, USER_ROW_MAPPER, id));
    }

    @Override
    public synchronized void update(User user) {
        jdbcTemplate.update(UPDATE, user.getLogin(), user.getPassword(), user.getUserId());
    }

    @Override
    public synchronized void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public synchronized void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
