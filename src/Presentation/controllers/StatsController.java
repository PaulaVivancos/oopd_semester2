package Presentation.controllers;

import Business.managers.StatsManager;
import Business.entities.CoffeeStats;
import Presentation.PaintChart;
import Presentation.views.StatsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Colección genérica correcta

import static Presentation.views.MenuView.GO_STATS;

public class StatsController implements ActionListener {
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

    public void saveStat(int gameId, double minute, double coffees) {
        statsManager.saveStat(gameId, minute, coffees);
    }

    public void onViewOpened() {
        detachListeners();

        statsView.setChart(new PaintChart());

        List<Integer> players = statsManager.getAllPlayers();
        statsView.setPlayersOptions(players);

        statsView.clearGamesOptions();
        statsView.setGameComboBoxEnabled(false);
        statsView.setTotalGamesCount(0);

        attachListeners();
    }

    private void handlePlayerSelection() {
        int selectedPlayer = statsView.getSelectedPlayerId();

        detachListeners(); // Always detach before altering ComboBox models to prevent loops

        if (selectedPlayer != -1) {
            List<Integer> games = statsManager.getGamesByPlayer(selectedPlayer);

            statsView.setGamesOptions(games);

            statsView.setGameComboBoxEnabled(true);

            statsView.setTotalGamesCount(games.size());
        } else {
            statsView.clearGamesOptions();
            statsView.setGameComboBoxEnabled(false);
            statsView.setTotalGamesCount(0);
        }

        attachListeners();
    }

    private void handleGameSelection() {
        int selectedPlayer = statsView.getSelectedPlayerId();
        int selectedGame = statsView.getSelectedGameId();

        if (selectedPlayer != -1 && selectedGame != -1) {
            List<CoffeeStats> stats = statsManager.getStatsByUserAndGame(selectedPlayer, selectedGame);


            PaintChart chart = new PaintChart();
            chart.setStats(stats);
            statsView.setChart(chart);


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(GO_STATS)) {
            appController.switchCard(STATS);
            SwingUtilities.invokeLater(() -> {
                onViewOpened();
            });
        }
    }
}