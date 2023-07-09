package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EditVehicleServlet extends HttpServlet {
    private VehicleService vehicleService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vehicleId = request.getParameter("vehicle_id");
        int id = parseIdParameter(vehicleId);

        Optional<Vehicle> vehicle = vehicleService.getById(id);

        Map<String, Object> data = new HashMap<>();
        data.put("vehicle", vehicle.get());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String page = PageGenerator.instance().getPage("vehicle_edit.html", data);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Vehicle updatedVehicle = buildVehicle(request);
        vehicleService.edit(updatedVehicle);
        response.sendRedirect("/vehicle_all");
    }

    private Vehicle buildVehicle(HttpServletRequest request) {
        String idStr = request.getParameter("vehicleId");
        int id = parseIdParameter(idStr);
        String manufacture = request.getParameter("manufacturer");
        String engineType = request.getParameter("enginetype");
        String model = request.getParameter("model");
        int price = Integer.parseInt(request.getParameter("price"));
        int age = Integer.parseInt(request.getParameter("age"));
        int weight = Integer.parseInt(request.getParameter("weight"));
        return Vehicle.builder()
                .vehicleId(id)
                .manufacture(VehicleManufacturer.getByManufacture(manufacture))
                .engineType(EngineType.getEngineType(engineType))
                .model(model)
                .price(price)
                .age(age)
                .weight(weight)
                .build();
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error, wrong ID.");
        }
    }
}
