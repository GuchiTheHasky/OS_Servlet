package the.husky.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String login;
    private String password;
    private LocalDateTime registrationTime;
}
