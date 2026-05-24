package Persistence;

import Business.entities.User;

import java.sql.SQLException;

/**
 * Interface that abstracts the persistence of users from the rest of the code.
 * In particular, it follows the Data Access Object design pattern, which is commonly used to abstract persistence
 * implementations with a set of generic operations.
 */
public interface UserDAO {

    /**
     * Inserts a new user record into the database.
     * @param user the user to insert
     * @throws SQLException if a database access error occurs
     */
    void insertUser(User user) throws SQLException;

    /**
     * Looks up a user by their username or email.
     * @param value the username or email to search by
     * @return the matching User, or null if not found
     */
    User findByUsernameOrEmail(String value);

    /**
     * Deletes the user record with the given ID.
     * @param id the unique identifier of the user to delete
     * @throws SQLException if a database error occurs
     */
    void deleteUser(int id) throws SQLException;

    /**
     * Checks whether a user with the given username already exists.
     * @param username the username to look up
     * @return true if the username is taken, false otherwise
     */
    boolean existsByUsername(String username);


    /**
     * Checks whether a user with the given email is already registered.
     * @param email the email address to look up
     * @return true if the email is registered, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Updates the password for the account associated with the given email.
     * @param email       the email of the account to update
     * @param newPassword the new hashed password to store
     */
    void updatePasswordByEmail(String email, String newPassword);
}

