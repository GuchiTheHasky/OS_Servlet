package the.husky.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import the.husky.entity.user.User;
import the.husky.service.UserService;

import java.util.Base64;
import java.util.List;

@Getter
@NoArgsConstructor
public class SecurityService {
    private List<User> usersCache;
    private UserService userService;
    private User authenticatedUser;

    public SecurityService(UserService service) {
        this.usersCache = service.getAll();
        this.userService = service;
    }

    public boolean authenticate(User user) {
        for (User cachedUser : usersCache) {
            if (cachedUser.getLogin().equals(user.getLogin()) && cachedUser.getPassword().equals(user.getPassword())) {
                authenticatedUser = cachedUser;
                return true;
            }
        }
        return false;
    }

    public String getToken(String password) {
        byte[] bytes = password.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes);
    }

    public void addNewUser(User user) {
        userService.add(user);
        usersCache.add(user);
    }

    public void deleteExistingUser(User user) {
        int id = user.getUserId().get();
        userService.delete(id);

        usersCache.remove(user);
        if (authenticatedUser != null && authenticatedUser.equals(user)) {
            authenticatedUser = null;
        }
    }

    public User getById(int id) {
        return userService.getUserById(id).get();
    }
}
