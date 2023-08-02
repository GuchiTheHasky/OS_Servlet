package the.husky.service.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.dao.VehicleDao;
import the.husky.entity.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class VehicleService {
    private VehicleDao vehicleDao;

    public List<Vehicle> findAll() {
        return vehicleDao.findAll().isPresent() ? vehicleDao.findAll().get() : new ArrayList<>();
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