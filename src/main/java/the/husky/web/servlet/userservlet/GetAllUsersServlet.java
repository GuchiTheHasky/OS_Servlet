package the.husky.web.servlet.userservlet;

import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.web.auth.UserAuthenticate;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private UserService userService;


    public GetAllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (UserAuthenticate.isAuthenticate(request)) {
            List<User> users;
            try {
                users = userService.getAll();
            } catch (DataAccessException e) {
                throw new ServletException("An error occurred while retrieving the user list.", e);
            }
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("users", users);
            String page = pageGenerator.getPage("user_list.html", parameters);
            response.getWriter().write(page);
        }
        response.sendRedirect("/login");
    }
}
