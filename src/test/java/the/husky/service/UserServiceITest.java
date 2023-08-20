package the.husky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import the.husky.entity.User;
import the.husky.repository.UserDao;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@DisplayName("Integration test for UserService")
class UserServiceITest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    private User darthVader;
    private User lukeSkywalker;
    private User masterYoda;

    @BeforeEach
    void setUp() {
        userDao.deleteAll();

        darthVader = setVader();
        lukeSkywalker = setLuke();
        masterYoda = setYoda();

        userService.add(darthVader);
        userService.add(lukeSkywalker);
        userService.add(masterYoda);
    }

    @DisplayName("Find all users, test.")
    @Test
    void testShouldRetrieveAllUsersFromDatabase() {
        List<User> users = userService.findAll();

        int expectedSize = 3;
        int actualSize = users.size();
        User expecteFirstdUser = darthVader;
        User expectedSecondUser = lukeSkywalker;
        User expectedThirdUser = masterYoda;

        assertFalse(users.isEmpty());
        assertEquals(expectedSize, actualSize);

        assertEquals(expecteFirstdUser.getLogin(), users.get(0).getLogin());
        assertEquals(expectedSecondUser.getLogin(), users.get(1).getLogin());
        assertEquals(expectedThirdUser.getLogin(), users.get(2).getLogin());

        assertEquals(expecteFirstdUser.getPassword(), users.get(0).getPassword());
        assertEquals(expectedSecondUser.getPassword(), users.get(1).getPassword());
        assertEquals(expectedThirdUser.getPassword(), users.get(2).getPassword());
    }

    @DisplayName("Add new user, test.")
    @Test
    void testAddingANewUserShouldUpdateTheListOfVehiclesWithTheNewEntity() {
        User khanSolo = setKhanSolo();
        String expectedLogin = khanSolo.getLogin();
        String expectedPassword = khanSolo.getPassword();
        int expectedSize = 4;

        userService.add(khanSolo);
        List<User> users = userService.findAll();

        String actualLogin = users.get(3).getLogin();
        String actualPassword = users.get(3).getPassword();
        int actualSize = users.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedLogin, actualLogin);
        assertEquals(expectedPassword, actualPassword);
    }

    @DisplayName("Find user by ID, test.")
    @Test
    void testFindingAUserByIDShouldReturnTheCorrespondingUserFromTheList() {
        List<User> users = userService.findAll();

        User expectedFirstUser = users.get(0);
        User expectedSecondUser = users.get(1);
        User expectedThirdUser = users.get(2);

        int firstUserId = expectedFirstUser.getUserId();
        int secondUserId = expectedSecondUser.getUserId();
        int thirdUserId = expectedThirdUser.getUserId();

        User actualFirstUser = userService.findUserById(firstUserId);
        User actualSecondUser = userService.findUserById(secondUserId);
        User actualThirdUser = userService.findUserById(thirdUserId);

        assertEquals(expectedFirstUser.getLogin(), actualFirstUser.getLogin());
        assertEquals(expectedSecondUser.getLogin(), actualSecondUser.getLogin());
        assertEquals(expectedThirdUser.getLogin(), actualThirdUser.getLogin());

        assertEquals(expectedFirstUser.getPassword(), actualFirstUser.getPassword());
        assertEquals(expectedSecondUser.getPassword(), actualSecondUser.getPassword());
        assertEquals(expectedThirdUser.getPassword(), actualThirdUser.getPassword());
    }

    @DisplayName("Delete user by ID, test.")
    @Test
    void testDeletingAUserShouldRemoveTheCorrespondingEntityFromTheListOfUsers() {
        List<User> users = userService.findAll();
        int userIdToDelete = users.get(2).getUserId();

        int expectedSize = 2;
        User expectedFirstUser = darthVader;
        User expectedSecondUser = lukeSkywalker;

        userService.delete(userIdToDelete);
        users = userService.findAll();

        int actualSize = users.size();
        User actualFirstUser = users.get(0);
        User actualSecondUser = users.get(1);

        assertEquals(expectedSize, actualSize);

        assertEquals(expectedFirstUser.getLogin(), actualFirstUser.getLogin());
        assertEquals(expectedSecondUser.getLogin(), actualSecondUser.getLogin());

        assertEquals(expectedFirstUser.getPassword(), actualFirstUser.getPassword());
        assertEquals(expectedSecondUser.getPassword(), actualSecondUser.getPassword());
    }

    private User setKhanSolo() {
        return User.builder()
                .userId(4)
                .login("KhanSolo")
                .password("captain")
                .registrationTime(LocalDateTime.now())
                .build();
    }

    private User setVader() {
        return User.builder()
                .userId(1)
                .login("DarthVader")
                .password("12345678")
                .registrationTime(LocalDateTime.now())
                .build();
    }

    private User setLuke() {
        return User.builder()
                .userId(2)
                .login("LukeSkywalker")
                .password("jedi")
                .registrationTime(LocalDateTime.now())
                .build();
    }

    private User setYoda() {
        return User.builder()
                .userId(3)
                .login("MasterYoda")
                .password("green")
                .registrationTime(LocalDateTime.now())
                .build();
    }
}