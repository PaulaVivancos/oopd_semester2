package Business.managers;

import Business.entities.*;
import Persistence.GameDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the lifecycle of game sessions, including creating, loading, saving,
 * and modifying the current game state. Acts as the main bridge between the
 * presentation layer and the game persistence layer.
 */
public class GameManager {
    private final GameDAO gameDAO;
    private Game currentGame;
    private int currentUserId;
    private final GeneratorFactory factory = new CoffeeGeneratorFactory();

    /**
     * Creates a new GameManager with the given data access object for game persistence.
     * @param gameDAO the DAO used to save, load, and update game sessions in the database
     */
    public GameManager(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * Loads the most recent unfinished game session for the given user from the database
     * and sets it as the current game. If no saved game is found, creates a new empty
     * game session in memory without persisting it.
     * @param userId the ID of the user whose game session should be loaded
     */
    public void loadGame(int userId) {
        this.currentUserId = userId;
        this.currentGame = gameDAO.getByUser(userId);

        if (this.currentGame == null) {
            this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false, factory);
        }
    }

    /**
     * Persists the current game session to the database. If the game has not been
     * saved before (game ID is 0), inserts it as a new record and updates the game ID.
     * Otherwise, updates the existing record.
     * @throws SQLException          if a database error occurs during save or update
     * @throws IllegalStateException if no game is currently loaded or if the game's
     *                               user ID does not match the current user ID
     */
    public void saveGame() throws SQLException {
        if (currentGame == null) {
            throw new IllegalStateException("No game loaded. Call loadGame() first.");
        }
        if (currentGame.getUserId() != currentUserId) {
            throw new IllegalStateException("Game user mismatch — cannot save.");
        }

        if (currentGame.getGameId() == 0) {
            int newId = gameDAO.save(currentGame);
            currentGame.setGameId(newId);
        } else {
            gameDAO.updateGame(currentGame);
        }
    }

    /**
     * Returns the game session currently loaded in memory, or null if no game
     * has been loaded or created yet.
     * @return the current game session, or null
     */
    public Game getCurrentGame() {
        return currentGame;
    }


    /**
     * Checks whether the given user has an unfinished game session saved in the database.
     * @param userId the ID of the user to check
     * @return true if an unfinished game session exists for this user, false otherwise
     */
    public boolean hasUnfinishedGame(int userId) {
        return gameDAO.getUnfinishedGameByUser(userId) != null;
    }

    /**
     * Creates a new game session for the given user. If a current unfinished game exists,
     * it is marked as finished, its end time is set, and it is updated in the database
     * before the new game is created. The new game is immediately saved to the database.
     * @param userId the ID of the user for whom the new game session is created
     */
    public void createNewGame(int userId) {
        this.currentUserId = userId;

        if (this.currentGame != null && !this.currentGame.isFinished()) {
            this.currentGame.setFinished(true);
            this.currentGame.setEndTime(java.time.LocalDateTime.now());
            this.currentGame.stopGame();

            try {
                gameDAO.updateGame(this.currentGame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false, factory);
        try {
            saveGame();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds one coffee to the current game session's coffee count.
     * Does nothing if no game is currently loaded.
     */
    public void addCoffee() {
        if (currentGame == null) return;
        currentGame.setNumCoffees(currentGame.getNumCoffees() + 1);
    }

    /**
     * Attempts to purchase one unit of the generator at the given index
     * in the current game session. Does nothing if no game is currently loaded.
     * @param id the index of the generator to purchase
     */
    public void addGenerator(int id) {
        if (currentGame == null) return;
        currentGame.addGenerator(id);
    }

    /**
     * Returns the list of all generator instances in the current game session,
     * including those with zero quantity.
     * @return the list of generators in the current game
     */
    public ArrayList<Generator> getGenerators() {
        return currentGame.getGenerators();
    }

    /**
     * Returns the list of generator types from the current game session,
     * extracted from each generator instance. Used to populate the shop view.
     * @return a list of generator types from the current game
     */
    public List<GeneratorType> getGeneratorTypes() {
        List<GeneratorType> types = new ArrayList<>();
        for (Generator gen : currentGame.getGenerators()) {
            types.add(gen.getType());
        }
        return types;
    }

    /**
     * Attempts to purchase the given upgrade in the current game session.
     * The purchase fails if no game is loaded, the upgrade has already been purchased,
     * or the player cannot afford it. On success, applies the upgrade multiplier
     * to the target generator and records the upgrade as purchased.
     * @param upgrade the upgrade to purchase
     * @return true if the upgrade was successfully purchased, false otherwise
     */
    public boolean buyUpgrade(Upgrade upgrade) {
        if (currentGame == null) return false;
        if (currentGame.isUpgradePurchased(upgrade.getName())) return false;
        if (!currentGame.spendCoffees(upgrade.getCost())) return false;

        currentGame.applyUpgrade(upgrade.getTargetGeneratorName(), upgrade.getMultiplier());

        currentGame.markUpgradePurchased(upgrade.getName());
        return true;
    }

}
