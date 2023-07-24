package the.husky.service.entity;

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
        return vehicleDao.findById(id).orElseThrow();
    }

    public void update(Vehicle vehicle) {
        vehicleDao.update(vehicle);
    }
}