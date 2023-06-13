package the.husky.web.servlet;

import jakarta.servlet.http.Cookie;
import lombok.Setter;
import the.husky.entity.user.User;
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

    public LoginServlet(UserAuthenticate userAuthenticate) {
        this.userAuthenticate = userAuthenticate;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userAuthenticate.authenticate(username, password);

        if (user != null) {
            //String token = UUID.randomUUID().toString();
            response.addCookie(new Cookie("user-token", user.getToken()));
            response.sendRedirect("/vehicle/all");
        } else {
            response.sendRedirect("/login");
        }
    }
}

