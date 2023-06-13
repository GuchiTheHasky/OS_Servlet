package the.husky.web.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import the.husky.entity.user.User;
import the.husky.service.UserService;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor

public class SecurityService {
    private List<User> users;
    private UserService userService;
    private User authenticatedUser;

    public SecurityService(List<User> users, UserService service) {
        this.users = users;
        this.userService = service;
    }

    public static boolean isAuthenticate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-valid") & cookie.getValue() != null) {
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
}
