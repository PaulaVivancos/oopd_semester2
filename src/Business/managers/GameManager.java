package Business.managers;

import Business.entities.*;
import Persistence.GameDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final GameDAO gameDAO;
    private Game currentGame;
    private int currentUserId;
    private final GeneratorFactory factory = new CoffeeGeneratorFactory();

    public GameManager(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void loadGame(int userId) {
        this.currentUserId = userId;
        this.currentGame = gameDAO.getByUser(userId);

        if (this.currentGame == null) {
            // No saved game for this user — start a fresh one
            this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false, factory);
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
        this.currentGame = new Game(userId, java.time.LocalDateTime.now(), 0, false,factory);
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

    public void addGenerator(int id) {
        if (currentGame == null) return;
        currentGame.addGenerator(id);
    }

    public ArrayList<Generator> getGenerators() {
        return currentGame.getGenerators();
    }

    public List<GeneratorType> getGeneratorTypes() {
        List<GeneratorType> types = new ArrayList<>();
        for (Generator gen : currentGame.getGenerators()) {
            types.add(gen.getType());
        }
        return types;
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
