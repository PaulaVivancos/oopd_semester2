package Presentation.controllers;

import Business.managers.StatsManager;
import Presentation.views.MenuView;
import Presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.MenuView.GO_STATS;

public class StatsController implements ActionListener {
    private final AppController appController;

    // views
    private final StatsView statsView;

    protected final static String STATS = "stats";

    private final StatsManager statsManager;

    public StatsController(AppController appController, StatsManager statsManager) {
        this.appController = appController;
        this.statsManager = statsManager;

        statsView = new StatsView();
        appController.addCardToMainFrame(statsView, STATS);

        // add listeners from stats view
        statsView.addBackListener(e -> appController.goBack());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(GO_STATS)) {
            appController.switchCard(STATS);
        }
    }
}
