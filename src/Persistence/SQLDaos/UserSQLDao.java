package Persistence.SQLDaos;

import Business.entities.User;
import Persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQLDao implements UserDAO {

    @Override
    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO user(username, email, password) VALUES ('" +
                user.getUsername() + "', '" +
                user.getEmail() + "', '" +
                user.getPassword() + "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public User findByUsernameOrEmail(String value) {
        String query = "SELECT * FROM user WHERE username = '" + value +
                "' OR email = '" + value + "' LIMIT 1;";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result != null && result.next()) {
                int id = result.getInt("user_id");
                String username = result.getString("username");
                String email = result.getString("email");
                String hashedPassword = result.getString("password");

                return new User(id, username, email, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User findByNameEmailAndPassword(String usernameOrEmail, String plainPassword) {
        String query = "SELECT user_id, username, email, password FROM user WHERE username = '"
                + usernameOrEmail + "' OR email = '" + usernameOrEmail + "' LIMIT 1;";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result != null && result.next()) {
                String hashedPassword = result.getString("password");

                if (BCrypt.checkpw(plainPassword, hashedPassword)) {
                    int id = result.getInt("user_id");
                    String username = result.getString("username");
                    String email = result.getString("email");

                    result.getStatement().close();

                    return new User(id, username, email, hashedPassword);
                }

                result.getStatement().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Returns null if user wasn't found or password verification failed
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

    @Override
    public void updateUser(User user) {
        String query = "UPDATE user SET username = '" + user.getUsername() +
                "', email = '" + user.getEmail() +
                "', password = '" + user.getPassword() +
                "' WHERE user_id = " + user.getId() + ";";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM user WHERE user_id = " + id + ";";
        SQLConnector.getInstance().insertQuery(query);
    }

    public void updatePasswordByEmail(String email, String newPassword) {
        String query = "UPDATE user SET password = '" + newPassword +
                "' WHERE email = '" + email + "';";
        SQLConnector.getInstance().updateQuery(query);
    }
}
