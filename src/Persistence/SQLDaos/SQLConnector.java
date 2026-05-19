package Persistence.SQLDaos;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Class that manages the MySQL database connection and provides
 * methods for executing common SQL operations. Connection settings
 * are loaded from src/config.json at initialization.
 */
public class SQLConnector {

    private static SQLConnector instance = null;

    /**
     * Returns the single instance of SQLConnector, creating and connecting it
     * on first call by reading credentials from src/config.json.
     * @return the shared SQLConnector instance, or null if config could not be read
     */
    public static SQLConnector getInstance() {
        if (instance == null) {
            try {
                // Read config.json
                String content = new String(Files.readAllBytes(Paths.get("src/config.json")));
                JSONObject config = new JSONObject(content);

                // Extract values from JSON
                String user     = config.getString("db_user");
                String password = config.getString("db_password");
                String host     = config.getString("db_host");
                int    port     = config.getInt("db_port");
                String dbName   = config.getString("db_name");

                instance = new SQLConnector(user, password, host, port, dbName);
                instance.connect();

            } catch (IOException e) {
                System.err.println("Could not read config.json --> " + e.getMessage());
            }
        }
        return instance;
    }

    private final String username;
    private final String password;
    private final String url;
    private Connection conn;

    /**
     * Builds the JDBC connection URL from the provided parameters.
     * @param username the database username
     * @param password the database password
     * @param ip the host address of the database server
     * @param port the port the database server is listening on
     * @param database the name of the target database
     */
    private SQLConnector(String username, String password, String ip, int port, String database) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
    }


    /**
     * Opens a JDBC connection to the database using the configured URL and credentials.
     */
    public void connect() {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Couldn't connect to --> " + url + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Executes an INSERT statement against the database.
     * @param query the SQL INSERT query to execute
     */
    public void insertQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when inserting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Executes an UPDATE statement against the database.
     * @param query the SQL UPDATE query to execute
     */
    public void updateQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when updating --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }


    /**
     * Executes a DELETE statement against the database.
     * @param query the SQL DELETE query to execute
     */
    public void deleteQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when deleting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Executes a SELECT query and returns the resulting rows.
     * @param query the SQL SELECT query to execute
     * @return the ResultSet from the query, or null if an error occurred
     */
    public ResultSet selectQuery(String query) {
        ResultSet rs = null;
        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when selecting data --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
        return rs;
    }

    /**
     * Closes the active database connection.
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem when closing the connection --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }
}