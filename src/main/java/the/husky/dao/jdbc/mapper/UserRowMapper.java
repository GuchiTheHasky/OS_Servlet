package the.husky.dao.jdbc.mapper;

import the.husky.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
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
