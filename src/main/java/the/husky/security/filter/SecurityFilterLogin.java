package the.husky.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.security.SecurityService;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
public class SecurityFilterLogin implements Filter {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValid(username) && isValid(password)) {
            User user = new User(username, password);
            if (securityService.authenticateUser(user) != null) {
            String token = generateToken();
            user.setToken(token);
            response.addCookie(new Cookie("token", token));
            response.sendRedirect("/vehicle/all");
            }

        } else {
            filterChain.doFilter(request, response);
        }
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

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}

