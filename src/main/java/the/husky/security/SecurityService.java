package the.husky.security;

import jakarta.servlet.http.Cookie; // todo забрати звідси цю штуку
import jakarta.servlet.http.HttpServletRequest; // todo забрати звідси цю штуку
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;

import java.security.SecureRandom;
import java.util.*;

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

    public boolean authenticate(User user) {
        for (User cachedUser : usersCache) {
            if (cachedUser.getName().equals(user.getName()) && cachedUser.getPassword().equals(user.getPassword())) {
                authenticatedUser = cachedUser;
                return true;
            }
        }
        return false;
    }

    public boolean isAuthenticate(int id) {
        authenticatedUser = userService.getUserById(id).get();
        for (User userCache : usersCache) {
            return userCache.equals(authenticatedUser);
        }
        return false;
    }

    public boolean isAuthenticate(User user) {
        for (User authUser : usersCache) {
            return user.equals(authUser);
        }
        return false;
    }

    public boolean isAuthenticate(String login) {
        for (User authUser : usersCache) {
            return authUser.getName().equals(login);
        }
        return false;
    }



    public String generateToken(String password) {
        return getToken(password) + getSalt();
    }

    public String getToken(String password) {
        byte[] bytes = password.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes);
    }

    public String getSalt() {
        return UUID.randomUUID().toString();
//        SecureRandom secureRandom = new SecureRandom();
//        int saltSize = new Random().nextInt() * 10;
//        byte[] saltBytes = new byte[saltSize];
//        secureRandom.nextBytes(saltBytes);
//        return Base64.getEncoder().encodeToString(saltBytes);
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
//    public User authenticateUser(User user) {
//        for (User registeredUser : usersCache) {
//            if (registeredUser.equals(user)) {
//                authenticatedUser = registeredUser;
//                return authenticatedUser;
//            }
//        }
//        return null;
//    }
}
