package Business;

import Business.entities.Game;
import Presentation.controllers.StatsController;

/**
 * Tracks and periodically saves gameplay statistics for a given game session.
 * Runs on a separate thread, saving the current coffee count every minute,
 * and also on significant events where the coffee count changes notably.
 */
public class StatsTracker implements Runnable {
    private final StatsController statsController;
    private final Game game;

    private volatile boolean running = true;
    private double lastSavedCoffees = 0;
    private final double minuteOffset;

    private final static int SLEEP_MILIS = 60000;
    private final static double SIXTY_SECONDS = 60;

    /**
     * Creates a new StatsTracker for the given game session.
     * Initializes the coffee baseline and the minute offset to continue
     * from where the last saved statistic left off.
     * @param statsController the controller used to persist statistics
     * @param game            the current game session being tracked
     * @param minuteOffset    the last recorded minute from previous sessions,
     *                        used to continue the timeline instead of restarting from zero
     */
    public StatsTracker(StatsController statsController, Game game, double minuteOffset) {
        this.statsController = statsController;
        this.game = game;
        this.lastSavedCoffees = game.getNumCoffees();
        this.minuteOffset = minuteOffset;
    }

    /**
     * Starts the tracking loop. Saves a stat entry immediately, then waits
     * one minute before saving again. Stops when {@link #stop()} is called
     * or the thread is interrupted.
     */
    @Override
    public void run() {
        while (running) {
            try {
                saveStat();
                Thread.sleep(SLEEP_MILIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Signals the tracker to stop running after the current iteration completes.
     */
    public void stop() {
        running = false;
    }

    /**
     * Called when a notable gameplay action occurs, such as clicking the coffee cup.
     * Saves a stat entry only if the coffee count has changed by at least 50
     * since the last save, avoiding unnecessary database writes.
     */
    public void onSignificantEvent() {
        double current = game.getNumCoffees();
        if (Math.abs(current - lastSavedCoffees) >= 50) {
            saveStat();
        }
    }

    /**
     * Saves the current coffee count and elapsed time to the database
     * via the StatsController, and updates the last saved coffee baseline.
     */
    private void saveStat() {
        double coffees = game.getNumCoffees();
        double minute = computeMinutes();

        lastSavedCoffees = coffees;

        statsController.saveStat(game.getGameId(), game.getUserId(), minute, coffees);
    }

    /**
     * Computes the number of minutes elapsed since the game session started,
     * adding the minute offset from previous sessions to maintain a continuous timeline.
     * @return the total elapsed time in minutes since the original game start,
     *         offset by the last recorded minute
     */
    private double computeMinutes() {
        long seconds = java.time.Duration.between(
                game.getStartTime(),
                java.time.LocalDateTime.now()
        ).getSeconds();

        return minuteOffset + (seconds / SIXTY_SECONDS);
    }
}