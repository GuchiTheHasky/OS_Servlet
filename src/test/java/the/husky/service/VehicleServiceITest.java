package the.husky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import the.husky.entity.Vehicle;
import the.husky.entity.enums.EngineType;
import the.husky.entity.enums.VehicleManufacturer;
import the.husky.repository.VehicleDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@DisplayName("Integration Tests for VehicleService.")
public class VehicleServiceITest {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleDao vehicleDao;

    private Vehicle bmw;
    private Vehicle subaru;
    private Vehicle gm;

    @BeforeEach
    void setUp() {
        vehicleDao.deleteAll();

        bmw = setBMW();
        subaru = setSubaru();
        gm = setGM();

        vehicleDao.save(bmw);
        vehicleDao.save(subaru);
        vehicleDao.save(gm);
    }

    @DisplayName("Find all vehicles, test.")
    @Test
    void testShouldRetrieveAllVehiclesFromDatabase() {
        List<Vehicle> vehicles = vehicleService.findAll();

        int expectedSize = 3;
        int actualSize = vehicles.size();
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        assertFalse(vehicles.isEmpty());
        assertEquals(expectedSize, actualSize);

        assertEquals(expectedFirstVehicle.getManufacture(), vehicles.get(0).getManufacture());
        assertEquals(expectedFirstVehicle.getEngineType(), vehicles.get(0).getEngineType());
        assertEquals(expectedFirstVehicle.getAge(), vehicles.get(0).getAge());
        assertEquals(expectedFirstVehicle.getModel(), vehicles.get(0).getModel());
        assertEquals(expectedFirstVehicle.getPrice(), vehicles.get(0).getPrice());
        assertEquals(expectedFirstVehicle.getWeight(), vehicles.get(0).getWeight());

        assertEquals(expectedSecondVehicle.getManufacture(), vehicles.get(1).getManufacture());
        assertEquals(expectedSecondVehicle.getEngineType(), vehicles.get(1).getEngineType());
        assertEquals(expectedSecondVehicle.getAge(), vehicles.get(1).getAge());
        assertEquals(expectedSecondVehicle.getModel(), vehicles.get(1).getModel());
        assertEquals(expectedSecondVehicle.getPrice(), vehicles.get(1).getPrice());
        assertEquals(expectedSecondVehicle.getWeight(), vehicles.get(1).getWeight());

        assertEquals(expectedThirdVehicle.getManufacture(), vehicles.get(2).getManufacture());
        assertEquals(expectedThirdVehicle.getEngineType(), vehicles.get(2).getEngineType());
        assertEquals(expectedThirdVehicle.getAge(), vehicles.get(2).getAge());
        assertEquals(expectedThirdVehicle.getModel(), vehicles.get(2).getModel());
        assertEquals(expectedThirdVehicle.getPrice(), vehicles.get(2).getPrice());
        assertEquals(expectedThirdVehicle.getWeight(), vehicles.get(2).getWeight());
    }

