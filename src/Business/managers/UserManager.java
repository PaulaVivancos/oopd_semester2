package Business.managers;

import Business.Exceptions.SendEmailException;
import Business.entities.EmailService;
import Business.entities.User;
import Persistence.SQLDaos.UserSQLDao;
import Persistence.UserDAO;

import java.sql.SQLException;

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
        emailService = new EmailService();
    }

    /**
     * Attempts to log in with the given credentials. Sets the current user on success.
     * @param username_email the user's username or email
     * @param password the user's password
     * @return true if credentials are valid, false otherwise
     */
    public boolean login(String username_email, String password) {
        if (!username_email.isEmpty() && !password.isEmpty()) {
            currentUser = userDao.findByNameEmailAndPassword(username_email, password);
            return currentUser != null;
        }
        return false;
    }

    /**
     * Logs out the current user by clearing the active session.
     */
    public void logout(){
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
     * @param username the desired username
     * @param email the user's email address
     * @param password the desired password
     * @param password_confirmation must match password
     * @return null on success, or an error message string describing the validation failure
     */
    public String signUp(String username, String email, String password, String password_confirmation) {
        if (password.length() < 8)
            return "Password must be at least 8 characters.";
        if (!password.equals(password_confirmation))
            return "Passwords do not match.";
        if (!email.contains("@"))
            return "Invalid email address.";
        if (userDao.existsByUsername(username))
            return "Username is already taken.";
        if (userDao.existsByEmail(email))
            return "Email is already registered.";

        User user = new User(username, email, password);
        addStudent(user);
        currentUser = userDao.findByNameEmailAndPassword(username, password);
        return null;
    }

    /**
     * Sends a verification code to the given email and stores it for later validation.
     * Stores null if the email could not be reached.
     * @param email the recipient's email address
     */
    public void handleSendCode(String email) {
        try {
            sendedCode =  emailService.sendVerificationCode(email);

        } catch (SendEmailException e) {
            sendedCode = null;
        }

    }

    /**
     * Persists a new user to the database.
     * @param user the user to insert
     */
    private void addStudent(User user) {
        try {
            userDao.insertUser(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the current user's account and clears the active session.
     * @return true if the account was deleted successfully, false otherwise
     */
    public boolean deleteUser(){
        if(currentUser == null)
            return false;
        try{
            userDao.deleteUser(currentUser.getId());
            currentUser = null;
            return true;
        }catch(Exception e){
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
     * Updates the current user's password in the database.
     * @param oldPassword the user's current password
     * @param newPassword the new password to set
     */
    public void changePassword(String oldPassword, String newPassword) {
        //userDao.changePassword();
    }
}