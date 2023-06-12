package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.web.auth.UserAuthenticate;

import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    private UserService service;
    private UserAuthenticate userAuthenticate;


    public DeleteUserServlet(UserService service, UserAuthenticate userAuthenticate) {
        this.service = service;
        this.userAuthenticate = userAuthenticate;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (UserAuthenticate.isAuthenticate(request)) {
            int id = Integer.parseInt(request.getParameter("id"));

            try {
                User user = service.getUserById(id);
                if (user != null) {
                    try {
                        service.delete(id);
                        userAuthenticate.deleteExistingUser(user);
                        response.sendRedirect("/user/all");
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
        response.sendRedirect("/login");
    }
}