    @DisplayName("Filtering vehicles by manufacturer, test.")
    @Test
    void testFilteringVehiclesByManufacturerShouldReturnAListOfVehiclesWithTheSpecifiedManufacturer() {
        String firstManufacturer = "BMW";
        String secondManufacturer = "Subaru";
        String thirdManufacturer = "GM";

        int expectedSize = 1;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        List<Vehicle> vehiclesBMW = vehicleService.filterByManufacturer(firstManufacturer);
        List<Vehicle> vehiclesSubaru = vehicleService.filterByManufacturer(secondManufacturer);
        List<Vehicle> vehiclesGM = vehicleService.filterByManufacturer(thirdManufacturer);

        assertFalse(vehiclesBMW.isEmpty());
        assertEquals(expectedSize, vehiclesBMW.size());

        assertFalse(vehiclesSubaru.isEmpty());
        assertEquals(expectedSize, vehiclesSubaru.size());

        assertFalse(vehiclesGM.isEmpty());
        assertEquals(expectedSize, vehiclesGM.size());

        assertEquals(expectedFirstVehicle.getManufacture(), vehiclesBMW.get(0).getManufacture());
        assertEquals(expectedFirstVehicle.getEngineType(), vehiclesBMW.get(0).getEngineType());
        assertEquals(expectedFirstVehicle.getAge(), vehiclesBMW.get(0).getAge());
        assertEquals(expectedFirstVehicle.getModel(), vehiclesBMW.get(0).getModel());
        assertEquals(expectedFirstVehicle.getPrice(), vehiclesBMW.get(0).getPrice());
        assertEquals(expectedFirstVehicle.getWeight(), vehiclesBMW.get(0).getWeight());

        assertEquals(expectedSecondVehicle.getManufacture(), vehiclesSubaru.get(0).getManufacture());
        assertEquals(expectedSecondVehicle.getEngineType(), vehiclesSubaru.get(0).getEngineType());
        assertEquals(expectedSecondVehicle.getAge(), vehiclesSubaru.get(0).getAge());
        assertEquals(expectedSecondVehicle.getModel(), vehiclesSubaru.get(0).getModel());
        assertEquals(expectedSecondVehicle.getPrice(), vehiclesSubaru.get(0).getPrice());
        assertEquals(expectedSecondVehicle.getWeight(), vehiclesSubaru.get(0).getWeight());

        assertEquals(expectedThirdVehicle.getManufacture(), vehiclesGM.get(0).getManufacture());
        assertEquals(expectedThirdVehicle.getEngineType(), vehiclesGM.get(0).getEngineType());
        assertEquals(expectedThirdVehicle.getAge(), vehiclesGM.get(0).getAge());
        assertEquals(expectedThirdVehicle.getModel(), vehiclesGM.get(0).getModel());
        assertEquals(expectedThirdVehicle.getPrice(), vehiclesGM.get(0).getPrice());
        assertEquals(expectedThirdVehicle.getWeight(), vehiclesGM.get(0).getWeight());
    }

    @DisplayName("Filtering vehicles by engine type, test.")
    @Test
    void testFilteringVehiclesByEngineTypeShouldReturnAListOfVehiclesWithTheSpecifiedEngineType() {
        String firstEngineType = "Diesel";
        String secondEngineType = "Gasoline";
        String thirdEngineType = "Electric";

        int expectedSize = 1;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        List<Vehicle> vehiclesBMW = vehicleService.filterByEngineType(firstEngineType);
        List<Vehicle> vehiclesSubaru = vehicleService.filterByEngineType(secondEngineType);
        List<Vehicle> vehiclesGM = vehicleService.filterByEngineType(thirdEngineType);

        assertEquals(expectedSize, vehiclesBMW.size());
        assertEquals(expectedSize, vehiclesSubaru.size());
        assertEquals(expectedSize, vehiclesGM.size());

        assertEquals(expectedFirstVehicle.getManufacture(), vehiclesBMW.get(0).getManufacture());
        assertEquals(expectedFirstVehicle.getEngineType(), vehiclesBMW.get(0).getEngineType());
        assertEquals(expectedFirstVehicle.getAge(), vehiclesBMW.get(0).getAge());
        assertEquals(expectedFirstVehicle.getModel(), vehiclesBMW.get(0).getModel());
        assertEquals(expectedFirstVehicle.getPrice(), vehiclesBMW.get(0).getPrice());
        assertEquals(expectedFirstVehicle.getWeight(), vehiclesBMW.get(0).getWeight());

        assertEquals(expectedSecondVehicle.getManufacture(), vehiclesSubaru.get(0).getManufacture());
        assertEquals(expectedSecondVehicle.getEngineType(), vehiclesSubaru.get(0).getEngineType());
        assertEquals(expectedSecondVehicle.getAge(), vehiclesSubaru.get(0).getAge());
        assertEquals(expectedSecondVehicle.getModel(), vehiclesSubaru.get(0).getModel());
        assertEquals(expectedSecondVehicle.getPrice(), vehiclesSubaru.get(0).getPrice());
        assertEquals(expectedSecondVehicle.getWeight(), vehiclesSubaru.get(0).getWeight());

        assertEquals(expectedThirdVehicle.getManufacture(), vehiclesGM.get(0).getManufacture());
        assertEquals(expectedThirdVehicle.getEngineType(), vehiclesGM.get(0).getEngineType());
        assertEquals(expectedThirdVehicle.getAge(), vehiclesGM.get(0).getAge());
        assertEquals(expectedThirdVehicle.getModel(), vehiclesGM.get(0).getModel());
        assertEquals(expectedThirdVehicle.getPrice(), vehiclesGM.get(0).getPrice());
        assertEquals(expectedThirdVehicle.getWeight(), vehiclesGM.get(0).getWeight());
    }

