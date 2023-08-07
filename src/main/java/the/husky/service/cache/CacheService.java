package the.husky.service.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import the.husky.entity.user.User;
import the.husky.entity.vehicle.Vehicle;
import the.husky.service.entity.UserService;
import the.husky.service.entity.VehicleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class CacheService {
    private UserService userService;
    private VehicleService vehicleService;
    private List<User> usersCache;
    private List<Vehicle> vehiclesCache;

    public CacheService(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        initCache();
    }

    private void initCache() {
        this.usersCache = userService.findAll();
        this.vehiclesCache = vehicleService.findAll();
    }

    public List<User> getUsersCache() {
        if (usersCache == null) {
            usersCache = Collections.synchronizedList(new ArrayList<>());
        }
        return usersCache;
    }

    public List<Vehicle> getVehiclesCache() {
        if (vehiclesCache == null) {
            vehiclesCache = Collections.synchronizedList(new ArrayList<>());
        }
        return vehiclesCache;
    }

    public void addUser(User user) {
        if (!loginAlreadyExist(user.getLogin())) {
            if (usersCache == null) {
                usersCache = Collections.synchronizedList(new ArrayList<>());
            }
            usersCache.add(user);
            userService.add(user);
        }
    }

    public void deleteUser(int id) {
        User user = userService.getUserById(id);
        userService.delete(id);
        usersCache.remove(user);
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehiclesCache == null) {
            vehiclesCache = Collections.synchronizedList(new ArrayList<>());
        }
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

    public boolean loginAlreadyExist(String login) {
        if (!(usersCache == null)) {
            for (User cachedUser : usersCache) {
                if (cachedUser.getLogin().equals(login)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleService.update(vehicle);
    }

    public List<User> getUsers() {
        usersCache = userService.findAll();
        return usersCache;
    }

    public List<Vehicle> getVehicles() {
        vehiclesCache = vehicleService.findAll();
        return vehiclesCache;
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public Vehicle getVehicleById(int id) {
        return vehicleService.getById(id);
    }
}
