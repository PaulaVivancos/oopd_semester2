package Business.managers;

import Business.entities.User;
import Persistence.SQLDaos.UserSQLDao;
import Persistence.UserDAO;

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

    public boolean login(String username, String email, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public void addStudent(User user) {
        userDao.insertUser(user);
    }

    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    public void deleteUser(int id) throws SQLException {
        userDao.deleteUser(id);
    }

}
