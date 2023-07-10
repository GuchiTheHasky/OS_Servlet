package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tokenRole = tokenRole(request);
        assert tokenRole != null;
        Cookie cookie = new Cookie(tokenRole, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/login");
    }

    private String tokenRole(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return "user-token";
                } else if (cookie.getName().equals("admin-token")) {
                    return "admin-token";
                }
            }
        }
        return null;
    }
}