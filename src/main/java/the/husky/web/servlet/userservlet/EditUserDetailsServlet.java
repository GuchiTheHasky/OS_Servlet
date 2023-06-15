package the.husky.web.servlet.userservlet;

import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.web.security.SecurityService;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditUserDetailsServlet extends HttpServlet {
    private UserService userService;

    public EditUserDetailsServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (DataAccessException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid access to update data.");
        }

        if (user != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("user", user);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().println(PageGenerator.instance().getPage("user_details.html", params));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (DataAccessException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid access to update data.");
        }

        assert user != null;
        user.setName(name);
        user.setPassword(password);

        try {
            userService.update(user);
        } catch (DataAccessException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while updating the user in the database.");
        }
        response.sendRedirect("/user/all");
    }
}
