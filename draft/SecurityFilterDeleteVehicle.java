package the.husky.security.filter.vehicle;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.service.VehicleService;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilterDeleteVehicle implements Filter {
    private VehicleService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("POST")) {
            int id = Integer.parseInt(request.getParameter("vehicle_id"));
            if (service.getById(id) != null) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
