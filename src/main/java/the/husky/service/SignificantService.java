package the.husky.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.husky.entity.User;
import the.husky.entity.Vehicle;

import java.util.List;

@Service
@Getter
@NoArgsConstructor
public class SignificantService {
    private UserService userService;
    private VehicleService vehicleService;

    @Autowired
    public SignificantService(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

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

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleService.add(vehicle);
    }

    public void delete(int id) {
        vehicleService.delete(id);
    }

    public Vehicle findVehicleById(int id) {
        return vehicleService.findVehicleById(id);

    }

    public void update(Vehicle vehicle) {
        vehicleService.update(vehicle);
    }

}
