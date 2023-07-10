package the.husky.security.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import the.husky.entity.user.User;
import the.husky.security.entity.Role;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Role role;
    private User user;
    private String attribute;
    private LocalDateTime expireDate;
}
