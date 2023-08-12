package the.husky.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.security.entity.Session;
import the.husky.service.WebService;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class CardFilter implements Filter, MyFilter {
    private final String[] COOKIE_NAME = {"user-token", "admin-token"};
    private WebService webService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String cookieToken = extractTokenValue(request, COOKIE_NAME);

        if (cookieToken == null) {
            response.sendRedirect("/login");
            return;
        }

        Session currentSession = getSession(cookieToken);
        validateSession(response, currentSession, "/login");

        if (identifyUser(currentSession, cookieToken, "User")) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/cart");
        }
    }

    private Session getSession(String token) {
        return webService.getSecurityService().getSession(token);
    }
}
