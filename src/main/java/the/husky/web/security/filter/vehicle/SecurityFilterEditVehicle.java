package the.husky.web.security.filter.vehicle;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.service.VehicleService;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilterEditVehicle implements Filter {
    private VehicleService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        int vehicleId = Integer.parseInt(request.getParameter("vehicle_id"));

        if (service.getById(vehicleId) == null) {
            response.sendRedirect(request.getContextPath() + "/vehicle/all");
        } else if (!isValidVehicleData(request)) {
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Invalid edit data");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isValidVehicleData(HttpServletRequest request) {
        return request.getParameter("manufacturer") != null &&
                request.getParameter("engineType") != null &&
                request.getParameter("model") != null &&
                Double.parseDouble(request.getParameter("price")) > 0 &&
                Integer.parseInt(request.getParameter("age")) >= 0 &&
                Integer.parseInt(request.getParameter("weight")) > 0;
    }
}
