package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.service.VehicleService;

import java.io.IOException;
import java.util.List;

public class VehicleFilterServlet extends HttpServlet {
    private VehicleService service;

    public VehicleFilterServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String manufacturer = request.getParameter("manufacturer");
        List<Vehicle> filteredVehicles = service.filterByManufacturer(manufacturer);
        List<Vehicle> allVehicles = service.findAll();
        List<String> manufacturers = VehicleManufacturer.getManufacturers();

        if (manufacturer != null && !manufacturer.isEmpty()) {
            request.setAttribute("vehicles", filteredVehicles);
        } else {
            request.setAttribute("vehicles", allVehicles);
        }

        request.setAttribute("manufacturers", manufacturers);
        request.getRequestDispatcher("/vehicle_all").forward(request, response);
    }
}
