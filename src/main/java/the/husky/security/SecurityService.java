package the.husky.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import the.husky.entity.user.User;
import the.husky.security.entity.Role;
import the.husky.security.session.Session;
import the.husky.service.UserService;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class SecurityService {
    private List<User> usersCache;
    private UserService userService;

    public SecurityService(UserService service) {
        this.usersCache = service.getAll();
        this.userService = service;
    }

    public Session createSession(Credentials credentials) {
        User user = getUserFromCredentials(credentials);
        if (user != null) {
            LocalDateTime expireDate = getExpirationDate();
            if (user.getLogin().equals("admin")) {
                return Session.builder()
                        .role(Role.ADMIN)
                        .user(user)
                        .expireDate(expireDate)
                        .build();
            } else {
                return Session.builder()
                        .role(Role.USER)
                        .user(user)
                        .expireDate(expireDate)
                        .build();
            }
        }
        return null;
    }

    private User getUserFromCredentials(Credentials credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        for (User user : usersCache) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private LocalDateTime getExpirationDate() {
        LocalDateTime date = LocalDateTime.now();
        long expirationTime = 1;
        return date.plusHours(expirationTime);
    }

    public void addUser(User user) {
        userService.add(user);
        usersCache.add(user);
    }

        public void deleteUser(int id) {
        User user = userService.getUserById(id);
        userService.delete(id);
        usersCache.remove(user);
    }







///////////////////////////////////////////////////////////////
//    public boolean authenticate(User user) {
//        for (User cachedUser : usersCache) {
//            if (cachedUser.getLogin().equals(user.getLogin()) && cachedUser.getPassword().equals(user.getPassword())) {
//                authenticatedUser = cachedUser;
//                return true;
//            }
//        }
//        return false;
//    }

    public String getToken(String password) {
        byte[] bytes = password.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes);
    }



//    public void deleteExistingUser(User user) {
//        int id = user.getUserId();
//        userService.delete(id);
//
//        usersCache.remove(user);
//        if (authenticatedUser != null && authenticatedUser.equals(user)) {
//            authenticatedUser = null;
//        }
//    }

    public User getById(int id) {
        return userService.getUserById(id);
    }
}
