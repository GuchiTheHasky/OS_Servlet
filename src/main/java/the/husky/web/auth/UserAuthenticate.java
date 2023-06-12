package the.husky.web.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import the.husky.entity.user.User;
import the.husky.service.UserService;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

public class UserAuthenticate {
    private List<User> users;
    private UserService userService;
    private User authenticatedUser;

    public UserAuthenticate(List<User> users, UserService service) {
        this.users = users;
        this.userService = service;
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                return user;
            }
        }
        return null;
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            if (authenticatedUser != null && authenticatedUser.equals(user)) {
                authenticatedUser = null;
            }
        }
    }

    public int getUserId(User user) {
        return user.getUserId();
    }

    public void addNewUser(User user) {
        users.add(user);
    }

    public void deleteExistingUser(User user) {
        users.remove(user);
        if (authenticatedUser != null && authenticatedUser.equals(user)) {
            authenticatedUser = null;
        }
    }

    public boolean isAuthenticated(User user) {
        return authenticatedUser != null && authenticatedUser.equals(user);
    }

}
