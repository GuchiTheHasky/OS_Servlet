package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class LogoutServlet extends HttpServlet {
    private final String[] cookieNames = {"user-token", "guest-token"};

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
                for (String cookieName : cookieNames) {
                    if (cookieName.equals(cookie.getName())) {
                        return cookieName;
                    }
                }
            }
        }
        return "admin-token";
    }
}