package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.security.SecurityService;

import java.io.IOException;
import java.util.Optional;

public class DeleteUserServlet extends HttpServlet {
    private UserService service;
    private SecurityService securityService;


    public DeleteUserServlet(UserService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Optional<User> user = service.getUserById(id);
            if (user.isPresent()) {
                try {
                    service.delete(id);
                    securityService.deleteExistingUser(user.get());
                    response.sendRedirect("/user_all");
                } catch (DataAccessException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (DataAccessException e) {
            throw new ServletException("Failed to retrieve user", e);
        }
    }
}

