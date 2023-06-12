package the.husky.web.servlet.userservlet;

import the.husky.entity.user.User;
import the.husky.service.UserService;
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
        String idParam = request.getParameter("id");
        int id;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
            return;
        }

        User user = userService.getUserById(id);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process the form submission for user update
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        // Validate the input data
        if (idParam == null || name == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid form data");
            return;
        }

        // Parse the user ID
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
            return;
        }

        // Retrieve the existing user from the database
        User user = userService.getUserById(id);

        if (user != null) {
            // Update the user details
            user.setName(name);
            user.setPassword(password);

            // Update the user in the database
            userService.update(user);

            // Redirect to the updated user list page
            response.sendRedirect("/user/all");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

}
