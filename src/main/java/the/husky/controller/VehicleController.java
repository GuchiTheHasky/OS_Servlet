package the.husky.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import the.husky.entity.Vehicle;
import the.husky.entity.enums.EngineType;
import the.husky.entity.enums.VehicleManufacturer;
import the.husky.service.VehicleService;

import java.util.List;

@Slf4j
@Controller
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicle_all")
    public String showAll(Model model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        List<String> manufacturers = VehicleManufacturer.getManufacturers();
        List<String> engineTypes = EngineType.getAllEngineTypes();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("engineTypes", engineTypes);
        return "vehicle_all";
    }

    @GetMapping("/vehicle_filter_by_manufacturer")
    public String filterByManufacturer(@RequestParam(name = "manufacturer") String manufacturer, Model model) {
        if (manufacturer.isEmpty()) {
            log.warn("Manufacturer is empty");
            return "redirect:/vehicle_all";
        }
        List<Vehicle> vehicles = vehicleService.filterByManufacturer(manufacturer);
        List<String> manufacturers = VehicleManufacturer.getManufacturers();
        List<String> engineTypes = EngineType.getAllEngineTypes();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("engineTypes", engineTypes);
        return "vehicle_all";
    }

    @GetMapping("/vehicle_filter_by_engine_type")
    public String filterByEngineType(@RequestParam(name = "engineType") String engineType, Model model) {
        if (engineType.isEmpty()) {
            log.warn("Engine type is empty");
            return "redirect:/vehicle_all";
        }
        List<Vehicle> vehicles = vehicleService.filterByEngineType(engineType);
        List<String> manufacturers = VehicleManufacturer.getManufacturers();
        List<String> engineTypes = EngineType.getAllEngineTypes();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("engineTypes", engineTypes);
        return "vehicle_all";
    }

    @GetMapping("/vehicle_add")
    public String addVehicle() {
        return "vehicle_add";
    }

    @PostMapping("/vehicle_add")
    public String addVehiclePost(@ModelAttribute Vehicle vehicle) {
        vehicleService.add(vehicle);
        log.info("Vehicle {} added", vehicle);
        return "redirect:/vehicle_all";
    }

    @GetMapping("vehicle_edit")
    public String editVehicle(@RequestParam(name = "vehicleId") int vehicleId, Model model) {
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);
        model.addAttribute("vehicle", vehicle);
        return "vehicle_edit";
    }

    @PostMapping("vehicle_edit")
    public String editVehiclePost(@ModelAttribute Vehicle vehicle) {
        vehicleService.update(vehicle);
        log.info("Vehicle {} updated", vehicle);
        return "redirect:/vehicle_all";
    }

    @PostMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam(name = "vehicleId") int vehicleId) {
        vehicleService.delete(vehicleId);
        log.info("Vehicle with id {} deleted", vehicleId);
        return "redirect:/vehicle_all";
    }
}
