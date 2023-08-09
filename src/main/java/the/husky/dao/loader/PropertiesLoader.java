package the.husky.dao.loader;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import the.husky.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class PropertiesLoader {
    private static final String DB_PROPERTIES = "db.properties";

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(DB_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error loading database properties.", e);
            throw new DataAccessException("Connections lost, please try again later.", e);
        }
        return properties;
    }
}
