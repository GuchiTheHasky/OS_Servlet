package the.husky.service;

import the.husky.dao.UserDao;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;


public class UserService {

    private final UserDao DAO;

    public UserService(UserDao userDao) {
        this.DAO = userDao;
    }

    public List<User> getAll() throws DataAccessException {
        return DAO.findAll();
    }

    public void add(User user) throws DataAccessException {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        DAO.add(user);
    }

    public User getByName(String name) throws DataAccessException {
        return DAO.findUserByName(name);
    }

    public User getUserById(int id) throws DataAccessException {
        return DAO.findById(id);
    }

    public void update(User user) throws DataAccessException {
        DAO.update(user);
    }

    public void delete(int id) throws DataAccessException {
        DAO.delete(id);
    }
}

