package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
import the.husky.service.UserService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private UserService service;

    public GetAllUsersServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = service.getAll();

        PageGenerator pageGenerator = PageGenerator.instance();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("users", users);
        String page = pageGenerator.getPage("user_cache.html", parameters);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
