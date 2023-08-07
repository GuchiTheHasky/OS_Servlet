package the.husky.entity.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private int userId;
    private String login;
    private String password;
    private LocalDateTime registrationTime;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, LocalDateTime registrationTime) {
        this.login = login;
        this.password = password;
        this.registrationTime = registrationTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }
}
