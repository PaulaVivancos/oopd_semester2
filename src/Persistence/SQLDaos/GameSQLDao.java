package Persistence.SQLDaos;

import Business.entities.Game;
import Persistence.GameDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GameSQLDao implements GameDAO {

    @Override
    public int save(Game game) throws SQLException {
        String query = "INSERT INTO game (user_id, start_time, end_time, num_coffees, finished) VALUES ("
                + game.getUserId() + ", "
                + "'" + game.getStartTime() + "', "
                + "NULL, "
                + game.getNumCoffees() + ", "
                + (game.isFinished() ? 1 : 0) + ");";

        SQLConnector.getInstance().insertQuery(query);

        ResultSet rs = SQLConnector.getInstance().selectQuery("SELECT LAST_INSERT_ID() AS game_id;");
        if (rs != null && rs.next()) {
            return rs.getInt("game_id");
        }
        return -1;
    }

    @Override
    public Game getByUser(int userId) {
        String query = "SELECT * FROM game WHERE user_id = " + userId + " AND finished = 0 LIMIT 1;";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            if (rs != null && rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateGame(Game game) throws SQLException {
        String endTime = (game.getEndTime() == null) ? "NULL" : "'" + game.getEndTime() + "'";
        String query = "UPDATE game SET num_coffees = " + game.getNumCoffees()
                + ", end_time = " + endTime
                + ", finished = " + (game.isFinished() ? 1 : 0)
                + " WHERE game_id = " + game.getGameId() + ";";
        SQLConnector.getInstance().updateQuery(query);
    }

    @Override
    public void deleteGame(int gameId) {
        SQLConnector.getInstance().deleteQuery("DELETE FROM statistics WHERE game_id = " + gameId + ";");
        SQLConnector.getInstance().deleteQuery("DELETE FROM game_upgrade WHERE game_id = " + gameId + ";");
        SQLConnector.getInstance().deleteQuery("DELETE FROM game_generator WHERE game_id = " + gameId + ";");
        SQLConnector.getInstance().deleteQuery("DELETE FROM game WHERE game_id = " + gameId + ";");
    }

    private Game mapRow(ResultSet rs) throws SQLException {
        int gameId = rs.getInt("game_id");
        int userId = rs.getInt("user_id");
        LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
        LocalDateTime endTime = rs.getTimestamp("end_time") != null
                ? rs.getTimestamp("end_time").toLocalDateTime() : null;
        double numCoffees = rs.getDouble("num_coffees");
        boolean finished = rs.getInt("finished") == 1;
        return new Game(gameId, userId, startTime, endTime, numCoffees, finished);
    }
}
