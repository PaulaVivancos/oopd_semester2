package Persistence.SQLDaos;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class SQLConnector {

    private static SQLConnector instance = null;

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

    // --- Everything below stays exactly the same as your original class ---

    private final String username;
    private final String password;
    private final String url;
    private Connection conn;

    private SQLConnector(String username, String password, String ip, int port, String database) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Couldn't connect to --> " + url + " (" + e.getMessage() + ")");
        }
    }

    public void insertQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when inserting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    public void updateQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when updating --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    public void deleteQuery(String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when deleting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

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

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem when closing the connection --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }
}