package Persistence;

import Business.entities.Game;

import java.sql.SQLException;

public interface GameDAO {
    int save(Game game) throws SQLException;
    Game getByUser(int userId);
    void updateGame(Game game) throws SQLException;
    void deleteGame(int gameId) throws SQLException;
    int getLastGameIdByUser(int userId);
    Game getUnfinishedGameByUser(int userId);
    void saveGameGenerators(Game game) throws SQLException;
    void saveGameUpgrades(Game game) throws SQLException;
}
