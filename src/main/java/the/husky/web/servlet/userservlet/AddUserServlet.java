package the.husky.web.servlet.userservlet;

import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.security.SecurityService;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AddUserServlet extends HttpServlet {
    private UserService service;
    private SecurityService securityService;


    public AddUserServlet(UserService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_user.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = buildUser(request);
        try {

            securityService.addNewUser(user);

            List<User> users = service.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/user_all").forward(request, response);
        } catch (DataAccessException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding user.");
        }
    }

    private User buildUser(HttpServletRequest request) {
        return User.builder()
                .name(request.getParameter("user_name"))
                .password(request.getParameter("password"))
                .build();
    }
}
