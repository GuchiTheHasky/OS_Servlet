package the.husky.web.servlet.userservlet;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;
import the.husky.service.UserService;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EditUserServlet extends HttpServlet {
    private UserService userService;

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<User> user = userService.getUserById(id);

        Map<String, Object> params = new HashMap<>();
        params.put("user", user);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println(PageGenerator.instance().getPage("user_details.html", params));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        // todo тут просто забілдити юзера і передати новий юзер в сервіс
        // todo отримання і білдення юзера можна запаувати в метод

        Optional<User> user = Optional.ofNullable(User.builder()
                .name(name)
                .password(password)
                .build());

        try {
            userService.update(user.get());
        } catch (DataAccessException e) { // todo цей кетч можна забрати
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while updating the user in the database.");
        }
        response.sendRedirect("/user/all");
    }
}
