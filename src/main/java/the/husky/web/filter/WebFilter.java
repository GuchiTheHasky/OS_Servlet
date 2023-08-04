package the.husky.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import the.husky.security.entity.Session;
import the.husky.service.WebService;

import java.io.IOException;
import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class WebFilter implements Filter {
    private final List<String> PERMITTED_URI = List.of("/login", "/task", "/user_add", "image.png",
            "/static", "/favicon.ico", "/wrong_answer.html");
    private final List<String> USER_ACCESS_LEVEL_URI = List.of("/login", "/logout", "/task", "/user_add",
            "image.png", "/static", "/favicon.ico", "/wrong_answer.html", "/vehicle_all", "/vehicle_add",
            "/vehicle_edit", "/vehicle/delete");
    private WebService webService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isPermittedURI(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String cookieToken = extractTokenValue(request);

        if (cookieToken == null) {
            response.sendRedirect("/login");
            return;
        }

        Session currentSession = getSession(cookieToken);
        validateSession(response, currentSession);

        if (isAdmin(currentSession, cookieToken) && !isSessionTerminated(cookieToken)) {
            filterChain.doFilter(request, response);
        } else if (isUser(currentSession, cookieToken) &&
                isUserAccessLevel(request) &&
                !isSessionTerminated(cookieToken)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    private boolean isSessionTerminated(String token) {
        return webService.getSecurityService().isSessionTerminated(token);
    }

    private boolean isAdmin(Session session, String token) {
        return session.getRole().getRole().equalsIgnoreCase("Admin") &&
                session.getToken().equals(token);
    }

    private boolean isUser(Session session, String token) {
        return session.getRole().getRole().equalsIgnoreCase("User") &&
                session.getToken().equals(token);
    }

    private boolean isUserAccessLevel(HttpServletRequest request) {
        for (String permittedUri : USER_ACCESS_LEVEL_URI) {
            if (request.getRequestURI().contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }

    private Session getSession(String token) {
        return webService.getSecurityService().getSession(token);
    }

    private String extractTokenValue(HttpServletRequest request) {
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

    private void validateSession(HttpServletResponse response, Session session) throws IOException {
        if (session == null) {
            response.sendRedirect("/login");
        }
    }

    private boolean isPermittedURI(String uri) {
        for (String permittedUri : PERMITTED_URI) {
            if (uri.contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }
}
