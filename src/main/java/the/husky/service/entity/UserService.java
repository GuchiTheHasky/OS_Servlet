package the.husky.service.entity;

import lombok.RequiredArgsConstructor;
import the.husky.dao.UserDao;
import the.husky.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

