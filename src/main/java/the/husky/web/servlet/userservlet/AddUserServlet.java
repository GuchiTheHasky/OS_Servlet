package the.husky.web.servlet.userservlet;

import the.husky.entity.user.User;
import the.husky.service.UserService;
import the.husky.web.auth.UserAuthenticate;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddUserServlet extends HttpServlet {
    private UserService service;
    private UserAuthenticate userAuthenticate;


    public AddUserServlet(UserService service, UserAuthenticate userAuthenticate) {
        this.service = service;
        this.userAuthenticate = userAuthenticate;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_user.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("user_name");
        String password = request.getParameter("password");

        if (name.isEmpty() || password.isEmpty()) {
            String errorMessage = "Please fill in all required fields.";
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("add_user.html", parameters);
            response.getWriter().write(page);
        } else if (isUserExist(name)) {
            String errorMessage = "Current user name: " + name + " is already in used, try again.";
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("add_user.html", parameters);
            response.getWriter().write(page);
        } else {
            User user = buildUser(request);
            service.add(user);
            userAuthenticate.addNewUser(user);

            List<User> users = service.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/user/all").forward(request, response);
        }
    }

    private User buildUser(HttpServletRequest request) {
        return User.builder()
                .name(request.getParameter("user_name"))
                .password(request.getParameter("password"))
                .build();
    }

    private boolean isUserExist(String name) {
        User currentUser = service.getByName(name);
        if (currentUser != null) {
            List<User> users = service.getAll();
            for (User user : users) {
                if (user.getName().equals(currentUser.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
