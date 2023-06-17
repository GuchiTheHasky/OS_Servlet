package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.web.security.SecurityService;
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

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = buildUser(request);

        if (securityService.authenticateUser(user) != null) {
            String token = generateToken();
            response.addCookie(new Cookie("token", token));
            response.sendRedirect("/vehicle/all");
        } else {
            response.sendRedirect("/user/add");
        }
    }

    private User buildUser(HttpServletRequest request) {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }

    private String generateToken() {
        String rndToken = UUID.randomUUID().toString();
        String salt = generateSalt();
        return rndToken + salt;
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