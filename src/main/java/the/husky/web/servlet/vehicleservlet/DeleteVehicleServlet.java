package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.vehicle.Vehicle;
import the.husky.service.VehicleService;
import the.husky.web.auth.UserAuthenticate;

import java.io.IOException;

public class DeleteVehicleServlet extends HttpServlet {
    private VehicleService service;

    public DeleteVehicleServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (UserAuthenticate.isAuthenticate(request)) {
            int id = Integer.parseInt(request.getParameter("vehicle_id"));

            Vehicle vehicle = service.getById(id);
            if (vehicle != null) {
                service.delete(id);
                response.sendRedirect("/vehicle/all");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
            }
        }
        response.sendRedirect("/login");
    }
}
