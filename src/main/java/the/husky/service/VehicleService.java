package the.husky.service;

import the.husky.dao.VehicleDao;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

public class VehicleService {
    private final VehicleDao DAO;

    public VehicleService(VehicleDao dao) {
        DAO = dao;
    }

    public List<Vehicle> getAll() {
        return DAO.getAll();
    }

    public void add(Vehicle vehicle) {
        DAO.add(vehicle);
    }

    public void delete(int id) {
        DAO.delete(id);
    }

    public Vehicle getById(int id) {
        return DAO.getById(id);
    }

    public void edit(Vehicle vehicle) {
        DAO.edit(vehicle);
    }

    public List<Vehicle> filterByManufacturer(String manufacturer) {
        return DAO.filterByManufacturer(manufacturer);
    }

    public List<Vehicle> filterByEngineType(String engineType) {
        return DAO.filterByManufacturer(engineType);
    }
}