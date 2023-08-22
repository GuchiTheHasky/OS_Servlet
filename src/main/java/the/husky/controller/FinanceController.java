package the.husky.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import the.husky.entity.Vehicle;
import the.husky.service.SignificantService;
import the.husky.service.VehicleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class FinanceController {
    private final List<Vehicle> cart = Collections.synchronizedList(new ArrayList<>());
    @Autowired
    private SignificantService significantService;

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("vehicles", cart);
        return "cart";
    }

    @PostMapping("/cart")
    public String cartPost(@RequestParam("vehicleId") int id, Model model) {
        Vehicle vehicle = significantService.getVehicleService().findVehicleById(id);
        cart.add(vehicle);
        model.addAttribute("vehicles", cart);
        log.info("Vehicle {} added to cart", vehicle);
        return "redirect:cart";
    }

    @PostMapping("/payment")
    public String clearCart() {
        for (Vehicle vehicle : cart) {
            significantService.getVehicleService().delete(vehicle.getVehicleId());
        }
        cart.clear();
        log.info("Cart cleared");
        return "redirect:vehicle_all";
    }
}
