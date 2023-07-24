package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.exception.ParseRequestException;
import the.husky.service.WebService;

import java.io.IOException;

@AllArgsConstructor
public class DeleteVehicleServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("vehicleId");
        int id = parseIdParameter(idStr);
        webService.getCacheService().deleteVehicle(id);
        response.sendRedirect("/vehicle_all");
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ParseRequestException("Error, wrong ID.");
        }
    }
}
