package the.husky.web.servlet.userservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.entity.vehicle.Vehicle;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class CartServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vehicle> vehicles = webService.getCacheService().getCartCache();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> parameters = Collections.synchronizedMap(new HashMap<>());
        parameters.put("vehicles", vehicles);
        String page = pageGenerator.getPage("cart.html", parameters);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("vehicleId");
        int id = Integer.parseInt(strId);
        webService.getCacheService().addToCart(id);
        doGet(request, response);

    }
}
