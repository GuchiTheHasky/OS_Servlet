package the.husky.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import the.husky.entity.Vehicle;
import the.husky.repository.VehicleDao;
import the.husky.repository.jdbc.mapper.VehicleRowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
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

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcVehicleDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Vehicle> findAll() {
        return jdbcTemplate.query(SELECT_ALL, VEHICLE_ROW_MAPPER);
    }

    @Override
    public synchronized void save(Vehicle vehicle) {
        jdbcTemplate.update(INSERT, vehicle.getManufacture().getManufacture(), vehicle.getEngineType().getType(),
                vehicle.getModel(), vehicle.getPrice(), vehicle.getAge(), vehicle.getWeight());
    }

    @Override
    public synchronized void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public synchronized void update(Vehicle vehicle) {
        jdbcTemplate.update(UPDATE, vehicle.getManufacture().getManufacture(), vehicle.getEngineType().getType(),
                vehicle.getModel(), vehicle.getPrice(), vehicle.getAge(), vehicle.getWeight(),
                vehicle.getVehicleId());
    }

    @Override
    public Optional<Vehicle> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_BY_ID, VEHICLE_ROW_MAPPER, id));
    }

    @Override
    public List<Vehicle> filterByManufacturer(String manufacturer) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle vehicle : findAll()) {
            if (vehicle.getManufacture().getManufacture().equals(manufacturer)) {
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> filterByEngineType(String engineType) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle vehicle : findAll()) {
            if (vehicle.getEngineType().getType().equals(engineType)) {
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    @Override
    public synchronized void deleteAll() {
        for (Vehicle vehicle : findAll()) {
            delete(vehicle.getVehicleId());
        }
    }
}
