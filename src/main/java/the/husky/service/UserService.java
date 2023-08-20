package the.husky.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.husky.entity.User;
import the.husky.exception.RepositoryException;
import the.husky.repository.UserDao;

import java.time.LocalDateTime;
import java.util.List;

@Service
@NoArgsConstructor
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void add(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        userDao.save(user);
    }

    public User findUserById(int id) {
        return userDao.findById(id).orElseThrow(() ->
                new RepositoryException(String.format("User with id: %d not found.", id)));
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }
}

