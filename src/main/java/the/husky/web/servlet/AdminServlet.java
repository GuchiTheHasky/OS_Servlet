package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.HashMap;

@AllArgsConstructor
public class AdminServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        int usersCount = webService.getCacheService().getUsersCache().size();
        int vehiclesCount = webService.getCacheService().getVehiclesCache().size();

        HashMap<String, Object> parameters = putParameters(usersCount, vehiclesCount);

        String page = pageGenerator.getPage("admin.html", parameters);
        response.getWriter().write(page);
    }

    private HashMap<String, Object> putParameters(int usersCount, int vehiclesCount) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("usersCount", usersCount);
        parameters.put("vehiclesCount", vehiclesCount);
        return parameters;
    }
}
