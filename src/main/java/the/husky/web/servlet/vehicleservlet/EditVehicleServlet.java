package the.husky.web.servlet.vehicleservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;
import the.husky.exception.ParseRequestException;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class EditVehicleServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Vehicle vehicle = getToUpdate(request);

        Map<String, Object> data = Collections.synchronizedMap(new HashMap<>());
        data.put("vehicle", vehicle);

        String page = PageGenerator.instance().getPage("vehicle_edit.html", data);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Vehicle updatedVehicle = buildVehicle(request);
        webService.getCacheService().updateVehicle(updatedVehicle);
        response.sendRedirect("/vehicle_all");
    }

    private Vehicle getToUpdate(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicle_id");
        int id = parseIdParameter(vehicleId);
        return webService.getCacheService().getVehicleById(id);
    }

    private Vehicle buildVehicle(HttpServletRequest request) {
        String idStr = request.getParameter("vehicleId");
        int id = parseIdParameter(idStr);
        String manufacture = request.getParameter("manufacturer");
        String engineType = request.getParameter("enginetype");
        String model = request.getParameter("model");
        String price = (request.getParameter("price"));
        String age = (request.getParameter("age").trim());
        String weight = (request.getParameter("weight"));
        return Vehicle.builder()
                .vehicleId(id)
                .manufacture(VehicleManufacturer.getByManufacture(manufacture))
                .engineType(EngineType.getEngineType(engineType))
                .model(model)
                .price(convertPrice(Double.parseDouble(price)))
                .age(Integer.parseInt(age))
                .weight(convertWeight(Integer.parseInt(weight)))
                .build();
    }

    private double convertPrice(Double price) {
        return price < 0 ? 0 : price;
    }

    private int convertWeight(Integer weight) {
        return weight <= 1500 ? 1500 : weight;
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ParseRequestException("Error, wrong ID.");
        }
    }
}
