package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.user.User;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class AllUsersServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<User> users = webService.getCacheService().getUsers();

        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> parameters = Collections.synchronizedMap(new HashMap<>());
        parameters.put("users", users);
        String page = pageGenerator.getPage("user_all.html", parameters);
        response.getWriter().write(page);
    }
}
