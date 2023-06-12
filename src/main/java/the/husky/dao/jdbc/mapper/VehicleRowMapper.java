package the.husky.dao.jdbc.mapper;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.entity.vehicle.VehicleManufacturer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRowMapper {

    public Vehicle mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("vehicle_id");
        String manufacture = resultSet.getString("manufacture");
        String engine = resultSet.getString("enginetype");
        System.out.println("RowMapper: " + engine);
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