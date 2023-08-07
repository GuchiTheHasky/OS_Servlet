package the.husky.dao.jdbc;

import org.junit.jupiter.api.Test;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcVehicleDaoITest {

    private final JdbcVehicleDao DAO = new JdbcVehicleDao();

    @Test
    public void testFindAll() {
        Iterable<List<Vehicle>> vehicles = DAO.findAll();
        System.out.println(vehicles.iterator().next());
        assertFalse(vehicles.iterator().next().isEmpty());

    }

//    @Test
//    public void testFindById() {
//        I<List<Vehicle>> vehicles = DAO.findAll();
//        Vehicle expectedVehicle = vehicles.get().get(0);
//        int id = expectedVehicle.getVehicleId();
//
//        Optional<Vehicle> actualVehicle = DAO.findById(id);
//        assertEquals(expectedVehicle.getVehicleId(), actualVehicle.get().getVehicleId());
//        assertEquals(expectedVehicle.getManufacture(), actualVehicle.get().getManufacture());
//        assertEquals(expectedVehicle.getEngineType(), actualVehicle.get().getEngineType());
//        assertEquals(expectedVehicle.getModel(), actualVehicle.get().getModel());
//        assertEquals(expectedVehicle.getAge(), actualVehicle.get().getAge());
//        assertEquals(expectedVehicle.getWeight(), actualVehicle.get().getWeight());
//    }

}
