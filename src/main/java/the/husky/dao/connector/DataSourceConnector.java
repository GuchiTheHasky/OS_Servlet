package the.husky.dao.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import the.husky.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DataSourceConnector {
    private static final String DB_PROPERTIES = "db.properties";
    private static final String CONNECTION_TEST_QUERY = "SELECT 1";
    private static HikariDataSource dataSource;

    public DataSourceConnector() {
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initializeDataSource();
        }
        return dataSource.getConnection();
    }

    private static void initializeDataSource() {
        Properties properties = loadProperties();
        String jdbcUrl = properties.getProperty("db.local.url");
        String jdbcUser = properties.getProperty("db.user");
        String jdbcPassword = properties.getProperty("db.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUser);
        config.setPassword(jdbcPassword);
        config.setConnectionTestQuery(CONNECTION_TEST_QUERY);

        dataSource = new HikariDataSource(config);
    }

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
