package the.husky.web.servlet.vehicleservlet;

import lombok.AllArgsConstructor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EditVehicleServlet extends HttpServlet {
    private VehicleService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicle_id"));

        Vehicle vehicle = service.getById(vehicleId);

        List<Vehicle> vehicles = service.findAll();

        Map<String, Object> data = new HashMap<>();
        data.put("vehicles", vehicles);
        data.put("vehicle", vehicle);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String page = PageGenerator.instance().getPage("edit_vehicle.html", data);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("vehicle_id"));

        Vehicle editedVehicle = service.getById(id);

        String updatedManufacturerString = request.getParameter("manufacturer");
        VehicleManufacturer updatedManufacturer = VehicleManufacturer.valueOf(updatedManufacturerString);
        String updatedEngineTypeString = request.getParameter("engineType");
        EngineType updatedEngineType = EngineType.valueOf(updatedEngineTypeString);
        String model = request.getParameter("model");
        double price = Double.parseDouble(request.getParameter("price"));
        int age = Integer.parseInt(request.getParameter("age"));
        int weight = Integer.parseInt(request.getParameter("weight"));

        editedVehicle.setManufacture(updatedManufacturer);
        editedVehicle.setEngineType(updatedEngineType);
        editedVehicle.setModel(model);
        editedVehicle.setPrice(price);
        editedVehicle.setAge(age);
        editedVehicle.setWeight(weight);

        service.edit(editedVehicle);

        response.sendRedirect(request.getContextPath() + "/vehicle/all");
    }
}
