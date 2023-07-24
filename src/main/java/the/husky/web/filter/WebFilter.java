package the.husky.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class WebFilter implements Filter {

    private final List<String> PERMITTED_URI = List.of("/login", "/task", "/user_add", "image.png",
            "/static", "/favicon.ico", "/wrong_answer.html");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (isGodModeRequest(requestURI)) {
            if (isAdmin(request) || isPermittedURI(requestURI)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/login");
            }
        } else {
            if ((isUser(request) || isPermittedURI(requestURI)) || isAdmin(request)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/login");
            }
        }
    }

    private boolean isGodModeRequest(String uri) {
        return uri.contains("/user_all") || uri.contains("/user_edit/*") || uri.contains("/user/delete/*");
    }

    private boolean isPermittedURI(String uri) {
        for (String permittedUri : PERMITTED_URI) {
            if (uri.contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    private boolean isUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAdmin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("admin-token")) {
                    return true;
                }
            }
        }
        return false;
    }
}
