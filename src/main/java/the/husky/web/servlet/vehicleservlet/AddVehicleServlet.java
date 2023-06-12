package the.husky.web.servlet.vehicleservlet;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AddVehicleServlet extends HttpServlet {
    private final VehicleService SERVICE;

    public AddVehicleServlet(VehicleService vehicleService) {
        SERVICE = vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_vehicle.html");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vehicle vehicle = buildVehicle(req);
        SERVICE.add(vehicle);
        resp.sendRedirect("/vehicle/all");
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
