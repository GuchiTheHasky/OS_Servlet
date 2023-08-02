package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class AllVehicleServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String manufacturerFilter = request.getParameter("manufacturer");
        String engineTypeFilter = request.getParameter("engineType");

        List<Vehicle> vehicles = webService.getCacheService().getVehicles();
        PageGenerator pageGenerator = PageGenerator.instance();

        if (manufacturerFilter != null && !manufacturerFilter.isEmpty()) {
            vehicles = filterByManufacturer(vehicles, manufacturerFilter);
        }

        if (engineTypeFilter != null && !engineTypeFilter.isEmpty()) {
            vehicles = filterByEngineType(vehicles, engineTypeFilter);
        }

        Map<String, Object> parameters = putParameters(vehicles);

        String page = pageGenerator.getPage("vehicle_all.html", parameters);
        response.getWriter().write(page);
    }

    private Map<String, Object> putParameters(List<Vehicle> vehicles) {
        Map<String, Object> parameters = Collections.synchronizedMap(new HashMap<>());
        parameters.put("vehicles", vehicles);
        parameters.put("manufacturers", VehicleManufacturer.getManufacturers());
        parameters.put("engineTypes", EngineType.getAllEngineTypes());
        return parameters;
    }

    private List<Vehicle> filterByManufacturer(List<Vehicle> vehicles, String manufacturerFilter) {
        List<Vehicle> filteredVehicles = Collections.synchronizedList(new ArrayList<>());
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getManufacture().getManufacture().equalsIgnoreCase(manufacturerFilter)) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }

    private List<Vehicle> filterByEngineType(List<Vehicle> vehicles, String engineFilter) {
        List<Vehicle> filteredVehicles = Collections.synchronizedList(new ArrayList<>());
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getEngineType().getType().equalsIgnoreCase(engineFilter)) {
                filteredVehicles.add(vehicle);
            }
        }
        return filteredVehicles;
    }
}
