package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.security.SecurityService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = buildUser(request);
        boolean isAuthenticated = securityService.authenticate(user);

        if (isAuthenticated) {
            String token = securityService.getToken(user.getPassword().get());
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.addCookie(new Cookie("token", token));
            response.sendRedirect("/vehicle_all");
        } else {
            response.sendRedirect("/login");
        }
    }

    private User buildUser(HttpServletRequest request) {
        String name = request.getParameter("login");
        String password = request.getParameter("password");
        return User.builder()
                .login(name)
                .password(password)
                .build();
    }
}