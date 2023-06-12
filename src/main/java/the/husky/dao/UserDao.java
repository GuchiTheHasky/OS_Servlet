package the.husky.dao;

import the.husky.entity.user.User;
import the.husky.exception.DataAccessException;

import java.util.List;

/**
 * The UserDao interface provides a set of methods for accessing and manipulating user data in a data source.
 * Implementations of this interface can be used to interact with a user database and perform various operations
 * such as retrieving, adding, updating, and deleting user records.
 */

public interface UserDao {
    /**
     * This method retrieves all user records from the data source and returns them as a list of User objects.
     * It allows you to get a comprehensive list of all users available in the database.
     */
    List<User> findAll() throws DataAccessException;

    /**
     * The add method adds a new user record to the data source. It takes a User object as a parameter,
     * representing the user to be added. This method is used to insert a new user into the database.
     */
    void add(User user) throws DataAccessException;

    /**
     * This method searches for a user record in the data source based on the provided name.
     * It takes a String parameter name and returns the corresponding User object if found.
     * It allows you to retrieve a specific user by their name.
     */
    User findUserByName(String name) throws DataAccessException;

    /**
     * The findById method retrieves a user record from the data source based on the provided ID.
     * It takes an integer parameter id representing the unique identifier of the user and returns
     * the corresponding User object. This method is useful for fetching a user by their ID.
     */
    User findById(int id) throws DataAccessException;

    /**
     * The update method updates an existing user record in the data source. It takes a User object as a parameter,
     * representing the updated user information. This method is used to modify the details
     * of an existing user in the database.
     */
    void update(User user) throws DataAccessException;

    /**
     * The delete method removes a user record from the data source based on the provided ID.
     * It takes an integer parameter id representing the unique identifier of the user to be deleted.
     * This method allows you to delete a specific user from the database.
     */
    void delete(int id) throws DataAccessException;
}
