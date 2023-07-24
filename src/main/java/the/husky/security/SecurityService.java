package the.husky.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import the.husky.entity.user.User;
import the.husky.security.entity.Credentials;
import the.husky.security.entity.Role;
import the.husky.security.entity.Session;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Getter
@NoArgsConstructor
public class SecurityService implements Security {
    @Override
    public Session createSession(Credentials credentials) {
        LocalDateTime expireDate = getExpirationDate();
        if (credentials.getLogin().equals("admin")) {
            return Session.builder()
                    .role(Role.ADMIN)
                    .expireDate(expireDate)
                    .build();
        } else {
            return Session.builder()
                    .role(Role.USER)
                    .expireDate(expireDate)
                    .build();
        }
    }

    @Override
    public String generateToken(String password) {
        byte[] bytes = Base64.getEncoder().encode(password.getBytes());
        return new String(bytes) + generateSalt();
    }

    private String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        int saltLength = 16;
        byte[] saltBytes = new byte[saltLength];
        secureRandom.nextBytes(saltBytes);
        return bytesToString(saltBytes);
    }

    private String bytesToString(byte... array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : array) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    private LocalDateTime getExpirationDate() {
        LocalDateTime date = LocalDateTime.now();
        long expirationTime = 1;
        return date.plusHours(expirationTime);
    }
}
