package the.husky.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import the.husky.entity.enums.EngineType;
import the.husky.entity.Vehicle;
import the.husky.entity.enums.VehicleManufacturer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRowMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle mapRow(ResultSet resultSet, int n) throws SQLException {
        int id = resultSet.getInt("vehicle_id");
        String manufacture = resultSet.getString("manufacture");
        String engine = resultSet.getString("enginetype");
        String model = resultSet.getString("model");
        double price = resultSet.getDouble("price");
        int age = resultSet.getInt("age");
        int weight = resultSet.getInt("weight");

        return Vehicle.builder()
                .vehicleId(id)
                .manufacture(VehicleManufacturer.getByManufacture(manufacture))
                .engineType(EngineType.getEngineType(engine))
                .model(model)
                .price(price)
                .age(age)
                .weight(weight)
                .build();
    }
}