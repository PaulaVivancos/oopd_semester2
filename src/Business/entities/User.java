package Business.entities;

/**
 * Represents a user account with credentials and a unique identifier.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    /**
     * Creates a new User without an ID, intended for writing a new record.
     * @param username the user's display name
     * @param email the user's email address
     * @param password the user's password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Creates a User with a known ID, intended for reading an existing record from storage.
     * @param id the unique identifier of the user
     * @param username the user's display name
     * @param email the user's email address
     * @param password the user's password
     */
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * @return the user's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return the user's display name
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return  the user's password
     */
    public String getPassword() {
        return password;
    }

}
