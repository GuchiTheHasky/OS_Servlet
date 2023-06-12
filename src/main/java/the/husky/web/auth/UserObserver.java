package the.husky.web.auth;

import the.husky.entity.user.User;

public interface UserObserver {
    User authenticate(String username, String password);
}
