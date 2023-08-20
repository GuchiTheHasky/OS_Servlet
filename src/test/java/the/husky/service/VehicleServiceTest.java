package the.husky.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import the.husky.entity.Vehicle;
import the.husky.entity.enums.EngineType;
import the.husky.entity.enums.VehicleManufacturer;
import the.husky.repository.VehicleDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Unit Tests for VehicleService")
class VehicleServiceTest {
    @Autowired
    private VehicleService vehicleService;
    @MockBean
    private VehicleDao vehicleDao;

    private Vehicle bmw;
    private Vehicle subaru;
    private Vehicle gm;

    @BeforeEach
    void setUp() {
        bmw = setBMW();
        subaru = setSubaru();
        gm = setGM();

        List<Vehicle> vehicles = new java.util.ArrayList<>(List.of(bmw, subaru, gm));
        when(vehicleDao.findAll()).thenReturn(vehicles);

        filterByManufacturerMock();
        filterByEngineTypeMock();
        findByIdMock();

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Vehicle vehicle = (Vehicle) args[0];
            vehicles.add(vehicle);
            return vehicle;
        }).when(vehicleDao).save(any(Vehicle.class));

        doAnswer(invocation -> {
            int deletedId = invocation.getArgument(0);
            vehicles.removeIf(vehicle -> vehicle.getVehicleId() == deletedId);
            return null;
        }).when(vehicleDao).delete(anyInt());
    }

    @DisplayName("Find all vehicles, test.")
    @Test
    void testShouldRetrieveAllVehiclesFromDatabase() {
        List<Vehicle> vehicles = vehicleService.findAll();

        int expectedSize = 3;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        assertFalse(vehicles.isEmpty());
        assertEquals(expectedSize, vehicles.size());

        assertEquals(expectedFirstVehicle, vehicles.get(0));
        assertEquals(expectedSecondVehicle, vehicles.get(1));
        assertEquals(expectedThirdVehicle, vehicles.get(2));

        assertSame(expectedFirstVehicle, vehicles.get(0));
        assertSame(expectedSecondVehicle, vehicles.get(1));
        assertSame(expectedThirdVehicle, vehicles.get(2));
    }

    @DisplayName("Filter by manufacturer, test.")
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
        List<Vehicle> vehiclesSUBARU = vehicleService.filterByManufacturer(secondManufacturer);
        List<Vehicle> vehiclesGM = vehicleService.filterByManufacturer(thirdManufacturer);

        assertFalse(vehiclesBMW.isEmpty());
        assertEquals(expectedSize, vehiclesBMW.size());

        assertFalse(vehiclesSUBARU.isEmpty());
        assertEquals(expectedSize, vehiclesSUBARU.size());

        assertFalse(vehiclesGM.isEmpty());
        assertEquals(expectedSize, vehiclesGM.size());

        assertEquals(expectedFirstVehicle, vehiclesBMW.get(0));
        assertEquals(expectedSecondVehicle, vehiclesSUBARU.get(0));
        assertEquals(expectedThirdVehicle, vehiclesGM.get(0));

        assertSame(expectedFirstVehicle, vehiclesBMW.get(0));
        assertSame(expectedSecondVehicle, vehiclesSUBARU.get(0));
        assertSame(expectedThirdVehicle, vehiclesGM.get(0));
    }

    @DisplayName("Filter by engine type, test.")
    @Test
    void testFilteringVehiclesByEngineTypeShouldReturnAListOfVehiclesWithTheSpecifiedEngineType() {
        String firstEngineType = "DIESEL";
        String secondEngineType = "GASOLINE";
        String thirdEngineType = "ELECTRIC";

        int expectedSize = 1;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        List<Vehicle> vehiclesBMW = vehicleService.filterByEngineType(firstEngineType);
        List<Vehicle> vehiclesSUBARU = vehicleService.filterByEngineType(secondEngineType);
        List<Vehicle> vehiclesGM = vehicleService.filterByEngineType(thirdEngineType);

        assertFalse(vehiclesBMW.isEmpty());
        assertEquals(expectedSize, vehiclesBMW.size());

        assertFalse(vehiclesSUBARU.isEmpty());
        assertEquals(expectedSize, vehiclesSUBARU.size());

        assertFalse(vehiclesGM.isEmpty());
        assertEquals(expectedSize, vehiclesGM.size());

        assertEquals(expectedFirstVehicle, vehiclesBMW.get(0));
        assertEquals(expectedSecondVehicle, vehiclesSUBARU.get(0));
        assertEquals(expectedThirdVehicle, vehiclesGM.get(0));

        assertSame(expectedFirstVehicle, vehiclesBMW.get(0));
        assertSame(expectedSecondVehicle, vehiclesSUBARU.get(0));
        assertSame(expectedThirdVehicle, vehiclesGM.get(0));
    }

    @DisplayName("Add new vehicle, test.")
    @Test
    void testAddingANewVehicleShouldUpdateTheListOfVehiclesWithTheNewEntity() {
        Vehicle kia = Vehicle.builder()
                .vehicleId(4)
                .manufacture(VehicleManufacturer.KIA)
                .engineType(EngineType.HYBRID)
                .model("Optima")
                .price(11000)
                .age(14)
                .weight(1700)
                .build();
        int expectedSize = 4;

        vehicleService.add(kia);

        List<Vehicle> vehicles = vehicleService.findAll();
        assertTrue(vehicles.contains(kia));
        assertEquals(expectedSize, vehicles.size());
    }

    @DisplayName("Delete vehicle by ID, test.")
    @Test
    void testDeletingAVehicleShouldRemoveTheCorrespondingEntityFromTheListOfVehicles() {
        int vehicleIdToDelete = 2;

        vehicleService.delete(vehicleIdToDelete);

        List<Vehicle> vehicles = vehicleService.findAll();
        int expectedSize = 2;
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = gm;

        assertEquals(expectedSize, vehicles.size());
        assertEquals(expectedFirstVehicle, vehicles.get(0));
        assertEquals(expectedSecondVehicle, vehicles.get(1));
    }

    @DisplayName("Find vehicle by ID, test.")
    @Test
    void testFindingAVehicleByIDShouldReturnTheCorrespondingVehicleFromTheList() {
        Vehicle expectedFirstVehicle = bmw;
        Vehicle expectedSecondVehicle = subaru;
        Vehicle expectedThirdVehicle = gm;

        Vehicle actualFirstVehicle = vehicleService.findVehicleById(1);
        Vehicle actualSecondVehicle = vehicleService.findVehicleById(2);
        Vehicle actualThirdVehicle = vehicleService.findVehicleById(3);

        assertEquals(expectedFirstVehicle, actualFirstVehicle);
        assertEquals(expectedSecondVehicle, actualSecondVehicle);
        assertEquals(expectedThirdVehicle, actualThirdVehicle);

        assertSame(expectedFirstVehicle, actualFirstVehicle);
        assertSame(expectedSecondVehicle, actualSecondVehicle);
        assertSame(expectedThirdVehicle, actualThirdVehicle);
    }

    private void filterByEngineTypeMock() {
        when(vehicleDao.filterByEngineType("DIESEL")).thenReturn(List.of(bmw));
        when(vehicleDao.filterByEngineType("GASOLINE")).thenReturn(List.of(subaru));
        when(vehicleDao.filterByEngineType("ELECTRIC")).thenReturn(List.of(gm));
    }

    private void filterByManufacturerMock() {
        when(vehicleDao.filterByManufacturer("BMW")).thenReturn(List.of(bmw));
        when(vehicleDao.filterByManufacturer("Subaru")).thenReturn(List.of(subaru));
        when(vehicleDao.filterByManufacturer("GM")).thenReturn(List.of(gm));
    }

    private void findByIdMock() {
        when(vehicleDao.findById(1)).thenReturn(java.util.Optional.ofNullable(bmw));
        when(vehicleDao.findById(2)).thenReturn(java.util.Optional.ofNullable(subaru));
        when(vehicleDao.findById(3)).thenReturn(java.util.Optional.ofNullable(gm));
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