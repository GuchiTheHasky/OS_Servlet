package the.husky.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import the.husky.security.entity.Credentials;
import the.husky.security.entity.Role;
import the.husky.security.entity.Session;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
public class SecurityService implements Security {
    private final List<Session> sessionList;

    public SecurityService() {
        this.sessionList = Collections.synchronizedList(new ArrayList<>());
    }

    public Session getSession (String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    public boolean isSessionTerminated(String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                    return false;
                } else {
                    sessionList.remove(session);
                }
            }
        }
        return true;
    }

    @Override
    public Session createSession(Credentials credentials) {
        Role role = credentials.getLogin().equalsIgnoreCase("admin") ? Role.ADMIN : Role.USER;
        String token = generateToken(credentials.getPassword());
        LocalDateTime expireDate = getExpirationDate();
        Session session = new Session(role, token, expireDate);
        sessionList.add(session);
        return session;
    }

    @Override
    public Session createGuestSession() {
        Role role = Role.USER;
        String token = generateToken("guest");
        LocalDateTime expireDate = getExpirationDate();
        Session session = new Session(role, token, expireDate);
        sessionList.add(session);
        return session;
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
