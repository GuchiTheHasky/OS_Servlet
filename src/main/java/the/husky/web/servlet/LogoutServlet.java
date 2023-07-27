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
            throws IOException {
        String tokenName = getTokenName(request);
        Cookie cookie = new Cookie(tokenName, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/login");
    }

    private String getTokenName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("user-token")) {
                    return "user-token";
                }
            }
        }
        return "admin-token";
    }
}