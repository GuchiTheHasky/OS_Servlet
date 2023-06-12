package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
            return;
        }

        User user = service.getUserById(id);

        if (user != null) {
            service.delete(id);
            User deletedUser = service.getUserById(id);
            if (deletedUser != null) {
                userAuthenticate.deleteExistingUser(deletedUser);
            }

            response.sendRedirect("/user/all");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

