package the.husky.dao.connector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
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
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private BasicDataSource basicDataSource;

    public DataSourceConnector(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
    }

    public Connection getConnection() {
        if (basicDataSource == null) {
            initializeDataSource();
        }
        try {
            return basicDataSource.getConnection();
        } catch (Exception e) {
            log.error("Error creating connection pool.", e);
            throw new DataAccessException("Connections lost, please try again later.", e);
        }
    }

    private void initializeDataSource() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(jdbcUser);
        basicDataSource.setPassword(jdbcPassword);
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
