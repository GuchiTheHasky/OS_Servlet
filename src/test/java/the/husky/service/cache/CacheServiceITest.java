package the.husky.service.cache;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.entity.user.User;
import the.husky.service.entity.UserService;
import the.husky.service.entity.VehicleService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CacheServiceITest {
    CacheService cacheService;

    @BeforeEach
    public void setup() {
        cacheService = new CacheService(new UserService(new JdbcUserDao()), new VehicleService(new JdbcVehicleDao()));
    }

    @Test
    @SneakyThrows
    public void test() {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();

        User firstTestUser = new User("test-user1", "test-password1");
        User secondTestUser = new User("test-user2", "test-password2");

        cacheService.addUser(firstTestUser);
        cacheService.addUser(secondTestUser);

        final AtomicReference<List<User>> firstUsersList = new AtomicReference<>(Collections.emptyList());
        Thread firstThread = new Thread(new Runnable() {
            final CacheService cacheService = new CacheService
                    (new UserService(new JdbcUserDao()), new VehicleService(new JdbcVehicleDao()));
            @Override
            public void run() {
                firstUsersList.set(cacheService.getUsersCache());
            }
        });
        firstThread.start();

        final AtomicReference<List<User>> secondUsersList = new AtomicReference<>(Collections.emptyList());
        Thread secondThread = new Thread(new Runnable() {
            final CacheService cacheService = new CacheService
                    (new UserService(new JdbcUserDao()), new VehicleService(new JdbcVehicleDao()));
            @Override
            public void run() {
                secondUsersList.set(cacheService.getUsersCache());
            }
        });
        secondThread.start();

        firstThread.join();
        secondThread.join();

        assertNotNull(firstUsersList.get());
        assertNotNull(secondUsersList.get());

        int expectedUsersCount = 2;
        int actualFirstUsersCount = firstUsersList.get().size();
        int actualSecondUsersCount = secondUsersList.get().size();
        assertEquals(expectedUsersCount, actualFirstUsersCount);
        assertEquals(expectedUsersCount, actualSecondUsersCount);

        assertEquals(firstUsersList.get().get(0).getLogin(), secondUsersList.get().get(0).getLogin());
        assertEquals(firstUsersList.get().get(0).getPassword(), secondUsersList.get().get(0).getPassword());
        
        assertEquals(firstUsersList.get().get(1).getLogin(), secondUsersList.get().get(1).getLogin());
        assertEquals(firstUsersList.get().get(1).getPassword(), secondUsersList.get().get(1).getPassword());

        User first_Test_User = jdbcUserDao.findByLogin("test-user1").get();
        User second_Test_User = jdbcUserDao.findByLogin("test-user2").get();
        cacheService.deleteUser(first_Test_User.getUserId());
        cacheService.deleteUser(second_Test_User.getUserId());
    }
}
