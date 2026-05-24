package Business.managers;

import Business.entities.CoffeeStats;
import Persistence.StatsDAO;

import java.util.List;

public class StatsManager {
    private final StatsDAO statsDAO;

    public StatsManager(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }

    public List<String> getAllPlayers() {
        return statsDAO.loadAllPlayers();
    }

    public List<Integer> getGamesByPlayer(String username) {
        return statsDAO.loadGamesByPlayer(username);
    }

    public List<CoffeeStats> getStatsByUserAndGame(String username, int gameId) {
        return statsDAO.loadStatsByUserAndGame(username, gameId);
    }

    public void saveStat(int gameId, double minute, double coffees) {
        statsDAO.insertStats(gameId, minute, coffees);
    }



}
