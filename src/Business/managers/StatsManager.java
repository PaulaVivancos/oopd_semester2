package Business.managers;

import Business.entities.CoffeeStats;
import Persistence.StatsDAO;

import java.util.List;

/**
 * Manages coffee game statistics by delegating persistence operations to {@link StatsDAO}.
 */
public class StatsManager {
    private final StatsDAO statsDAO;

    /**
     * Creates a StatsManager with the given DAO.
     * @param statsDAO the DAO used for stats persistence
     */
    public StatsManager(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }

    /**
     * Returns all registered player usernames.
     * @return list of player usernames
     */
    public List<String> getAllPlayers() {
        return statsDAO.loadAllPlayers();
    }

    /**
     * Returns the IDs of all games played by a given player.
     * @param username the player's username
     * @return list of game IDs
     */
    public List<Integer> getGamesByPlayer(String username) {
        return statsDAO.loadGamesByPlayer(username);
    }

    /**
     * Returns the stats for a specific player and game.
     * @param username the player's username
     * @param gameId   the game ID
     * @return list of {@link CoffeeStats} for that player and game
     */
    public List<CoffeeStats> getStatsByUserAndGame(String username, int gameId) {
        return statsDAO.loadStatsByUserAndGame(username, gameId);
    }

    /**
     * Saves a stat entry for a given game and player.
     * @param gameId  the game ID
     * @param userId  the user ID
     * @param minute  the in-game minute of the stat
     * @param coffees the number of coffees recorded
     */
    public void saveStat(int gameId, int userId, double minute, double coffees) {
        statsDAO.insertStats(gameId, userId, minute, coffees);
    }

    /**
     * Returns the last recorded minute for a given game.
     * @param gameId the game ID
     * @return the last minute recorded in that game
     */
    public double getLastMinute(int gameId) {
        return statsDAO.getLastMinute(gameId);
    }

}
