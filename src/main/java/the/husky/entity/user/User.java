package the.husky.entity.user;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;
    private String name;
    private String password;
    private LocalDateTime registrationTime;
    private String token;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
