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
public class PaymentFilter implements Filter {
    private final String[] cookieNames = {"user-token", "admin-token", "guest-token"};
    private WebService webService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String cookieToken = FilterUtil.extractTokenValue(request, cookieNames);

        if (cookieToken == null) {
            response.sendRedirect("/login");
            return;
        }

        Session currentSession = getSession(cookieToken);
        FilterUtil.validateSession(response, currentSession, "/login");

        if (FilterUtil.identifyUser(currentSession, cookieToken, "User")) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/cart");
        }
    }

    private Session getSession(String token) {
        return webService.getSecurityService().getSession(token);
    }
}
