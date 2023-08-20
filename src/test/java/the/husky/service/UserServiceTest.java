package the.husky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import the.husky.entity.User;
import the.husky.repository.UserDao;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Unit Tests for UserService")
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserDao userDao;

    private User darthVader;
    private User lukeSkywalker;
    private User masterYoda;

    @BeforeEach
    void setUp() {
        darthVader = setVader();
        lukeSkywalker = setLuke();
        masterYoda = setYoda();

        List<User> users = new java.util.ArrayList<>(List.of(darthVader, lukeSkywalker, masterYoda));
        when(userDao.findAll()).thenReturn(users);

        findByIdMock();

        doAnswer(invocation -> {
            int deletedId = invocation.getArgument(0);
            users.removeIf(user -> user.getUserId() == deletedId);
            return null;
        }).when(userDao).delete(anyInt());
    }

    @DisplayName("Find all users")
    @Test
    void testShouldRetrieveAllUsersFromDatabase() {
        List<User> users = userService.findAll();

        int expectedSize = 3;
        User expecteFirstdUser = darthVader;
        User expectedSecondUser = lukeSkywalker;
        User expectedThirdUser = masterYoda;

        assertFalse(users.isEmpty());
        assertEquals(expectedSize, users.size());

        assertEquals(expecteFirstdUser.getLogin(), users.get(0).getLogin());
        assertEquals(expectedSecondUser.getLogin(), users.get(1).getLogin());
        assertEquals(expectedThirdUser.getLogin(), users.get(2).getLogin());

        assertEquals(expecteFirstdUser.getPassword(), users.get(0).getPassword());
        assertEquals(expectedSecondUser.getPassword(), users.get(1).getPassword());
        assertEquals(expectedThirdUser.getPassword(), users.get(2).getPassword());
    }

    @DisplayName("Find user by ID, test.")
    @Test
    void findUserById() {
        User expectedFirstUser = darthVader;
        User expectedSecondUser = lukeSkywalker;
        User expectedThirdUser = masterYoda;

        User actualFirstUser = userService.findUserById(1);
        User actualSecondUser = userService.findUserById(2);
        User actualThirdUser = userService.findUserById(3);

        assertEquals(expectedFirstUser, actualFirstUser);
        assertEquals(expectedSecondUser, actualSecondUser);
        assertEquals(expectedThirdUser, actualThirdUser);
    }

    @DisplayName("Delete user by ID, test.")
    @Test
    void testDeletingAUserShouldRemoveTheCorrespondingEntityFromTheListOfUsers() {
        int userIdToDelete = 2;

        userService.delete(userIdToDelete);
        List<User> users = userService.findAll();

        int expectedSize = 2;
        User expectedFirstUser = darthVader;
        User expectedSecondUser = masterYoda;

        assertEquals(expectedSize, users.size());
        assertEquals(expectedFirstUser, users.get(0));
        assertEquals(expectedSecondUser, users.get(1));
    }

    private void findByIdMock() {
        when(userDao.findById(1)).thenReturn(java.util.Optional.ofNullable(darthVader));
        when(userDao.findById(2)).thenReturn(java.util.Optional.ofNullable(lukeSkywalker));
        when(userDao.findById(3)).thenReturn(java.util.Optional.ofNullable(masterYoda));
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