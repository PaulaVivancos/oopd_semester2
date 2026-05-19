package Business;

import Business.entities.Game;
import Presentation.controllers.StatsController;

public class StatsTracker implements Runnable {
    private final StatsController statsController;
    private final Game game;
    private final int gameId;

    private boolean running = true;
    private int lastSavedCoffees = 0;

    private final static int SLEEP_MILIS = 60000;

    public StatsTracker(StatsController statsController, Game game) {
        this.statsController = statsController;
        this.game = game;
        this.gameId = game.getGameId();
    }

    @Override
    public void run() {
        while (running) {
            try {
                saveStat();
                Thread.sleep(SLEEP_MILIS);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void onSignificantEvent() {
        int current = game.getNumCoffees();
        if (Math.abs(current - lastSavedCoffees) >= 50) {
            saveStat();
        }
    }

    private void saveStat() {
        int coffees = game.getNumCoffees();
        double minute = computeMinutes();

        lastSavedCoffees = coffees;

        statsController.saveStat(gameId, minute, coffees);
    }

    private double computeMinutes() {
        long seconds = java.time.Duration.between(
                game.getStartTime(),
                java.time.LocalDateTime.now()
        ).getSeconds();

        return seconds / 60.0;
    }
}
