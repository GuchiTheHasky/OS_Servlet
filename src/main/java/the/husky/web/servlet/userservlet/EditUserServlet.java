package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import the.husky.entity.user.User;
import the.husky.exception.ParseRequestException;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class EditUserServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        User user = getToUpdate(request);

        Map<String, Object> params = Collections.synchronizedMap(new HashMap<>());
        params.put("user", user);

        String page = pageGenerator.getPage("user_edit.html", params);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getNewUser(request);
        webService.getCacheService().updateUser(user);
        response.sendRedirect("/user_all");
    }

    private User getToUpdate(HttpServletRequest request) {
        String str = request.getParameter("id");
        int id = parseIdParameter(str);
        return webService.getCacheService().getUserById(id);
    }

    private User getNewUser(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        int id = parseIdParameter(idStr);
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
            log.error("Couldn't parse ID parameter: {}", idParam);
            throw new ParseRequestException("Error, wrong ID.");
        }
    }
}