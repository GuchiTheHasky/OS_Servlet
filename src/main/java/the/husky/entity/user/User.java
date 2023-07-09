package the.husky.entity.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String login;
    private String password;
    private LocalDateTime registrationTime;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    public Optional<Integer> getUserId() {
        return Optional.of(userId);
    }

    public Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public Optional<LocalDateTime> getRegistrationTime() {
        return Optional.ofNullable(registrationTime);
    }
}
