package the.husky.entity;

import lombok.*;
import the.husky.entity.enums.EngineType;
import the.husky.entity.enums.VehicleManufacturer;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private int vehicleId;
    private VehicleManufacturer manufacture;
    private EngineType engineType;
    private String model;
    private double price;
    private int age;
    private int weight;

    public Vehicle(int vehicleId, String manufactureId, String engineType, String model, double price, int age, int weight) {
        this.vehicleId = vehicleId;
        this.manufacture = VehicleManufacturer.getByManufacture(manufactureId);
        this.engineType = EngineType.getEngineType(engineType);
        this.model = model;
        this.price = price;
        this.age = age;
        this.weight = weight;
    }
}
