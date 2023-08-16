package the.husky.web.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.security.entity.Session;

import java.io.IOException;

public class FilterUtil {
    public static void validateSession(HttpServletResponse response, Session session, String path) throws IOException {
        if (session == null) {
            response.sendRedirect(path);
        }
    }

    public static boolean identifyUser(Session session, String token, String role) {
        return session.getRole().getRole().equalsIgnoreCase(role) &&
                session.getToken().equals(token);
    }

    public static String extractTokenValue(HttpServletRequest request, String... cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                for (String currentName : cookieName) {
                    if (cookie.getName().equals(currentName)) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }
}
