package Business.managers;

import Business.entities.User;
import Persistence.SQLDaos.UserSQLDao;

import java.sql.SQLException;
import java.util.List;

/**
 * The UserManager class will act as the system's model, storing and
 * managing the information the user enters.
 *
 * The UserManager class will use the system's DAOs to manage
 * to abstractly access and modify the information.
 */
public class UserManager {
    private final UserSQLDao userDao;

    public UserManager() {
        this.userDao = new UserSQLDao();
    }

    public boolean login(String username_email, String password) {
        if (!username_email.isEmpty() && !password.isEmpty()) {
            User user = userDao.findByNameEmailAndPassword(username_email, password);
            return user != null;
        }
        return false;
    }

    public boolean register(String username, String email, String password, String password_confirmation) {
        if (password.equals(password_confirmation) && !password.isEmpty()) {
            User user = new User(username, email, password);
            addStudent(user);
            return user != null;
        }
        return false;
    }

    private void addStudent(User user) {
        try {
            userDao.insertUser(user);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    public void deleteUser(int id) throws SQLException {
        userDao.deleteUser(id);
    }

}
