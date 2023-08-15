package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import the.husky.security.entity.Credentials;
import the.husky.security.entity.Session;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@Slf4j
@NoArgsConstructor
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
        Credentials credentials = createCredentials(request);
        validateCredentials(response, credentials);

        Session session = webService.getSecurityService().createSession(credentials);
        validateSession(response, session);

        if (session.getRole().getRole().equalsIgnoreCase("USER")) {
            String user = "user-token";
            setCookies(response, session.getToken(), user);
            response.sendRedirect("/vehicle_all");
        } else if (session.getRole().getRole().equalsIgnoreCase("Admin")) {
            String admin = "admin-token";
            setCookies(response, session.getToken(), admin);
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
            log.error("Session is null");
            response.sendRedirect("/login");
        }
    }

    private void validateCredentials(HttpServletResponse response, Credentials credentials) throws IOException {
        if (credentials.getLogin().isEmpty() || credentials.getPassword().isEmpty()) {
            log.error("Credentials is empty");
            response.sendRedirect("/login");
        }
    }

    private Credentials createCredentials(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return Credentials.builder()
                .login(login)
                .password(password)
                .build();
    }
}