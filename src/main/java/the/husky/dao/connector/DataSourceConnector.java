package the.husky.dao.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import the.husky.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceConnector {
    private static final String DB_PROPERTIES = "db.properties";     // TODO: It's used for flyway migration.
    private static final String CONNECTION_TEST_QUERY = "SELECT 1";
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private HikariDataSource dataSource;

    public Connection getConnection() {
        if (dataSource == null) {
            initializeDataSource();
        }
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            log.error("Error creating connection pool.", e);
            throw new DataAccessException("Connections lost, please try again later.", e);
        }
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUser);
        config.setPassword(jdbcPassword);
        config.setConnectionTestQuery(CONNECTION_TEST_QUERY);

        dataSource = new HikariDataSource(config);
    }

    // TODO: It's used for flyway migration.
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DataSourceConnector.class.getClassLoader().getResourceAsStream(DB_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error loading database properties.", e);
            throw new DataAccessException("Connections lost, please try again later.", e);
        }
        return properties;
    }
}
