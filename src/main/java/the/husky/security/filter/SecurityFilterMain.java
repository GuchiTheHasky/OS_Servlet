package the.husky.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import the.husky.security.SecurityService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SecurityFilterMain implements Filter {

    private SecurityService securityService;

    private final List<String> PERMITTED_URI = List.of("/login", "/task", "/user_add", "/favicon.ico", "image.png",
            "/static", "/wrong_answer.html");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        String requestURI = request.getRequestURI();

        if (isAuthenticated || isPermittedURI(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/login");
        }


//        boolean isAuthenticated = checkAuthentication(request);
//        String requestURI = request.getRequestURI();
//        if (isAuthenticated || isPermittedURI(requestURI)) {
//            filterChain.doFilter(request, response);
//        } else {
//            response.sendRedirect("/login");
//        }
    }

    private boolean isPermittedURI(String uri) {
        for (String permittedUri : PERMITTED_URI) {
            if (uri.contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error, wrong ID.");
        }
    }

    private boolean isToken(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            return cookie.getValue().contains("token");
        }
        return false;
    }

    public boolean isAdministrator(String login, String password) {
        return login.equals("admin") && password.equals("admin");
    }

    private String getLogin(HttpServletRequest request) {
        Optional<String> value = Optional.ofNullable(request.getParameter("user_name"));
        return value.get();
    }

    private String getPassword(HttpServletRequest request) {
        Optional<String> value = Optional.ofNullable(request.getParameter("password"));
        return value.get();
    }





    private boolean isRequestPermitted(HttpServletRequest request) {
        for (String uri : PERMITTED_URI) {
            if (request.getRequestURI().contains(uri)) {
                return true;
            }
        }
        return false;
    }
}