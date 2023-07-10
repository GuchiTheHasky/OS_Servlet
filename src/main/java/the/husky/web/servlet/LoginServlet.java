package the.husky.web.servlet;

import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.security.Credentials;
import the.husky.security.SecurityService;
import the.husky.security.session.Session;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;

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

        Session session = securityService.createSession(credentials);
        validateSession(response, session);

        if (session.getRole().getRole().equalsIgnoreCase("User")) {
            String user = "user-token";
            setCookies(response, credentials, user);
            response.sendRedirect("/vehicle_all");
        } else if (session.getRole().getRole().equalsIgnoreCase("Admin")) {
            String admin = "admin-token";
            setCookies(response, credentials, admin);
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/login");
        }
    }

    private void setCookies(HttpServletResponse response, Credentials credentials, String tokenRole) {
        Cookie cookie = new Cookie(tokenRole, generateTokenBase64(credentials.getPassword()));
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    private static void validateSession(HttpServletResponse response, Session session) throws IOException {
        if (session == null) {
            response.sendRedirect("/login");
        }
    }

    private static void validateCredentials(HttpServletResponse response, Credentials credentials) throws IOException {
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


//    private String generateTokenArgon(String password) {
//        Argon2 argon2 = Argon2Factory.create();
//        String salt = generateSalt();
//        return argon2.hash(64, 32768, 2, password.getBytes()) + salt;
//    }

    private String generateTokenBase64(String password) {
        byte[] bytes = Base64.getEncoder().encode(password.getBytes());
        return new String(bytes) + generateSalt();
    }

    private String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBytes = new byte[16];
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








    private User buildUser(HttpServletRequest request) {
        String name = request.getParameter("login");
        String password = request.getParameter("password");
        return User.builder()
                .login(name)
                .password(password)
                .build();
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Credentials credentials = getCredentials(request);
//
//
//        User user = buildUser(request);
//        boolean isAuthenticated = securityService.authenticate(user);
//
//        if (isAuthenticated) {
//            String token = securityService.getToken(user.getPassword());
//            HttpSession session = request.getSession(true);
//            session.setAttribute("user", user);
//            response.addCookie(new Cookie("token", token));
//            response.sendRedirect("/vehicle_all");
//        } else {
//            response.sendRedirect("/login");
//        }
//    }


}