package the.husky.service;

import lombok.RequiredArgsConstructor;
import the.husky.dao.VehicleDao;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

@RequiredArgsConstructor
public class VehicleService {
    private final VehicleDao vehicleDao;

    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    public void add(Vehicle vehicle) {
        vehicleDao.save(vehicle);
    }

    public void delete(int id) {
        vehicleDao.delete(id);
    }

    public Vehicle getById(int id) {
        return vehicleDao.findById(id);
    }

    public void edit(Vehicle vehicle) {
        vehicleDao.update(vehicle);
    }

    public List<Vehicle> filterByManufacturer(String manufacturer) {
        return vehicleDao.filterByManufacturer(manufacturer);
    }

    public List<Vehicle> filterByEngineType(String engineType) {
        return vehicleDao.filterByManufacturer(engineType);
    }
}