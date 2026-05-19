package Persistence.SQLDaos;

import Business.entities.CoffeeStats;
import Persistence.StatsDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsSQLDao implements StatsDAO {
    @Override
    public void insertStats(int gameId, double time, int num_coffees) {
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
    public List<Integer> loadAllPlayers() {
        List<Integer> players = new ArrayList<>();
        String query = "SELECT DISTINCT user_id FROM game ORDER BY user_id;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);

        try {
            while (rs != null && rs.next()) {
                players.add(Integer.parseInt(rs.getString("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Integer> loadGamesByPlayer(int playerId) {
        List<Integer> games = new ArrayList<>();
        String query = "SELECT game_id FROM game WHERE user_id = '" + playerId + "' ORDER BY game_id;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);

        try {
            while (rs != null && rs.next()) {
                games.add(Integer.parseInt(rs.getString("game_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public List<CoffeeStats> loadStatsByUserAndGame(int playerId, int gameId) {
        List<CoffeeStats> list = new ArrayList<>();

        String query = "SELECT s.minute, s.num_coffees FROM statistics s " +
                "JOIN game g ON s.game_id = g.game_id " +
                "WHERE g.user_id = '" + playerId + "' AND s.game_id = " + gameId + " " +
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
