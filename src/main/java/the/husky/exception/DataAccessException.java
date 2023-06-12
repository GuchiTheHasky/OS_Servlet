package the.husky.exception;

import java.sql.SQLException;

public class DataAccessException extends SQLException {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
