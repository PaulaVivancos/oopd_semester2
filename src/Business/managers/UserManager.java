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
    private User loggedInUser;

    public UserManager() {
        this.userDao = new UserSQLDao();
    }

    public boolean login(String username_email, String password) {
        if (!username_email.isEmpty() && !password.isEmpty()) {
             loggedInUser = userDao.findByNameEmailAndPassword(username_email, password);
            return loggedInUser != null;
        }
        return false;
    }
    public void logout(){
        loggedInUser = null;
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

    public boolean deleteUser(){
        if(loggedInUser == null)
            return false;
        try{
            userDao.deleteUser(loggedInUser.getId());
            loggedInUser = null;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
