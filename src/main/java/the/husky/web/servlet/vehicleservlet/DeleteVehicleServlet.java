package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.service.VehicleService;

import java.io.IOException;

public class DeleteVehicleServlet extends HttpServlet {
    private VehicleService service;

    public DeleteVehicleServlet(VehicleService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("vehicleId");
        int id = parseIdParameter(idStr);
        service.delete(id);
        response.sendRedirect("/vehicle_all");
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error, wrong ID.");
        }
    }
}
