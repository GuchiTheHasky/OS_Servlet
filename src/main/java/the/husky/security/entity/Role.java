package the.husky.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import the.husky.exception.InvalidTypeException;

@AllArgsConstructor
@Getter
public enum Role {
    USER("User"),
    ADMIN("Admin");

    private String role;


    public static Role getRole(String role) {
        for (Role value : values()) {
            if (value.role.equalsIgnoreCase(role)) {
                return value;
            }
        }
        throw new InvalidTypeException("Invalid role type.");
    }
}
