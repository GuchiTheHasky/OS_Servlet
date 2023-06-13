package the.husky.web.servlet.vehicleservlet;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;
import the.husky.web.auth.UserAuthenticate;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddVehicleServlet extends HttpServlet {
    private VehicleService service;

    public AddVehicleServlet(VehicleService vehicleService) {
        service = vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (UserAuthenticate.isAuthenticate(request)) {
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("add_vehicle.html");
            response.getWriter().write(page);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Vehicle vehicle = buildVehicle(req);
        service.add(vehicle);
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
