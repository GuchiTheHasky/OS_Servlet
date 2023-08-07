package the.husky.dao;

import the.husky.entity.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * The VehicleDao interface provides a set of methods for accessing and manipulating vehicle data in a data source.
 * Implementations of this interface can be used to interact with a vehicle database and perform various operations
 * such as retrieving, adding, updating, and deleting vehicle records.
 */

public interface VehicleDao {
    /**
     * Retrieves a list of all vehicles stored in the data source.
     *
     * @return An iterable containing lists of vehicles.
     */

    Iterable<List<Vehicle>> findAll();

    /**
     * Adds a new vehicle record to the data source.
     *
     * @param vehicle The vehicle object to be added.
     */
    void save(Vehicle vehicle);

    /**
     * Deletes a vehicle record from the data source based on the provided ID.
     *
     * @param id The unique identifier of the vehicle to be deleted.
     */
    void delete(int id);

    /**
     * Updates an existing vehicle record in the data source.
     *
     * @param vehicle The updated vehicle information.
     */
    void update(Vehicle vehicle);

    /**
     * Retrieves a vehicle record from the data source based on the provided ID.
     *
     * @param id The unique identifier of the vehicle.
     * @return An optional containing the corresponding Vehicle object if found, or empty if not found.
     */
    Optional<Vehicle> findById(int id);
}
