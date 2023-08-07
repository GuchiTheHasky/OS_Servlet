package the.husky.dao.jdbc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import the.husky.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {
    private final JdbcUserDao USER_DAO = new JdbcUserDao();

    @Test
    @DisplayName("Test, find all users.")
    public void testFindAllUsers() {
        Iterable<List<User>> users = USER_DAO.findAll();

        boolean isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
        assertTrue(isEmptyUsersList);

        assertNotNull(users);
    }

    @Test
    @DisplayName("Test, save new user & delete.")
    public void testSaveNewUser() {
        String testLogin = "new-test-user";
        String testPassword = "new-test-user";
        User user = new User(testLogin, testPassword, LocalDateTime.now());

        boolean isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
        assertTrue(isEmptyUsersList);

        USER_DAO.save(user);

        isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
        assertFalse(isEmptyUsersList);

        int expectedUsersCount = 1;
        int actualUsersCount = USER_DAO.findAll().iterator().next().size();
        assertEquals(expectedUsersCount, actualUsersCount);

        User actualUser = USER_DAO.findByLogin(user.getLogin()).get();
        USER_DAO.delete(actualUser.getUserId());
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, find User by name.")
    public void testFindUserByName() {
        String name = "user";
        Optional<User> currentUser = USER_DAO.findByLogin(name);
        assertNotNull(currentUser);

        String expectedPassword = "user";
        String actualPassword = currentUser.get().getPassword();
        assertEquals(expectedPassword, actualPassword);
    }
}
