package the.husky.web.servlet;

import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.service.WebService;
import the.husky.security.entity.Credentials;
import the.husky.security.entity.Session;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = getCredentials(request);
        validateCredentials(response, credentials);

        Session session = webService.getSecurityService().createSession(credentials);
        validateSession(response, session);

        var tokenValue = webService.getSecurityService().generateToken(credentials.getPassword());

        if (session.getRole().getRole().equalsIgnoreCase("USER")) {
            String user = "user-token";
            setCookies(response, tokenValue, user);
            response.sendRedirect("/vehicle_all");
        } else if (session.getRole().getRole().equalsIgnoreCase("Admin")) {
            String admin = "admin-token";
            setCookies(response, tokenValue, admin);
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/login");
        }
    }

    private void setCookies(HttpServletResponse response, String token, String tokenRole) {
        Cookie cookie = new Cookie(tokenRole, token);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    private void validateSession(HttpServletResponse response, Session session) throws IOException {
        if (session == null) {
            response.sendRedirect("/login");
        }
    }

    private void validateCredentials(HttpServletResponse response, Credentials credentials) throws IOException {
        if (credentials.getLogin().isEmpty() || credentials.getPassword().isEmpty()) {
            response.sendRedirect("/login");
        }
    }

    private Credentials getCredentials(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return Credentials.builder()
                .login(login)
                .password(password)
                .build();
    }
}