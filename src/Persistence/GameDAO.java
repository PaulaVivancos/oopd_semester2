package Persistence;

import Business.entities.Game;

import java.sql.SQLException;

public interface GameDAO {
    int save(Game game) throws SQLException;
    Game getByUser(int userId);
    void updateGame(Game game) throws SQLException;
    void deleteGame(int gameId) throws SQLException;
}