    @DisplayName("Finding a vehicle by ID, test.")
    @Test
    void testFindingAVehicleByIDShouldReturnTheCorrespondingVehicleFromTheList() {
        List<Vehicle> vehicles = vehicleService.findAll();

        Vehicle expectedFirstVehicle = vehicles.get(0);
        Vehicle expectedSecondVehicle = vehicles.get(1);
        Vehicle expectedThirdVehicle = vehicles.get(2);

        int firstId = vehicles.get(0).getVehicleId();
        int secondId = vehicles.get(1).getVehicleId();
        int thirdId = vehicles.get(2).getVehicleId();

        Vehicle actualFirstVehicle = vehicleService.findVehicleById(firstId);
        Vehicle actualSecondVehicle = vehicleService.findVehicleById(secondId);
        Vehicle actualThirdVehicle = vehicleService.findVehicleById(thirdId);

        assertEquals(expectedFirstVehicle.getManufacture(), actualFirstVehicle.getManufacture());
        assertEquals(expectedFirstVehicle.getEngineType(), actualFirstVehicle.getEngineType());
        assertEquals(expectedFirstVehicle.getAge(), actualFirstVehicle.getAge());
        assertEquals(expectedFirstVehicle.getWeight(), actualFirstVehicle.getWeight());
        assertEquals(expectedFirstVehicle.getPrice(), actualFirstVehicle.getPrice());
        assertEquals(expectedFirstVehicle.getModel(), actualFirstVehicle.getModel());

        assertEquals(expectedSecondVehicle.getManufacture(), actualSecondVehicle.getManufacture());
        assertEquals(expectedSecondVehicle.getEngineType(), actualSecondVehicle.getEngineType());
        assertEquals(expectedSecondVehicle.getAge(), actualSecondVehicle.getAge());
        assertEquals(expectedSecondVehicle.getWeight(), actualSecondVehicle.getWeight());
        assertEquals(expectedSecondVehicle.getPrice(), actualSecondVehicle.getPrice());
        assertEquals(expectedSecondVehicle.getModel(), actualSecondVehicle.getModel());

        assertEquals(expectedThirdVehicle.getManufacture(), actualThirdVehicle.getManufacture());
        assertEquals(expectedThirdVehicle.getEngineType(), actualThirdVehicle.getEngineType());
        assertEquals(expectedThirdVehicle.getAge(), actualThirdVehicle.getAge());
        assertEquals(expectedThirdVehicle.getWeight(), actualThirdVehicle.getWeight());
        assertEquals(expectedThirdVehicle.getPrice(), actualThirdVehicle.getPrice());
        assertEquals(expectedThirdVehicle.getModel(), actualThirdVehicle.getModel());
    }

