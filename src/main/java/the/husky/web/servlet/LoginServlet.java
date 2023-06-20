package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = buildUser(request);

        if (user.getUserId() > 0) {
            response.sendRedirect("/vehicle/all");
        } else {
            response.sendRedirect("/user/add");
        }
    }

    private User buildUser(HttpServletRequest request) {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }
}