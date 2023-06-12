package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import lombok.Setter;
import the.husky.entity.user.User;
import the.husky.service.UserService;
import the.husky.web.auth.UserAuthenticate;
import the.husky.web.util.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@Setter
public class LoginServlet extends HttpServlet {

    private UserAuthenticate userAuthenticate;
    private UserService service;

    public LoginServlet(UserService service, UserAuthenticate userAuthenticate) {
        this.service = service;
        this.userAuthenticate = userAuthenticate;
    }

    public LoginServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userAuthenticate.authenticate(username, password);

        if (user != null && !userAuthenticate.isAuthenticated(user)) {
            String token = UUID.randomUUID().toString();
            System.out.println("token " + token);

            resp.addCookie(new Cookie("user-token", token));
            resp.addCookie(new Cookie("preferredlanguage", "en"));
            resp.sendRedirect("vehicle/all");
        } else {
            resp.sendRedirect("/login");
        }
    }
}

