package Persistence;

import Business.entities.Game;

import java.sql.SQLException;

/**
 * DAO interface for persisting and retrieving game state.
 */
public interface GameDAO {

    /**
     * Saves a new game and returns its generated ID.
     * @param game the game to insert
     * @return the generated game ID, or -1 on failure
     * @throws SQLException if a database access error occurs
     */
    int save(Game game) throws SQLException;

    /**
     * Returns the active (unfinished) game for the given user.
     * @param userId the user's ID
     * @return the matching Game, or null if none found
     */
    Game getByUser(int userId);

    /**
     * Updates an existing game's state in the database.
     * @param game the game to update
     * @throws SQLException if a database access error occurs
     */
    void updateGame(Game game) throws SQLException;


    /**
     * Returns the unfinished game for the given user, if any.
     * @param userId the user's ID
     * @return the unfinished Game, or null if none found
     */
    Game getUnfinishedGameByUser(int userId);

    /**
     * Persists the generator state of a game, replacing any existing entries.
     * @param game the game whose generators to save
     * @throws SQLException if a database access error occurs
     */
    void saveGameGenerators(Game game) throws SQLException;

    /**
     * Persists the purchased upgrades of a game, replacing any existing entries.
     * @param game the game whose upgrades to save
     * @throws SQLException if a database access error occurs
     */
    void saveGameUpgrades(Game game) throws SQLException;

    /**
     * Loads and applies saved generator quantities into the given game.
     * @param game the game to populate with generator data
     */
    void loadGameGenerators(Game game);

    /**
     * Loads and marks purchased upgrades into the given game.
     * @param game the game to populate with upgrade data
     */
    void loadGameUpgrades(Game game);
}

