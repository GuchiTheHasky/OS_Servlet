package the.husky.web.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import the.husky.entity.user.User;
import the.husky.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public static boolean isAuthenticate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return true;
                }
            }
        }
        return false;
    }

    public User authenticate(String username, String password) {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getName().equals(username) && user.getPassword().equals(password))
                .findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            authenticatedUser = user;
            return user;
        }
        return null;
    }


//    public User authenticate(String username, String password) {
//        for (User user : users) {
//            if (user.getName().equals(username) && user.getPassword().equals(password)) {
//                return user;
//            }
//        }
//        return null;
//    }

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
