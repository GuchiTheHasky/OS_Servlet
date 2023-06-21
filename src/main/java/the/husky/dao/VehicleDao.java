package the.husky.dao;

import the.husky.entity.vehicle.EngineType;
import the.husky.entity.vehicle.Vehicle;

import java.util.List;

/**
 * The VehicleDao interface provides a set of methods for accessing and manipulating vehicle data in a data source.
 * Implementations of this interface can be used to interact with a vehicle database and perform various operations
 * such as retrieving, adding, updating, and deleting vehicle records.
 */

public interface VehicleDao {
    /**
     * This method retrieves all vehicle records from the data source and returns them as a list of Vehicle objects.
     * It allows you to get a comprehensive list of all vehicles available in the database.
     */
    List<Vehicle> findAll();

    /**
     * The add method adds a new vehicle record to the data source. It takes a Vehicle object as a parameter,
     * representing the vehicle to be added. This method is used to insert a new vehicle into the database.
     */
    void save(Vehicle vehicle);

    /**
     * The delete method removes a vehicle record from the data source based on the provided ID. It takes an integer
     * parameter id representing the unique identifier of the vehicle to be deleted. This method allows you to delete
     * a specific vehicle from the database.
     */
    void delete(int id);

    /**
     * The edit method updates an existing vehicle record in the data source. It takes a Vehicle object as a parameter,
     * representing the updated vehicle information. This method is used to modify the details of an existing vehicle
     * in the database.
     */
    void update(Vehicle vehicle);

    /**
     * The getById method retrieves a vehicle record from the data source based on the provided ID. It takes an integer
     * parameter id representing the unique identifier of the vehicle and returns the corresponding Vehicle object.
     * This method is useful for fetching a vehicle by its ID.
     */
    Vehicle findById(int id);

    /**
     * The filterByManufacturer method retrieves a list of vehicle records from the data source that match the provided
     * manufacturer. It takes a String parameter manufacturer representing the manufacturer name and returns a list of
     * Vehicle objects. This method allows you to filter vehicles by their manufacturer.
     */
    List<Vehicle> filterByManufacturer(String manufacturer);

    /**
     * The filterByEngineType method retrieves a list of vehicle records from the data source that have the provided
     * engine type. It takes an EngineType parameter type representing the engine type and returns a list of
     * Vehicle objects. This method allows you to filter vehicles by their engine type.
     */
    List<Vehicle> filterByEngineType(EngineType type);
}
