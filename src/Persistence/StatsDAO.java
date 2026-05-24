package Persistence;

import Business.entities.CoffeeStats;
import java.util.List;

/**
 * DAO interface for persisting and retrieving coffee game statistics.
 */
public interface StatsDAO {

    /**
     * Inserts a stat entry for a given game and user.
     * @param gameId      the game ID
     * @param userId      the user ID
     * @param time        the in-game minute of the stat
     * @param num_coffees the number of coffees recorded
     */
    void insertStats(int gameId, int userId, double time, double num_coffees);

    /**
     * Returns all recorded stats across all games and players.
     * @return list of all {@link CoffeeStats}
     */
    List<CoffeeStats> loadStats();

    /**
     * Deletes all stats from the database.
     */
    void deleteAll();

    /**
     * Returns all registered player usernames.
     * @return list of usernames
     */
    List<String> loadAllPlayers();

    /**
     * Returns the IDs of all games played by a given player.
     * @param username the player's username
     * @return list of game IDs
     */
    List<Integer> loadGamesByPlayer(String username);

    /**
     * Returns the stats for a specific player and game.
     * @param username the player's username
     * @param gameId   the game ID
     * @return list of {@link CoffeeStats} for that player and game
     */
    List<CoffeeStats> loadStatsByUserAndGame(String username, int gameId);

    /**
     * Returns the last recorded minute for a given game.
     * @param gameId the game ID
     * @return the last minute recorded in that game
     */
    double getLastMinute(int gameId);
}