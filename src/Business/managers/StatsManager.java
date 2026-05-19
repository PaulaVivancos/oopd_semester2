package Business.managers;

import Business.entities.CoffeeStats;
import Persistence.StatsDAO;

import java.util.List;

public class StatsManager {
    private final StatsDAO statsDAO;

    public StatsManager(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }

    public List<Integer> getAllPlayers() {
        return statsDAO.loadAllPlayers();
    }

    public List<Integer> getGamesByPlayer(int playerId) {
        return statsDAO.loadGamesByPlayer(playerId);
    }

    public List<CoffeeStats> getStatsByUserAndGame(int playerId, int gameId) {
        return statsDAO.loadStatsByUserAndGame(playerId, gameId);
    }

    public void saveStat(int gameId, double minute, int coffees) {
        statsDAO.insertStats(gameId, minute, coffees);
    }



}
