package the.husky.service;

import the.husky.dao.UserDao;
import the.husky.entity.user.User;
import the.husky.web.auth.UserAuthenticate;

import java.time.LocalDateTime;
import java.util.List;


public class UserService {

    private final UserDao DAO;

    public UserService(UserDao userDao) {
        this.DAO = userDao;
    }

    public List<User> getAll() {
        return DAO.getUsers();
    }

    public void add(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setRegistrationTime(time);
        DAO.add(user);
    }

    public User getByName(String name) {
        return DAO.getUserByName(name);
    }

    public User getUserById(int id) {
        return DAO.getById(id);
    }

    public void update(User user) {
        DAO.update(user);
    }

    public void delete(int id) {
        DAO.delete(id);
    }
}

