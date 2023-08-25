package the.husky.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import the.husky.exception.InvalidTypeException;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum EngineType {
    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric");

    private final String type;

    public static List<String> getAllEngineTypes() {
        List<String> engineTypes = new ArrayList<>();
        for (EngineType engineType : EngineType.values()) {
            engineTypes.add(engineType.getType());
        }
        return engineTypes;
    }

    public static EngineType getEngineType(String type) {
        for (EngineType engineType : values()) {
            if (engineType.getType().equalsIgnoreCase(type)) {
                return engineType;
            }
        }
        throw new InvalidTypeException("Invalid engine type.");
    }
}

