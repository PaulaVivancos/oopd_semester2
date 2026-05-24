package Presentation.controllers;

import Business.managers.StatsManager;
import Business.entities.CoffeeStats;
import Presentation.PaintChart;
import Presentation.views.StatsView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controls the stats view, handling player and game selection to display coffee stats charts.
 */
public class StatsController  {
    private final StatsView statsView;
    private final StatsManager statsManager;
    protected final static String STATS = "stats";
    private final ActionListener playerChangedListener;
    private final ActionListener gameChangedListener;

    /**
     * @param appController the main app controller for navigation and panel registration
     * @param statsManager  the manager providing stats data
     */
    public StatsController(AppController appController, StatsManager statsManager) {
        this.statsManager = statsManager;

        this.statsView = new StatsView();
        appController.addCardToMainFrame(statsView, STATS);

        statsView.addBackListener(e -> appController.goBack());

        this.playerChangedListener = e -> handlePlayerSelection();
        this.gameChangedListener = e -> handleGameSelection();

        attachListeners();
    }

    /**
     * Attaches player and game selection listeners to the stats view combo boxes.
     */
    private void attachListeners() {
        statsView.addComboBoxListeners(playerChangedListener, gameChangedListener);
    }

    /**
     * Detaches player and game selection listeners from the stats view combo boxes.
     */
    private void detachListeners() {
        statsView.removeComboBoxListeners(playerChangedListener, gameChangedListener);
    }

    /**
     * Saves a stat entry for a given game and user.
     * @param gameId  the game ID
     * @param userId  the user ID
     * @param minute  the in-game minute of the stat
     * @param coffees the number of coffees recorded
     */
    public void saveStat(int gameId, int userId, double minute, double coffees) {
        statsManager.saveStat(gameId, userId, minute, coffees);
    }

    /**
     * Initializes and refreshes the stats view for the given logged-in user.
     * @param loggedUsername the username to pre-select, or null to show all players
     */
    public void onViewOpened(String loggedUsername) {
        detachListeners();

        statsView.setChart(new PaintChart());

        List<String> players = statsManager.getAllPlayers();
        statsView.setPlayersOptions(players);
        statsView.clearGamesOptions();
        statsView.setGameComboBoxEnabled(false);
        statsView.setTotalGamesCount(0);

        if (loggedUsername != null) {
            statsView.selectPlayer(loggedUsername);
        }

        attachListeners();

        if (loggedUsername != null) {
            handlePlayerSelection();
        }
    }

    /**
     * Updates the game combo box based on the currently selected player.
     */
    private void handlePlayerSelection() {
        String selectedPlayer = statsView.getSelectedPlayer();

        detachListeners();

        if (selectedPlayer != null) {
            List<Integer> games = statsManager.getGamesByPlayer(selectedPlayer);
            statsView.setGamesOptions(games);
            statsView.setTotalGamesCount(games.size());
        } else {
            statsView.clearGamesOptions();
            statsView.setGameComboBoxEnabled(false);
            statsView.setTotalGamesCount(0);
        }

        attachListeners();
    }

    /**
     * Updates the chart based on the currently selected player and game.
     */
    private void handleGameSelection() {
        String selectedPlayer = statsView.getSelectedPlayer();
        int selectedGame = statsView.getSelectedGameId();

        if (selectedPlayer != null && selectedGame != -1) {
            List<CoffeeStats> stats = statsManager.getStatsByUserAndGame(selectedPlayer, selectedGame);
            PaintChart chart = new PaintChart();
            chart.setStats(stats);
            statsView.setChart(chart);
        }
    }

    /**
     * Returns the last recorded minute for a given game.
     * @param gameId the game ID
     * @return the last minute recorded in that game
     */
    public double getLastMinute(int gameId) {
        return statsManager.getLastMinute(gameId);
    }

}