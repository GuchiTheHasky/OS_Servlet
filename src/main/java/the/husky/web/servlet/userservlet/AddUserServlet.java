package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@AllArgsConstructor
public class AddUserServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("user_add.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = buildUser(request);
        if (isUserExists(user.getLogin())) {
            response.sendRedirect("/user_add");
        } else {
            webService.getCacheService().addUser(user);
            response.sendRedirect("/login");
        }
    }

    private User buildUser(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        return new User(login, password);
    }

    private boolean isUserExists(String login) {
        return webService.getCacheService().loginAlreadyExist(login);
    }
}