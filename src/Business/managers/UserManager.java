package Business.managers;

import Business.Exceptions.SendEmailException;
import Business.entities.EmailService;
import Business.entities.User;
import Persistence.SQLDaos.UserSQLDao;
import Persistence.UserDAO;

import java.sql.SQLException;

/**
 * The UserManager class will act as the system's model, storing and
 * managing the information the user enters.
 *
 * The UserManager class will use the system's DAOs to manage
 * to abstractly access and modify the information.
 */
public class UserManager {
    private final UserDAO userDao;
    private User currentUser;
    private EmailService emailService;
    private String sendedCode;

    public UserManager(UserDAO userDao) {
        this.userDao = userDao;
        emailService = new EmailService();
    }

    public boolean login(String username_email, String password) {
        if (!username_email.isEmpty() && !password.isEmpty()) {
            currentUser = userDao.findByNameEmailAndPassword(username_email, password);
            return currentUser != null;
        }
        return false;
    }
    public void logout(){
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Returns null on success, or an error message string on failure.
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


    public void handleSendCode(String email) {
        try {
            sendedCode =  emailService.sendVerificationCode(email);

        } catch (SendEmailException e) {
            sendedCode = null;
        }

    }

    private void addStudent(User user) {
        try {
            userDao.insertUser(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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

    public String getSendCode() {
        return sendedCode;
    }

    public void changePassword(String oldPassword, String newPassword) {
        //userDao.changePassword();
    }
}