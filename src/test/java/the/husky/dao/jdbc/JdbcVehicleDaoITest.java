package the.husky.dao.jdbc;

import org.junit.jupiter.api.Test;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcVehicleDaoITest {

    private final JdbcVehicleDao DAO = new JdbcVehicleDao();

    @Test
    public void testFindAll() {
        List<Vehicle> vehicles = DAO.findAll();

        assertFalse(vehicles.isEmpty());
        for (Vehicle vehicle : vehicles) {
            assertNotEquals(0, vehicle.getVehicleId());
        }
    }

    @Test
    public void testFindById() {
        List<Vehicle> vehicles = DAO.findAll();
        Vehicle expectedVehicle = vehicles.get(0);
        int id = expectedVehicle.getVehicleId();

        Vehicle actualVehicle = DAO.findById(id);
        assertEquals(expectedVehicle.getVehicleId(), actualVehicle.getVehicleId());
        assertEquals(expectedVehicle.getManufacture(), actualVehicle.getManufacture());
        assertEquals(expectedVehicle.getEngineType(), actualVehicle.getEngineType());
        assertEquals(expectedVehicle.getModel(), actualVehicle.getModel());
        assertEquals(expectedVehicle.getAge(), actualVehicle.getAge());
        assertEquals(expectedVehicle.getWeight(), actualVehicle.getWeight());
    }

}
