package the.husky.repository;

import the.husky.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The UserDao interface provides a set of methods for accessing and manipulating user data in a data source.
 * Implementations of this interface can be used to interact with a user database and perform various operations
 * such as retrieving, adding, updating, and deleting user records.
 */

public interface UserDao {

    /**
     * Retrieves a list of all users stored in the data source.
     *
     * @return An iterable containing lists of users.
     */
    List<User> findAll();

    /**
     * Adds a new user record to the data source.
     *
     * @param user The user object to be added.
     */
    int save(User user);

    /**
     * Retrieves a user record from the data source based on the provided ID.
     *
     * @param id The unique identifier of the user.
     * @return An optional containing the corresponding User object if found, or empty if not found.
     */
    Optional<User> findById(int id);

    /**
     * Updates an existing user record in the data source.
     *
     * @param user The updated user information.
     */
    void update(User user);

    /**
     * Removes a user record from the data source based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    void delete(int id);

    /**
     * Removes all user records from the data source.
     */
    void deleteAll();
}
