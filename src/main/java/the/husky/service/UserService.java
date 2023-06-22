package the.husky.service;

import lombok.RequiredArgsConstructor;
import the.husky.dao.UserDao;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.security.SecurityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private SecurityService securityService;

    public List<User> getAll() throws DataAccessException {
        return userDao.findAll();
    }

    public void add(User user) throws DataAccessException {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        userDao.save(user);
    }

    public Optional<User> getByName(String name) throws DataAccessException {
        return userDao.findByLogin(name);
    }

    public Optional<User> getUserById(int id) throws DataAccessException {
        return userDao.findById(id);
    }

    public void update(User user) throws DataAccessException {
        userDao.update(user);
    }

    public void delete(int id) throws DataAccessException {

        userDao.delete(id);
    }
}

