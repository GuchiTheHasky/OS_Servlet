package the.husky.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.husky.entity.Vehicle;
import the.husky.exception.RepositoryException;
import the.husky.repository.VehicleDao;

import java.util.List;

@Service
@NoArgsConstructor
public class VehicleService {
    private VehicleDao vehicleDao;

    @Autowired
    public VehicleService(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    public List<Vehicle> filterByManufacturer(String manufacturer) {
        return vehicleDao.filterByManufacturer(manufacturer);
    }

    public List<Vehicle> filterByEngineType(String engineType) {
        return vehicleDao.filterByEngineType(engineType);
    }

    public Vehicle add(Vehicle vehicle) {
        vehicleDao.save(vehicle);
        return vehicle;
    }

    public void delete(int id) {
        vehicleDao.delete(id);
    }

    public Vehicle findVehicleById(int id) {
        return vehicleDao.findById(id).orElseThrow(() ->
                new RepositoryException(String.format("Vehicle with id: %d not found.", id)));

    }

    public void update(Vehicle vehicle) {
        vehicleDao.update(vehicle);
    }
}