package Persistence.SQLDaos;

import Business.entities.User;
import Persistence.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void deleteUser(int id) {
        String query = "DELETE FROM User WHERE user_id = " + id + ";";
        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public boolean existsByUsername(String username) {
        String query = "SELECT user_id FROM user WHERE username = '" + username + "';";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String query = "SELECT user_id FROM user WHERE email = '" + email + "';";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
