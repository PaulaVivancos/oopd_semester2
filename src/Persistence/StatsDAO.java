package Persistence;

import Business.entities.CoffeeStats;
import Persistence.SQLDaos.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StatsDAO {
    public void insertStats(int gameId, double time, double num_coffees);
    public List<CoffeeStats> loadStats();
    public void deleteAll();
    public List<String> loadAllPlayers();
    public List<Integer> loadGamesByPlayer(String username);
    public List<CoffeeStats> loadStatsByUserAndGame(String username, int gameId);
}
