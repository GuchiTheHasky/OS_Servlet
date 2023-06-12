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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditVehicleServlet extends HttpServlet {
    private final VehicleService service;

    public EditVehicleServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleIdParam = request.getParameter("vehicle_id");
        if (vehicleIdParam == null || vehicleIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vehicle/all");
            return;
        }

        int vehicleId = Integer.parseInt(vehicleIdParam);

        Vehicle vehicle = service.getById(vehicleId);

        if (vehicle != null) {
            List<Vehicle> vehicles = service.getAll(); // Отримати список всіх автомобілів

            Map<String, Object> data = new HashMap<>();
            data.put("vehicles", vehicles); // Додати список автомобілів до моделі даних
            data.put("vehicle", vehicle);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String page = PageGenerator.instance().getPage("edit_vehicle.html", data);
            response.getWriter().write(page);
        } else {
            response.sendRedirect(request.getContextPath() + "/vehicle/all");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleIdParam = request.getParameter("vehicle_id");
        if (vehicleIdParam == null || vehicleIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vehicle/all");
            return;
        }

        int vehicleId = Integer.parseInt(vehicleIdParam);

        Vehicle editedVehicle = service.getById(vehicleId);

        String updatedManufacturerString = request.getParameter("manufacturer");
        VehicleManufacturer updatedManufacturer = VehicleManufacturer.valueOf(updatedManufacturerString);
        String updatedEngineTypeString = request.getParameter("engineType");
        EngineType updatedEngineType = EngineType.valueOf(updatedEngineTypeString);
        String model = request.getParameter("model");
        double price = Double.parseDouble(request.getParameter("price"));
        int age = Integer.parseInt(request.getParameter("age"));
        int weight = Integer.parseInt(request.getParameter("weight"));

        System.out.println("Updated manufacturer: " + updatedManufacturer);
        System.out.println("Updated engine type: " + updatedEngineType);
        System.out.println("Model: " + model);
        System.out.println("Price: " + price);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight);

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
