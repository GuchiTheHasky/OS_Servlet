package the.husky.dao.jdbc;

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import the.husky.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {
    private static final JdbcUserDao USER_DAO = new JdbcUserDao();

    @BeforeAll
    static void init() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/OS");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        USER_DAO.setDataSource(dataSource);
    }

    @Test
    @DisplayName("Test, find all users.")
    public void testFindAllUsers() {
        Iterable<List<User>> users = USER_DAO.findAll();

        assertNotNull(users);

        boolean isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
        assertTrue(isEmptyUsersList);
    }

    @Test
    @DisplayName("Test, save new user & delete.")
    public void testSaveNewUserAndDelete() {
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
        String login = "user";
        String password = "user";
        User user = new User(login, password, LocalDateTime.now());
        USER_DAO.save(user);

        Optional<User> currentUser = USER_DAO.findByLogin(login);
        assertNotNull(currentUser);

        String actualLogin = currentUser.get().getLogin();
        String actualPassword = currentUser.get().getPassword();
        assertEquals(login, actualLogin);
        assertEquals(password, actualPassword);

        USER_DAO.delete(currentUser.get().getUserId());
    }
}
