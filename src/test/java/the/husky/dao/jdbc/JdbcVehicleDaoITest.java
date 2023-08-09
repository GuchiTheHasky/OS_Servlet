package the.husky.dao.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcVehicleDaoITest {

    private static final JdbcVehicleDao VEHICLE_DAO = new JdbcVehicleDao();

    @BeforeAll
    static void init () {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/OS");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        VEHICLE_DAO.setDataSource(dataSource);
    }

    @Test
    public void testFindAll() {
        Iterable<List<Vehicle>> vehicles = VEHICLE_DAO.findAll();

        assertNotNull(vehicles);
        boolean isEmptyVehiclesList = VEHICLE_DAO.findAll().iterator().next().isEmpty();
        assertTrue(isEmptyVehiclesList);
    }

    @Test
    @DisplayName("Test, add new vehicle & delete.")
    public void testAddNewVehicleAndDelete() {
        String manufactureId = "SUBARU";
        String engineType = "GASOLINE";
        String model = "Forester";
        double price = 16000;
        int age = 8;
        int weight = 2000;
        Vehicle vehicle = new Vehicle(manufactureId, engineType, model, price, age, weight);

        int expectedVehiclesCount = 0;
        int actualVehiclesCount = VEHICLE_DAO.findAll().iterator().next().size();
        assertEquals(expectedVehiclesCount, actualVehiclesCount);

        VEHICLE_DAO.save(vehicle);

        expectedVehiclesCount = 1;
        actualVehiclesCount = VEHICLE_DAO.findAll().iterator().next().size();
        assertEquals(expectedVehiclesCount, actualVehiclesCount);

        Iterable<List<Vehicle>> vehicles = VEHICLE_DAO.findAll();
        Vehicle actualVehicle = vehicles.iterator().next().get(0);
        VEHICLE_DAO.delete(actualVehicle.getVehicleId());

        expectedVehiclesCount = 0;
        actualVehiclesCount = VEHICLE_DAO.findAll().iterator().next().size();
        assertEquals(expectedVehiclesCount, actualVehiclesCount);
    }




//    @Test
//    @DisplayName("Test, save new user & delete.")
//    public void testSaveNewUserAndDelete() {
//        String testLogin = "new-test-user";
//        String testPassword = "new-test-user";
//        User user = new User(testLogin, testPassword, LocalDateTime.now());
//
//        boolean isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
//        assertTrue(isEmptyUsersList);
//
//        USER_DAO.save(user);
//
//        isEmptyUsersList = USER_DAO.findAll().iterator().next().isEmpty();
//        assertFalse(isEmptyUsersList);
//
//        int expectedUsersCount = 1;
//        int actualUsersCount = USER_DAO.findAll().iterator().next().size();
//        assertEquals(expectedUsersCount, actualUsersCount);
//
//        User actualUser = USER_DAO.findByLogin(user.getLogin()).get();
//        USER_DAO.delete(actualUser.getUserId());
//    }




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
