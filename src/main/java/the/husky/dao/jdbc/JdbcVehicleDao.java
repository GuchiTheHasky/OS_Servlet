package the.husky.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import the.husky.dao.VehicleDao;
import the.husky.dao.connector.DataSourceConnector;
import the.husky.dao.jdbc.mapper.VehicleRowMapper;
import the.husky.entity.vehicle.Vehicle;
import the.husky.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcVehicleDao implements VehicleDao {
    private static final VehicleRowMapper VEHICLE_ROW_MAPPER = new VehicleRowMapper();
    private static final String SELECT_ALL = "SELECT vehicle_id, manufacture, enginetype, model, price, age, weight " +
            "FROM \"vehicle\"";
    private static final String INSERT = "INSERT " +
            "INTO \"vehicle\" (manufacture, enginetype, model, price, age, weight) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM \"vehicle\" WHERE vehicle_id = ?";
    private static final String UPDATE = "UPDATE \"vehicle\" " +
            "SET manufacture=?, enginetype=?, model=?, price=?, age=?, weight=? WHERE vehicle_id=?";
    private static final String GET_BY_ID = "SELECT * FROM \"vehicle\" WHERE vehicle_id = ?";

    @Override
    public List<Vehicle> findAll() {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            List<Vehicle> vehicles = new ArrayList<>();
            while (resultSet.next()) {
                Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                vehicles.add(vehicle);
            }
            return vehicles;
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: findAll;", e);
            throw new DataAccessException("Error retrieving vehicles. Please try again later.", e);
        }
    }

    @Override
    public void save(Vehicle vehicle) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, vehicle.getManufacture().getManufacture());
            statement.setString(2, vehicle.getEngineType().getType());
            statement.setString(3, getStringValue(vehicle.getModel()));
            statement.setDouble(4, getDoubleValue(vehicle.getPrice()));
            statement.setInt(5, getIntValue(vehicle.getAge()));
            statement.setInt(6, getIntValue(vehicle.getWeight()));

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: save;", e);
            throw new DataAccessException("Error saving vehicle. Please try again later.", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: delete;", e);
            throw new DataAccessException(
                    String.format("Error deleting vehicle with \"Id\": %d. Please try again later.", id), e);
        }
    }

    @Override
    public void update(Vehicle updatedVehicle) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, updatedVehicle.getManufacture().getManufacture());
            statement.setString(2, updatedVehicle.getEngineType().getType());
            statement.setString(3, getStringValue(updatedVehicle.getModel()));
            statement.setDouble(4, getDoubleValue(updatedVehicle.getPrice()));
            statement.setInt(5, getIntValue(updatedVehicle.getAge()));
            statement.setInt(6, getIntValue(updatedVehicle.getWeight()));
            statement.setInt(7, getIntValue(updatedVehicle.getVehicleId()));

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: update;", e);
            throw new DataAccessException("Error updating vehicle. Please try again later.", e);
        }
    }

    @Override
    public Optional<Vehicle> findById(int id) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(VEHICLE_ROW_MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: findById;", e);
            throw new DataAccessException(
                    String.format("Error finding vehicle with \"Id\": %d. Please try again later.", id), e);
        }
        return Optional.empty();
    }

    private int getIntValue(Optional<Integer> value) {
        return value.orElseThrow(() -> {
            log.error("Value is not present");
            return new DataAccessException("Value is not present");
        });
    }

    private Double getDoubleValue(Optional<Double> value) {
        return value.orElseThrow(() -> {
            log.error("Value is not present");
            return new DataAccessException("Value is not present");
        });
    }

    private String getStringValue(Optional<String> value) {
        return value.orElseThrow(() -> {
            log.error("Value is not present");
            return new DataAccessException("Value is not present");
        });
    }
}
