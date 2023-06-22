package the.husky.security;

import jakarta.servlet.http.Cookie; // todo забрати звідси цю штуку
import jakarta.servlet.http.HttpServletRequest; // todo забрати звідси цю штуку
import lombok.Getter;
import lombok.NoArgsConstructor;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;

import java.util.List;

@Getter
@NoArgsConstructor
public class SecurityService {
    private List<User> usersCache;
    private UserService userService;
    private User authenticatedUser;

    public SecurityService(UserService service) throws DataAccessException {
        this.usersCache = service.getAll();
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
        userService.add(user);
        usersCache.add(user);
    }


    public void deleteExistingUser(User user) {
        usersCache.remove(user);
        if (authenticatedUser != null && authenticatedUser.equals(user)) {
            authenticatedUser = null;
        }
    }
    public User authenticateUser(User user) {
        for (User registeredUser : usersCache) {
            if (registeredUser.equals(user)) {
                authenticatedUser = registeredUser;
                return authenticatedUser;
            }
        }
        return null;
    }
}
