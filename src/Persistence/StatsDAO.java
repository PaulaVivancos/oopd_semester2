package Persistence;

import Business.entities.CoffeeStats;
import java.util.List;

public interface StatsDAO {
    void insertStats(int gameId, int userId, double time, double num_coffees);
    List<CoffeeStats> loadStats();
    void deleteAll();
    List<String> loadAllPlayers();
    List<Integer> loadGamesByPlayer(String username);
    List<CoffeeStats> loadStatsByUserAndGame(String username, int gameId);
    double getLastMinute(int gameId);
}
