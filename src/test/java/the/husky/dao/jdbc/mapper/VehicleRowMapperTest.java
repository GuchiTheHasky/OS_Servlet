package the.husky.dao.jdbc.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleRowMapperTest {

    private final VehicleRowMapper MAPPER = new VehicleRowMapper();

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getInt("vehicle_id")).thenReturn(1);
        Mockito.when(resultSet.getString("manufacture")).thenReturn("Toyota");
        Mockito.when(resultSet.getString("enginetype")).thenReturn("Gasoline");
        Mockito.when(resultSet.getString("model")).thenReturn("Camry");
        Mockito.when(resultSet.getDouble("price")).thenReturn(25000.0);
        Mockito.when(resultSet.getInt("age")).thenReturn(3);
        Mockito.when(resultSet.getInt("weight")).thenReturn(1500);

        Vehicle vehicle = MAPPER.mapRow(resultSet);

        assertEquals(1, vehicle.getVehicleId());
        assertEquals(VehicleManufacturer.TOYOTA, vehicle.getManufacture());
        assertEquals(EngineType.GASOLINE, vehicle.getEngineType());
        assertEquals("Camry", vehicle.getModel());
        assertEquals(25000.0, vehicle.getPrice());
        assertEquals(3, vehicle.getAge());
        assertEquals(1500, vehicle.getWeight());
    }
}
