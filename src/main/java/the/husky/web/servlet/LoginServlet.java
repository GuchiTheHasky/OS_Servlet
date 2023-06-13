package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import the.husky.entity.user.User;
import the.husky.web.util.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = buildUser(username, password);

        if (user != null) {
            String token = UUID.randomUUID().toString();
            String salt = generateSalt();
            user.setToken(token + salt);
            response.addCookie(new Cookie("user-valid", token));
            response.sendRedirect("/vehicle/all");
        } else {
            response.sendRedirect("/login");
        }
    }

    private User buildUser(String name, String password) {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }

    private String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        int maxLength = 32;
        int saltLength = new Random().nextInt(maxLength);
        byte[] salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}