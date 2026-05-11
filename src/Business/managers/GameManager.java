package Business.managers;

import Business.entities.Game;
import Business.entities.Upgrade;
import Persistence.GameDAO;
import java.sql.SQLException;

public class GameManager {
    private final GameDAO gameDAO;
    private Game currentGame;
    private int currentUserId;

    public GameManager(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void loadGame(int userId) {
        this.currentUserId = userId;
        this.currentGame = gameDAO.getByUser(userId);

        if (this.currentGame == null) {
            // No saved game for this user — start a fresh one
            this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false, new java.util.ArrayList<>());
        }
    }

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

    public Game getCurrentGame() {
        return currentGame;
    }

    public int getNewGameID() {
        int lastId = gameDAO.getLastGameIdByUser(currentUserId);
        return (lastId + 1);
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public boolean hasUnfinishedGame(int userId) {
        return gameDAO.getUnfinishedGameByUser(userId) != null;
    }

    public void createNewGame(int userId) {
        this.currentUserId = userId;
        this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false, new java.util.ArrayList<>());
        try {
            saveGame();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addCoffee() {
        if (currentGame == null) return;
        currentGame.setNumCoffees(currentGame.getNumCoffees() + 1);
    }

    public boolean buyUpgrade(Upgrade upgrade) {
        if (currentGame == null) return false;
        if (currentGame.isUpgradePurchased(upgrade.getName())) return false;
        if (!currentGame.spendCoffees(upgrade.getCost())) return false;
        currentGame.applyUpgrade(upgrade.getTargetGeneratorName(), upgrade.getMultiplier());
        currentGame.markUpgradePurchased(upgrade.getName());
        return true;
    }
}
