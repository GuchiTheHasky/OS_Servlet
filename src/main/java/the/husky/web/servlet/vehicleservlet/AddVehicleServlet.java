package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

public class AddVehicleServlet extends HttpServlet {
    private VehicleService vehicleService;

    public AddVehicleServlet(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("vehicle_add.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Vehicle vehicle = buildVehicle(req);
        vehicleService.add(vehicle);
        resp.sendRedirect("/vehicle_all");
    }

    private Vehicle buildVehicle(HttpServletRequest request) {
        String manufactureId = request.getParameter("manufacture");
        String engineType = request.getParameter("engineType");
        return Vehicle.builder()
                .manufacture(VehicleManufacturer.getByManufacture(manufactureId))
                .engineType(EngineType.getEngineType(engineType))
                .model(request.getParameter("model"))
                .price(Double.parseDouble(request.getParameter("price")))
                .age(Integer.parseInt(request.getParameter("age")))
                .weight(Integer.parseInt(request.getParameter("weight")))
                .build();
    }
}
