package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@AllArgsConstructor
public class AddVehicleServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("vehicle_add.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Vehicle vehicle = buildVehicle(req);
        webService.getCacheService().addVehicle(vehicle);
        resp.sendRedirect("/vehicle_all");
    }

    private Vehicle buildVehicle(HttpServletRequest request) {
        String manufactureId = request.getParameter("manufacture");
        String engineType = request.getParameter("engineType");
        String model = request.getParameter("model");
        String price = (request.getParameter("price"));
        String age = (request.getParameter("age"));
        String weight = (request.getParameter("weight"));
        return Vehicle.builder()
                .manufacture(VehicleManufacturer.getByManufacture(manufactureId))
                .engineType(EngineType.getEngineType(engineType))
                .model(model)
                .price(Double.parseDouble(price))
                .age(Integer.parseInt(age))
                .weight(Integer.parseInt(weight))
                .build();
    }
}
