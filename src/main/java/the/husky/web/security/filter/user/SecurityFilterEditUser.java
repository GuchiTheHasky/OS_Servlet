package the.husky.web.security.filter.user;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilterEditUser implements Filter {

    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("POST")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = null;
            try {
                user = userService.getUserById(id);
            } catch (DataAccessException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid access to update data.");
            }
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            if (request.getParameter("id") == null) {
                response.sendRedirect("/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
