package the.husky.web.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.security.entity.Session;

import java.io.IOException;

public interface MyFilter {
    default void validateSession(HttpServletResponse response, Session session, String path) throws IOException {
        if (session == null) {
            response.sendRedirect(path);
        }
    }

    default boolean identifyUser(Session session, String token, String role) {
        return session.getRole().getRole().equalsIgnoreCase(role) &&
                session.getToken().equals(token);
    }

    default String extractTokenValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName()) || "admin-token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
