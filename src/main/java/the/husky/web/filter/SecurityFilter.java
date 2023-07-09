package the.husky.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class SecurityFilter implements Filter {

    private final List<String> PERMITTED_URI = List.of("/login", "/task", "/user_add", "image.png",
            "/static", "/favicon.ico", "/wrong_answer.html");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        String requestURI = request.getRequestURI();

        if (isAuthenticated || isPermittedURI(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    private boolean isPermittedURI(String uri) {
        for (String permittedUri : PERMITTED_URI) {
            if (uri.contains(permittedUri)) {
                return true;
            }
        }
        return false;
    }
}
