package Business.managers;

import Business.Exceptions.SendEmailException;
import Business.entities.EmailService;
import Business.entities.User;
import Persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Manages user authentication and account operations, acting as the business
 * layer between controllers and the persistence layer. Maintains the session
 * of the currently logged-in user.
 */
public class UserManager {
    private final UserDAO userDao;
    private User currentUser;
    private EmailService emailService;
    private String sendedCode;

    /**
     * Constructor method to create a new User Manager
     * @param userDao the DAO used to access and modify user records
     */
    public UserManager(UserDAO userDao) {
        this.userDao = userDao;
        this.emailService = new EmailService();
    }

    /**
     * Attempts to log in with the given credentials. Sets the current user on success.
     *
     * @param username_email the user's username or email
     * @param password       the user's password
     * @return true if credentials are valid, false otherwise
     */
    public boolean login(String username_email, String password) {
        if (username_email.isEmpty() || password.isEmpty())
            return false;

        User user = userDao.findByUsernameOrEmail(username_email);

        if (user == null)
            return false;

        if (BCrypt.checkpw(password, user.getPassword())) {
            currentUser = user;
            return true;
        }

        return false;
    }

    /**
     * Logs out the current user by clearing the active session.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * @return the currently logged-in user, or null if no session is active
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Validates the provided registration fields and creates a new user account if valid.
     *
     * @param username              the desired username
     * @param email                 the user's email address
     * @param password              the desired password
     * @param password_confirmation must match password
     * @return null on success, or an error message string describing the validation failure
     */
    public String signUp(String username, String email, String password, String password_confirmation) {
        if (password.length() < 8)
            return "Password must be at least 8 characters.";

        boolean hasLower = false, hasUpper = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLower)
            return "Password must contain at least one lowercase letter.";
        if (!hasUpper)
            return "Password must contain at least one uppercase letter.";
        if (!hasDigit)
            return "Password must contain at least one number.";

        if (!password.equals(password_confirmation))
            return "Passwords do not match.";
        if (!email.contains("@"))
            return "Invalid email address.";
        if (userDao.existsByUsername(username))
            return "Username is already taken.";
        if (userDao.existsByEmail(email))
            return "Email is already registered.";

        // Hash password
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, email, hashed);

        // Call helper method to persist the user and return its result
        return addStudent(user, username);
    }

    /**
     * Sends a verification code to the given email if it exists in the system.
     * @param email the email address to send the code to
     * @return true if the code was sent successfully, false if the email wasn't found or sending failed
     */
    public boolean handleSendCode(String email) {
        if (!userDao.existsByEmail(email)) {
            sendedCode = null;
            return false;
        }

        try {
            sendedCode = emailService.sendVerificationCode(email);
            return true;
        } catch (SendEmailException e) {
            sendedCode = null;
            return false;
        }
    }

    /**
     * Persists a new user to the database.
     * * @param user     the user to insert
     * @param username the username to re-query the session user
     * @return null on success, or an error string on failure
     */
    private String addStudent(User user, String username) {
        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating user.";
        }

        currentUser = userDao.findByUsernameOrEmail(username);
        return null;
    }

    /**
     * Deletes the currently logged-in user's account and clears the session.
     * @return true if the user was deleted successfully, false if no user is logged in or an error occurs
     */
    public boolean deleteUser() {
        if (currentUser == null)
            return false;

        try {
            userDao.deleteUser(currentUser.getId());
            currentUser = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return the verification code most recently sent via email, or null if sending failed
     */
    public String getSendCode() {
        return sendedCode;
    }

    /**
     * Validates and updates the password for the account associated with the given email.
     * @param email                the email of the account to update
     * @param newPassword          the new password
     * @param passwordConfirmation must match newPassword
     * @return null on success, or an error message describing the failure
     */
    public String changePassword(String email, String newPassword, String passwordConfirmation) {
        if (email == null || email.trim().isEmpty()) {
            return "Invalid session or email context.";
        }

        if (newPassword.length() < 8) {
            return "Password must be at least 8 characters.";
        }

        if (!newPassword.equals(passwordConfirmation)) {
            return "Passwords do not match.";
        }

        try {
            String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            userDao.updatePasswordByEmail(email, hashed);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating the password in our records.";
        }
    }

    /**
     * @return the username of the currently logged-in user
     */
    public String getLoggedInUsername() {
        return currentUser.getUsername();
    }
}