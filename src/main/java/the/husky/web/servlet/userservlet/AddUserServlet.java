package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
import the.husky.security.SecurityService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    private SecurityService securityService;

    public AddUserServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("user_add.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = buildUser(request);
        securityService.addNewUser(user);
        response.sendRedirect("/login");
    }

    private User buildUser(HttpServletRequest request) {
        return User.builder()
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .build();
    }
}