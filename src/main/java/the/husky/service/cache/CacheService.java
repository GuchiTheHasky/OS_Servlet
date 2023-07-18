package the.husky.service.cache;

import lombok.Data;
import the.husky.entity.user.User;
import the.husky.entity.vehicle.Vehicle;
import the.husky.security.entity.Credentials;
import the.husky.service.entity.UserService;
import the.husky.service.entity.VehicleService;

import java.util.List;

@Data
public class CacheService {
    private UserService userService;
    private VehicleService vehicleService;
    private List<User> usersCache;
    private List<Vehicle> vehiclesCache;

    public CacheService(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.usersCache = userService.findAll();
        this.vehiclesCache = vehicleService.findAll();
    }

    public User getUserFromCredentials(Credentials credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        for (User user : usersCache) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        usersCache.add(user);
        userService.add(user);
    }

    public void deleteUser(int id) {
        User user = userService.getUserById(id);
        userService.delete(id);
        usersCache.remove(user);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleService.add(vehicle);
        vehiclesCache.add(vehicle);
    }

    public void deleteVehicle(int id) {
        Vehicle vehicle = vehicleService.getById(id);
        vehicleService.delete(id);
        vehiclesCache.remove(vehicle);
    }

    public void updateUser(User user) {
        userService.update(user);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleService.update(vehicle);
    }

    public List<User> getUsers() {
        return userService.findAll();
    }

    public List<Vehicle> getVehicles() {
        return vehicleService.findAll();
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public Vehicle getVehicleById(int id) {
        return vehicleService.getById(id);
    }
}
