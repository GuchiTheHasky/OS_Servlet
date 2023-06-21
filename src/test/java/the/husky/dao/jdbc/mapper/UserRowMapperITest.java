package the.husky.dao.jdbc.mapper;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import the.husky.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRowMapperITest {
    private final UserRowMapper MAPPER = new UserRowMapper();
    // todo моки це не інтеграційний тест, а звичайний
    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getString("user_name")).thenReturn("Obi-Wan Kenobi");
        Mockito.when(resultSet.getString("password")).thenReturn("master");
        Mockito.when(resultSet.getTimestamp("registration_time")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        User user = MAPPER.mapRow(resultSet);

        assertEquals(1, user.getUserId());
        assertEquals("Obi-Wan Kenobi", user.getName());
        assertEquals("master", user.getPassword());
        assertNotNull(user.getRegistrationTime());
    }
}
