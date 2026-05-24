package Persistence.SQLDaos;

import Business.entities.CoffeeStats;
import Persistence.StatsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsSQLDao implements StatsDAO {
    @Override
    public void insertStats(int gameId, double time, double num_coffees) {
        String query = "INSERT INTO statistics(game_id, minute, num_coffees) VALUES (" +
                gameId + ", " +
                time + ", " +
                num_coffees + ");";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public List<CoffeeStats> loadStats() {
        List<CoffeeStats> list = new ArrayList<>();

        String query = "SELECT minute, num_coffees FROM statistics ORDER BY minute;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);

        try {
            while (rs != null && rs.next()) {
                double time = rs.getDouble("minute");
                int coffees = rs.getInt("num_coffees");

                list.add(new CoffeeStats(time, coffees));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteAll() {
        String query = "DELETE FROM statistics;";
        SQLConnector.getInstance().deleteQuery(query);
    }

    @Override
    public List<String> loadAllPlayers() {
        List<String> players = new ArrayList<>();
        String query = "SELECT username FROM user ORDER BY username;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            while (rs != null && rs.next()) {
                players.add(rs.getString("username").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Integer> loadGamesByPlayer(String username) {
        List<Integer> games = new ArrayList<>();
        String query = "SELECT g.game_id FROM game g " +
                "JOIN user u ON g.user_id = u.user_id " +
                "WHERE u.username = '" + username + "' ORDER BY g.game_id;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);

        try {
            while (rs != null && rs.next()) {
                games.add(rs.getInt("game_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public List<CoffeeStats> loadStatsByUserAndGame(String username, int gameId) {
            List<CoffeeStats> list = new ArrayList<>();
            String query = "SELECT s.minute, s.num_coffees FROM statistics s " +
                    "JOIN game g ON s.game_id = g.game_id " +
                    "JOIN user u ON g.user_id = u.user_id " +
                    "WHERE u.username = '" + username + "' AND s.game_id = " + gameId + " " +
                    "ORDER BY s.minute;";

        ResultSet rs = SQLConnector.getInstance().selectQuery(query);

        try {
            while (rs != null && rs.next()) {
                double time = rs.getDouble("minute");
                int coffees = rs.getInt("num_coffees");
                list.add(new CoffeeStats(time, coffees));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
