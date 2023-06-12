package the.husky.entity.vehicle;

import lombok.*;

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
}
