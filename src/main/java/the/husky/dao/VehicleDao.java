package the.husky.dao;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> getAll();

    void add(Vehicle vehicle);

    void delete(int id);

    void edit(Vehicle vehicle);

    Vehicle getById(int id);

    List<Vehicle> filterByManufacturer(String manufacturer);

    List<Vehicle> filterByEngineType(EngineType type);
}
