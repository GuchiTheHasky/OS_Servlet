package the.husky.entity.vehicle;

import lombok.*;

import java.util.Optional;

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

    public Optional<Integer> getVehicleId() {
        return Optional.of(vehicleId);
    }

    public VehicleManufacturer getManufacture() {
        return manufacture;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public Optional<String> getModel() {
        return Optional.ofNullable(model);
    }

    public Optional<Double> getPrice() {
        return Optional.of(price);
    }

    public Optional<Integer> getAge() {
        return Optional.of(age);
    }

    public Optional<Integer> getWeight() {
        return Optional.of(weight);
    }
}
