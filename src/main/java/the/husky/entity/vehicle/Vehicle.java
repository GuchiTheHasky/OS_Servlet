package the.husky.entity.vehicle;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vehicle {
    private int vehicleId;
    private VehicleManufacturer manufacture;
    private EngineType engineType;
    private String model;
    private double price;
    private int age;
    private int weight;

    public Vehicle(String manufactureId, String engineType, String model, double price, int age, int weight) {
        this.manufacture = VehicleManufacturer.getByManufacture(manufactureId);
        this.engineType = EngineType.getEngineType(engineType);
        this.model = model;
        this.price = price;
        this.age = age;
        this.weight = weight;
    }
}
