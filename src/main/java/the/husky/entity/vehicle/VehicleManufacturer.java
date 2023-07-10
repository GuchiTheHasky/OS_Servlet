package the.husky.entity.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import the.husky.exception.InvalidTypeException;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum VehicleManufacturer {
    SUBARU("SUBARU CORPORATION", "Subaru"),
    KIA("KIA MOTORS CORPORATION", "Kia"),
    VOLVO("VOLVO CARS", "Volvo"),
    BMW("BMW GROUP", "BMW"),
    HONDA("HONDA MOTOR COMPANY", "Honda"),
    FORD("FORD MOTOR COMPANY", "Ford"),
    GM("GENERAL MOTORS", "GM"),
    MAZDA("MAZDA MOTOR CORPORATION", "Mazda"),
    TOYOTA("TOYOTA", "Toyota"),
    MITSUBISHI("MITSUBISHI MOTORS CORPORATION", "Mitsubishi");

    private String manufactureId;
    private String manufacture;

    public static List<String> getManufacturers() {
        List<String> manufacturers = new ArrayList<>();
        for (VehicleManufacturer manufacturer : values()) {
            manufacturers.add(manufacturer.getManufacture());
        }
        return manufacturers;
    }

    public static VehicleManufacturer getByManufacture(String manufacture) {
        for (VehicleManufacturer manufacturer : values()) {
            if (manufacturer.getManufacture().equalsIgnoreCase(manufacture)) {
                return manufacturer;
            }
        }
        throw new InvalidTypeException("Invalid vehicle manufacturer: " + manufacture);
    }
}

