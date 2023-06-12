package the.husky.dao;

import the.husky.entity.user.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    void add(User user);

    User getUserByName(String name);

    User getById(int id);

    void update(User user);

    void delete(int id);
}