    @DisplayName("Add new vehicle, test.")
    @Test
    public void testAddingANewVehicleShouldUpdateTheListOfVehiclesWithTheNewEntity() {
        Vehicle kia = setKia();

        VehicleManufacturer expectedManufacturer = VehicleManufacturer.KIA;
        EngineType expectedEngineType = EngineType.HYBRID;
        int expectedAge = 14;
        int expectedWeight = 1700;
        double expectedPrice = 11000;

        int expectedSize = 4;

        vehicleService.add(kia);
        List<Vehicle> vehicles = vehicleService.findAll();

        VehicleManufacturer actualManufacturer = vehicles.get(3).getManufacture();
        EngineType actualEngineType = vehicles.get(3).getEngineType();
        int actualAge = vehicles.get(3).getAge();
        int actualWeight = vehicles.get(3).getWeight();
        double actualPrice = vehicles.get(3).getPrice();

        assertEquals(expectedSize, vehicles.size());
        assertEquals(expectedManufacturer, actualManufacturer);
        assertEquals(expectedEngineType, actualEngineType);
        assertEquals(expectedAge, actualAge);
        assertEquals(expectedWeight, actualWeight);
        assertEquals(expectedPrice, actualPrice);
    }

    @DisplayName("Delete vehicle, test.")
    @Test
    public void testDeletingAVehicleShouldRemoveTheCorrespondingEntityFromTheListOfVehicles() {
        List<Vehicle> vehicles = vehicleService.findAll();
        int vehicleIdToDelete = vehicles.get(1).getVehicleId();

        int expectedSize = 2;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = gm;

        vehicleService.delete(vehicleIdToDelete);
        vehicles = vehicleService.findAll();

        Vehicle actualFirstVehicle = vehicles.get(0);
        Vehicle actualSecondVehicle = vehicles.get(1);

        assertEquals(expectedSize, vehicles.size());

        assertEquals(expectedFirstVehicle.getManufacture(), actualFirstVehicle.getManufacture());
        assertEquals(expectedFirstVehicle.getEngineType(), actualFirstVehicle.getEngineType());
        assertEquals(expectedFirstVehicle.getAge(), actualFirstVehicle.getAge());
        assertEquals(expectedFirstVehicle.getWeight(), actualFirstVehicle.getWeight());
        assertEquals(expectedFirstVehicle.getPrice(), actualFirstVehicle.getPrice());
        assertEquals(expectedFirstVehicle.getModel(), actualFirstVehicle.getModel());

        assertEquals(expectedSecondVehicle.getManufacture(), actualSecondVehicle.getManufacture());
        assertEquals(expectedSecondVehicle.getEngineType(), actualSecondVehicle.getEngineType());
        assertEquals(expectedSecondVehicle.getAge(), actualSecondVehicle.getAge());
        assertEquals(expectedSecondVehicle.getWeight(), actualSecondVehicle.getWeight());
        assertEquals(expectedSecondVehicle.getPrice(), actualSecondVehicle.getPrice());
        assertEquals(expectedSecondVehicle.getModel(), actualSecondVehicle.getModel());
    }

    private Vehicle setKia() {
        return Vehicle.builder()
                .vehicleId(4)
                .manufacture(VehicleManufacturer.KIA)
                .engineType(EngineType.HYBRID)
                .model("Optima")
                .price(11000)
                .age(14)
                .weight(1700)
                .build();
    }

    private Vehicle setBMW() {
        return Vehicle.builder()
                .vehicleId(1)
                .manufacture(VehicleManufacturer.BMW)
                .engineType(EngineType.DIESEL)
                .model("X5")
                .price(10000)
                .age(5)
                .weight(2000)
                .build();
    }

    private Vehicle setSubaru() {
        return Vehicle.builder()
                .vehicleId(2)
                .manufacture(VehicleManufacturer.SUBARU)
                .engineType(EngineType.GASOLINE)
                .model("Forester")
                .price(15000)
                .age(3)
                .weight(1800)
                .build();
    }

    private Vehicle setGM() {
        return Vehicle.builder()
                .vehicleId(3)
                .manufacture(VehicleManufacturer.GM)
                .engineType(EngineType.ELECTRIC)
                .model("Bolt")
                .price(20000)
                .age(2)
                .weight(1500)
                .build();
    }
}
