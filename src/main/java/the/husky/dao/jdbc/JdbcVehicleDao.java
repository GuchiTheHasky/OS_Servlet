package the.husky.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import the.husky.dao.VehicleDao;
import the.husky.dao.jdbc.mapper.VehicleRowMapper;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;
import the.husky.exception.DataAccessException;

import java.sql.*;
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
    private static final String SELECT_BY_MANUFACTURE = "SELECT * FROM \"vehicle\" WHERE manufacture = ?";
    private static final String SELECT_BY_ENGINE_TYPE = "SELECT * FROM \"vehicle\" WHERE enginetype = ?";

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
            statement.setString(3, vehicle.getModel());
            statement.setDouble(4, vehicle.getPrice());
            statement.setInt(5, vehicle.getAge());
            statement.setInt(6, vehicle.getWeight());

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
            statement.setString(3, updatedVehicle.getModel());
            statement.setDouble(4, updatedVehicle.getPrice());
            statement.setInt(5, updatedVehicle.getAge());
            statement.setInt(6, updatedVehicle.getWeight());
            statement.setInt(7, updatedVehicle.getVehicleId());

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

    @Override
    public List<Vehicle> filterByManufacturer(String manufacturer) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_MANUFACTURE)) {
            List<Vehicle> filteredVehicles = new ArrayList<>();
            statement.setString(1, manufacturer);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                    filteredVehicles.add(vehicle);
                }
            }
            return filteredVehicles;
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: filterByManufacturer;", e);
            throw new DataAccessException("Error filtering vehicles by manufacturer. Please try again later.", e);
        }
    }

    @Override
    public List<Vehicle> filterByEngineType(EngineType type) {
        try (Connection connection = DataSourceConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ENGINE_TYPE)) {
            statement.setString(1, type.getType());
            List<Vehicle> filteredVehicles = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                    filteredVehicles.add(vehicle);
                }
            }
            return filteredVehicles;
        } catch (SQLException e) {
            log.error("SQL or data connection refused; JdbcVehicleDao.class, method: filterByEngineType;", e);
            throw new DataAccessException("Error filtering vehicles by engine type. Please try again later.", e);
        }
    }
}
