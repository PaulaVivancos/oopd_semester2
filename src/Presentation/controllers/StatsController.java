package Presentation.controllers;

import Business.managers.StatsManager;
import Business.entities.CoffeeStats;
import Presentation.PaintChart;
import Presentation.views.StatsView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class StatsController  {
    private final AppController appController;
    private final StatsView statsView;
    private final StatsManager statsManager;

    protected final static String STATS = "stats";

    private final ActionListener playerChangedListener;
    private final ActionListener gameChangedListener;

    public StatsController(AppController appController, StatsManager statsManager) {
        this.appController = appController;
        this.statsManager = statsManager;

        this.statsView = new StatsView();
        appController.addCardToMainFrame(statsView, STATS);

        statsView.addBackListener(e -> appController.goBack());

        this.playerChangedListener = e -> handlePlayerSelection();
        this.gameChangedListener = e -> handleGameSelection();

        attachListeners();
    }

    private void attachListeners() {
        statsView.addComboBoxListeners(playerChangedListener, gameChangedListener);
    }

    private void detachListeners() {
        statsView.removeComboBoxListeners(playerChangedListener, gameChangedListener);
    }

    public void saveStat(int gameId, int userId, double minute, double coffees) {
        statsManager.saveStat(gameId, userId, minute, coffees);
    }

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

    public double getLastMinute(int gameId) {
        return statsManager.getLastMinute(gameId);
    }




}