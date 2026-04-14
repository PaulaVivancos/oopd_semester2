package Persistence.SQLDaos;

import Business.entities.User;
import Persistence.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements the methods described in the {@link UserDAO} interface.
 *
 * Specifically, it implements the user persistence in a SQL database.
 */
public class UserSQLDao implements UserDAO {

    @Override
    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO User(username, email, password) VALUES ('" +
                user.getUsername() + "', '" +
                user.getEmail() + "', '" +
                user.getPassword() + "');";

        SQLConnector.getInstance().insertQuery(query);
    }


    public User findByNameEmailAndPassword(String username, String password) {
        String query = "SELECT * FROM user WHERE (username = '" + username + "' OR email = '" + username + "') AND password = '" + password + "';";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                int id = result.getInt("user_id");
                String email = result.getString("email");
                return new User(id, username, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        /*
        List<User> students = new LinkedList<>();
        String query = "SELECT id, login, full_name, birth_date FROM user;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int id = result.getInt("id");
                String login = result.getString("login");
                String studentName = result.getString("full_name");
                String birthDate = result.getString("birth_date");

                students.add(new User(id, login, studentName, birthDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        return users;
    }

    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM User WHERE user_id = " + id + ";";
        SQLConnector.getInstance().insertQuery(query);
    }
}
