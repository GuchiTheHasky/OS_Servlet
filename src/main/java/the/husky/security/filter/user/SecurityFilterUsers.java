package the.husky.security.filter.user;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.security.SecurityService;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilterUsers implements Filter {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("GET")) {
            if (!securityService.isAuthenticate(request)) {
                filterChain.doFilter(request, response);
            }
            response.sendRedirect("/login");
        }
    }
}
