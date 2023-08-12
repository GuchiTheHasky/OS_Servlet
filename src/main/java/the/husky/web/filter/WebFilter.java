package the.husky.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import the.husky.security.entity.Session;
import the.husky.service.WebService;

import java.io.IOException;
import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class WebFilter implements Filter, MyFilter {
    private final List<String> PERMITTED_URI = List.of("/login", "/task", "/user_add", "image.png",
            "/static", "/favicon.ico", "/wrong_answer.html");
    private final List<String> USER_ACCESS_LEVEL_URI = List.of("/login", "/logout", "/task", "/user_add",
            "image.png", "/static", "/favicon.ico", "/wrong_answer.html", "/vehicle_all", "/vehicle_add",
            "/vehicle_edit", "/vehicle/delete", "/cart", "/card");
    private final String[] COOKIE_NAME = {"user-token", "admin-token"};
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

        String cookieToken = extractTokenValue(request, COOKIE_NAME);

        if (cookieToken == null) {
            response.sendRedirect("/login");
            return;
        }

        Session currentSession = getSession(cookieToken);
        validateSession(response, currentSession, "/login");
        String admin = "Admin";
        String user = "User";
        String guest = "Guest";

        if (identifyUser(currentSession, cookieToken, admin) && !isSessionTerminated(cookieToken)) {
            filterChain.doFilter(request, response);
        } else if ((identifyUser(currentSession, cookieToken, user) ||
                identifyUser(currentSession, cookieToken, guest) )&&
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

    private boolean isPermittedURI(String uri) {
        for (String permittedUri : PERMITTED_URI) {
            if (uri.contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }
}
