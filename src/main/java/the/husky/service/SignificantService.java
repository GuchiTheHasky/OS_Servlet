package the.husky.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import the.husky.entity.User;
import the.husky.entity.Vehicle;

import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class SignificantService {
    private final UserService userService;
    private final VehicleService vehicleService;

    public List<User> findAllUsers() {
        return userService.findAll();
    }
    public User findUserById(int id) {
        return userService.findUserById(id);
    }

    public void saveUser(User user) {
        userService.add(user);
    }

    public void updateUser(User user) {
        userService.update(user);
    }

    public void deleteUser(int id) {
        userService.delete(id);
    }

    public List<Vehicle> findAllVehicles() {
        return vehicleService.findAll();
    }

    public List<Vehicle> filterByManufacturer(String manufacturer) {
        return vehicleService.filterByManufacturer(manufacturer);
    }

    public List<Vehicle> filterByEngineType(String engineType) {
        return vehicleService.filterByEngineType(engineType);
    }

    public void saveVehicle(Vehicle vehicle) {
        vehicleService.add(vehicle);
    }

    public void deleteVehicle(int id) {
        vehicleService.delete(id);
    }

    public Vehicle findVehicleById(int id) {
        return vehicleService.findVehicleById(id);

    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleService.update(vehicle);
    }

}
