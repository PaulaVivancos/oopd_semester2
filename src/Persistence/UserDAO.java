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
     * Method that saves a specific student, persisting it.
     * @param user The new student to save.
     */
    public void insertUser(User user) throws SQLException;

    /**
     * Looks up a user by matching their username or email along with their password.
     * @param username the username or email to search by
     * @param password the password to match
     * @return the matching User, or null if no match is found
     */
    public User findByNameEmailAndPassword(String username, String password);

    public User findByUsernameOrEmail(String value);
    /**
     * Method that allows to update the user with the information changed.
     * @param user is the user whose information has been changed and it's going to be updated.
     * @throws SQLException if an error occurs.
     */
    public void updateUser(User user) throws SQLException;

    /**
     * Deletes the user record with the given ID.
     * @param id the unique identifier of the user to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteUser(int id) throws SQLException;

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

    void updatePasswordByEmail(String email, String newPassword);
}

