package the.husky.web.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.web.security.SecurityService;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilterLogin implements Filter {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("GET")) {
            filterChain.doFilter(request, response);
        } else {


            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (isValid(username) && isValid(password)) {
                if (securityService.isAuthenticate(request)) {
                    filterChain.doFilter(request, response);
                }
            } else {
                response.sendRedirect("/login");
            }
        }
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}

