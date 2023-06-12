package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import the.husky.service.VehicleService;

public class VehicleManufacturerServlet extends HttpServlet {
    private final VehicleService SERVICE;

    public VehicleManufacturerServlet(VehicleService vehicleService) {
        SERVICE = vehicleService;
    }


}
