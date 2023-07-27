package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AdminServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        int usersCount = webService.getCacheService().getUsersCache().size();
        int vehiclesCount = webService.getCacheService().getVehiclesCache().size();

        Map<String, Object> parameters = putParameters(usersCount, vehiclesCount);

        String page = pageGenerator.getPage("admin.html", parameters);
        response.getWriter().write(page);
    }

    private Map<String, Object> putParameters(int usersCount, int vehiclesCount) {
        Map<String, Object> parameters = Collections.synchronizedMap(new HashMap<>());
        parameters.put("usersCount", usersCount);
        parameters.put("vehiclesCount", vehiclesCount);
        return parameters;
    }
}
