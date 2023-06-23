package the.husky.dao.jdbc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import the.husky.entity.user.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {
    private final JdbcUserDao DAO = new JdbcUserDao();

    @Test
    @SneakyThrows
    @DisplayName("Test, find all users.")
    public void testFindAllUsers() {
        List<User> users = DAO.findAll();
        assertFalse(users.isEmpty());
        for (User user : users) {
            assertNotEquals(0, user.getUserId());
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, find user by id.")
    public void testFindById() {
        int id = 1;
        Optional<User> user = DAO.findById(id);
        assertNotNull(user);
        assertFalse(user.get().getName().isEmpty());
        assertFalse(user.get().getPassword().isEmpty());
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, find User by name.")
    public void testFindUserByName() {
        String name = "user";
        Optional<User> currentUser = DAO.findByLogin(name);
        assertNotNull(currentUser);

        String expectedPassword = "user";
        String actualPassword = currentUser.get().getPassword();
        assertEquals(expectedPassword, actualPassword);
    }
}
