package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.entity.user.User;
import the.husky.entity.vehicle.Vehicle;
import the.husky.service.UserService;
import the.husky.service.VehicleService;

import java.io.IOException;

public class DeleteVehicleServlet extends HttpServlet {
    private VehicleService service;

    public DeleteVehicleServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("vehicle_id");
        int id;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
            return;
        }

        Vehicle vehicle = service.getById(id);

        if (vehicle != null) {
            service.delete(id);
            response.sendRedirect("/vehicle/all");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
