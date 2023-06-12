package the.husky.dao.jdbc;

import the.husky.dao.VehicleDao;
import the.husky.dao.jdbc.mapper.VehicleRowMapper;
import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Vehicle> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public void add(Vehicle vehicle) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, vehicle.getManufacture().getManufacture());
            statement.setString(2, vehicle.getEngineType().getType());
            statement.setString(3, vehicle.getModel());
            statement.setDouble(4, vehicle.getPrice());
            statement.setInt(5, vehicle.getAge());
            statement.setInt(6, vehicle.getWeight());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Vehicle updatedVehicle) {
        try (Connection connection = connect();
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vehicle getById(int id) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return VEHICLE_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> filterByManufacturer(String manufacturer) {
        List<Vehicle> filteredVehicles = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_MANUFACTURE)) {
            statement.setString(1, manufacturer);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                    filteredVehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return filteredVehicles;
    }


    @Override
    public List<Vehicle> filterByEngineType(EngineType type) {
        List<Vehicle> filteredVehicles = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ENGINE_TYPE)) {
            statement.setString(1, type.getType());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = VEHICLE_ROW_MAPPER.mapRow(resultSet);
                    filteredVehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return filteredVehicles;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OS", "postgres", "root");
    }
}
