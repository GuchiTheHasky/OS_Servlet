package the.husky.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import the.husky.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int n) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Timestamp registrationTime = resultSet.getTimestamp("registration_time");

        return User.builder()
                .userId(id)
                .login(login)
                .password(password)
                .registrationTime(registrationTime.toLocalDateTime())
                .build();
    }
}
