package the.husky.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;

import java.util.*;

@Getter
@NoArgsConstructor

public class SecurityService {
    private List<User> users;
    private UserService userService;
    private User authenticatedUser;

    public SecurityService(UserService service) throws DataAccessException {
        this.users = service.getAll();
        this.userService = service;
    }

    public boolean isAuthenticate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")
                        && cookie.getValue() != null) {
                    return true;
                }
            }
        }
        return false;
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
    public User authenticateUser(User user) {
        for (User registeredUser : users) {
            if (registeredUser.equals(user)) {
                authenticatedUser = registeredUser;
                return authenticatedUser;
            }
        }
        return null;
    }
}
