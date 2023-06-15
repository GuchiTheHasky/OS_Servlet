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
import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private UserService service;

    public GetAllUsersServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users;
        try {
            users = service.getAll();
        } catch (DataAccessException e) {
            throw new ServletException("An error occurred while retrieving the user list.", e);
        }
        PageGenerator pageGenerator = PageGenerator.instance();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("users", users);
        String page = pageGenerator.getPage("user_list.html", parameters);
        response.getWriter().write(page);
    }

    @Override // todo
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
