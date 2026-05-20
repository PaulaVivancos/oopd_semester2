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
            int generatedId = rs.getInt("game_id");
            game.setGameId(generatedId);

            saveGameGenerators(game);
            saveGameUpgrades(game);

            return generatedId;
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

        saveGameGenerators(game);
        saveGameUpgrades(game);
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
        int numCoffees = rs.getInt("num_coffees");
        boolean finished = rs.getInt("finished") == 1;

        Game game = new Game(gameId, userId, startTime, endTime, numCoffees, finished);

        loadGameGenerators(game);
        loadGameUpgrades(game);

        game.reapplyPurchasedUpgrades();

        return game;
    }

    @Override
    public int getLastGameIdByUser(int userId) {
        String query = "SELECT MAX(game_id) FROM game WHERE user_id = " + userId;
        ResultSet results = SQLConnector.getInstance().selectQuery(query);

        try {
            if (results != null && results.next()) {
                return results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Game getUnfinishedGameByUser(int userId) {
        String query = "SELECT * FROM game WHERE user_id = " + userId + " AND finished = 0 LIMIT 1";
        ResultSet results = SQLConnector.getInstance().selectQuery(query);

        try {
            if (results != null && results.next()) {
                Game game = new Game(
                        results.getInt("game_id"),
                        results.getInt("user_id"),
                        results.getTimestamp("start_time").toLocalDateTime(),
                        results.getTimestamp("end_time") != null ? results.getTimestamp("end_time").toLocalDateTime() : null,
                        results.getInt("num_coffees"),
                        results.getBoolean("finished")
                );

                loadGameGenerators(game);
                loadGameUpgrades(game);

                return game;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void saveGameGenerators(Game game) throws SQLException {
        SQLConnector.getInstance().deleteQuery("DELETE FROM game_generator WHERE game_id = " + game.getGameId() + ";");

        var generators = game.getGenerators();
        for (int i = 0; i < generators.size(); i++) {
            var gen = generators.get(i);

            int databaseGeneratorId = i + 1;

            String query = "INSERT INTO game_generator (game_id, generator_id, quantity) VALUES ("
                    + game.getGameId() + ", "
                    + databaseGeneratorId + ", "
                    + gen.getQuantity() + ");";
            SQLConnector.getInstance().insertQuery(query);
        }
    }

    public void saveGameUpgrades(Game game) throws SQLException {
        SQLConnector.getInstance().deleteQuery("DELETE FROM game_upgrade WHERE game_id = " + game.getGameId() + ";");

        for (String upgradeName : game.getPurchasedUpgradeNames()) {

            String query = "INSERT INTO game_upgrade (game_id, upgrade_id) " +
                    "SELECT " + game.getGameId() + ", upgrade_id " +
                    "FROM upgrade " +
                    "WHERE name = '" + upgradeName + "' LIMIT 1;";

            SQLConnector.getInstance().insertQuery(query);
        }
    }

    private void loadGameGenerators(Game game) {
        String query = "SELECT generator_id, quantity FROM game_generator WHERE game_id = " + game.getGameId() + ";";
        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            while (rs != null && rs.next()) {
                int genId = rs.getInt("generator_id");
                int quantity = rs.getInt("quantity");

                if (genId >= 0 && genId < game.getGenerators().size()) {
                    game.getGenerators().get(genId).setQuantity(quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadGameUpgrades(Game game) {
        String query = "SELECT u.name " +
                "FROM game_upgrade gu " +
                "INNER JOIN upgrade u ON gu.upgrade_id = u.upgrade_id " +
                "WHERE gu.game_id = " + game.getGameId() + ";";

        ResultSet rs = SQLConnector.getInstance().selectQuery(query);
        try {
            while (rs != null && rs.next()) {
                String upgradeName = rs.getString("name");
                game.markUpgradePurchased(upgradeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
