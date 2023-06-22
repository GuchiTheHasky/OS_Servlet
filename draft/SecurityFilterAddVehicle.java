package the.husky.security.filter.vehicle;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityFilterAddVehicle implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("POST")) {
            String manufacturer = request.getParameter("manufacture");
            String engineType = request.getParameter("engineType");
            String model = request.getParameter("model");
            double price = Double.parseDouble(request.getParameter("price"));
            int age = Integer.parseInt(request.getParameter("age"));
            double weight = Double.parseDouble(request.getParameter("weight"));

            if (manufacturer == null || engineType == null || model == null || price <= 0 || age < 0 || weight <= 0) {
                response.sendRedirect("/vehicle/all");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
