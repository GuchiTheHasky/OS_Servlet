package the.husky.service;

import lombok.RequiredArgsConstructor;
import the.husky.dao.UserDao;
import the.husky.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void add(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        userDao.save(user);
    }

    public User getByName(String name) {
        return userDao.findByLogin(name).orElseThrow();
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

