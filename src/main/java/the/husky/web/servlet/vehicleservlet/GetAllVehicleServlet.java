package the.husky.web.servlet.vehicleservlet;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.web.security.SecurityService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAllVehicleServlet extends HttpServlet {
    private VehicleService service;

    public GetAllVehicleServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manufacturerFilter = request.getParameter("manufacturer");
        String engineTypeFilter = request.getParameter("engineType");

        List<Vehicle> vehicles = service.getAll();
        PageGenerator pageGenerator = PageGenerator.instance();

        if (manufacturerFilter != null && !manufacturerFilter.isEmpty()) {
            vehicles = filterByManufacturer(vehicles, manufacturerFilter);
        }

        if (engineTypeFilter != null && !engineTypeFilter.isEmpty()) {
            vehicles = filterByEngineType(vehicles, engineTypeFilter);
        }

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("vehicles", vehicles);
        parameters.put("manufacturers", VehicleManufacturer.getManufacturers());
        parameters.put("engineTypes", EngineType.getAllEngineTypes());

        String page = pageGenerator.getPage("vehicle_list.html", parameters);
        response.getWriter().write(page);
    }

    private List<Vehicle> filterByManufacturer(List<Vehicle> vehicles, String manufacturerFilter) {
        List<Vehicle> filteredVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getManufacture().getManufacture().equalsIgnoreCase(manufacturerFilter)) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }

    private List<Vehicle> filterByEngineType(List<Vehicle> vehicles, String engineFilter) {
        List<Vehicle> filteredVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getEngineType().getType().equalsIgnoreCase(engineFilter)) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
}
