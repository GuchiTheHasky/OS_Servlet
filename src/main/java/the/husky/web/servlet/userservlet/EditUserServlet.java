package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import the.husky.entity.user.User;
import the.husky.exception.ParseRequestException;
import the.husky.service.UserService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getToUpdate(request);

        Map<String, Object> params = new HashMap<>();
        params.put("user", user);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println(PageGenerator.instance().getPage("user_edit.html", params));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = getNewUser(request);
        userService.update(user);
        response.sendRedirect("/user_all");
    }

    private User getToUpdate(HttpServletRequest request) {
        String str = request.getParameter("id");
        int id = parseIdParameter(str);
        return userService.getUserById(id);
    }

    private User getNewUser(HttpServletRequest request) {
        int id = parseIdParameter(request.getParameter("id"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        return User.builder()
                .userId(id)
                .login(login)
                .password(password)
                .build();
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ParseRequestException("Error, wrong ID.");
        }
    }
}
