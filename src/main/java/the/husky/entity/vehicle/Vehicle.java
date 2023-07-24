package the.husky.entity.vehicle;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Vehicle {
    @NonNull
    private int vehicleId;
    private VehicleManufacturer manufacture;
    private EngineType engineType;
    private String model;
    private double price;
    private int age;
    private int weight;
}
