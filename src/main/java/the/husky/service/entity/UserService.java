package the.husky.service.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.dao.UserDao;
import the.husky.entity.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    private UserDao userDao;

    public List<User> findAll() {
        List<User> allUsers = Collections.synchronizedList(new ArrayList<>());
        for (List<User> currentUser : userDao.findAll()) {
            synchronized (allUsers) {
                allUsers.addAll(currentUser);
            }
        }
        return allUsers;
    }

    public void add(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        userDao.save(user);
    }

    public Optional<User> getByLogin(String name) {
        return userDao.findByLogin(name);
    }

    public User getUserById(int id) {
        return userDao.findById(id).orElseThrow();
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }
}

