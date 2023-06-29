package the.husky.entity.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EngineType {
    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric");

    private String type;

    public static List<String> getAllEngineTypes() {
        List<String> engineTypes = new ArrayList<>();
        for (EngineType engineType : EngineType.values()) {
            engineTypes.add(engineType.getType());
        }
        return engineTypes;
    }

    public static EngineType getEngineType(String type) {
        for (EngineType engineType : values()) {
            if (engineType.type.equalsIgnoreCase(type)) {
                return engineType;
            }
        }
        throw new IllegalArgumentException("Invalid engine type.");
    }
}

