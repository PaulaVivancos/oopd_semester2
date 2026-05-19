package Business.managers;

import Business.entities.User;
import Persistence.SQLDaos.UserSQLDao;
import Persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

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

    public UserManager(UserDAO userDao) {
        this.userDao = userDao;
    }

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

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

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

        // Hash
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, email, hashed);

        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating user.";
        }

        currentUser = userDao.findByUsernameOrEmail(username);

        return null;
    }

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
}
