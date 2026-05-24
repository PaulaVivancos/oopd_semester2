package Business.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single game session, managing the coffee count, owned generators,
 * purchased upgrades, and notifying registered listeners when the game state changes.
 * A game can be started, paused, saved, loaded, and finished across multiple sessions.
 */
public class Game {

    private int gameId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double numCoffees;
    private boolean finished;
    private final ArrayList<Generator> generators = new ArrayList<>();
    private final List<Thread> generatorThreads = new ArrayList<>();
    private GeneratorFactory factory = new CoffeeGeneratorFactory();

    private ArrayList<GameListener> listeners = new ArrayList<>();
    private final ArrayList<String> purchasedUpgradeNames = new ArrayList<>();

    /**
     * Creates a new game session from scratch for the given user.
     * Initializes all generators from the provided factory with zero quantity.
     * @param userId     the ID of the user who owns this game
     * @param startTime  the time at which this game session started
     * @param numCoffees the initial coffee count, typically zero for a new game
     * @param finished   whether this game session is already marked as finished
     * @param factory    the factory used to create the generator types for this game
     */
    public Game(int userId, LocalDateTime startTime, double numCoffees, boolean finished, GeneratorFactory factory) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = null;
        this.numCoffees = numCoffees;
        this.finished = finished;
        for (GeneratorType type : factory.createGeneratorTypes()) {
            this.generators.add(new Generator(type, this));
        }
    }

    /**
     * Reconstructs a game session loaded from the database.
     * Initializes all generators from the default factory with zero quantity,
     * which are then populated with saved quantities by the DAO after construction.
     * @param gameId     the unique identifier of this game session in the database
     * @param userId     the ID of the user who owns this game
     * @param startTime  the time at which this game session originally started
     * @param endTime    the time at which this game session ended, or null if still ongoing
     * @param numCoffees the coffee count at the time this game was last saved
     * @param finished   whether this game session is marked as finished
     */
    public Game(int gameId, int userId, LocalDateTime startTime, LocalDateTime endTime,
                double numCoffees, boolean finished) {
        this.gameId = gameId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numCoffees = numCoffees;
        this.finished = finished;
        for (GeneratorType type : factory.createGeneratorTypes()) {
            this.generators.add(new Generator(type, this));
        }
    }

    /**
     * Attempts to purchase one unit of the generator at the given index.
     * Deducts the current price from the coffee count if affordable,
     * then increases the generator's quantity and updates its price.
     * @param id the index of the generator to purchase in the generators list
     */
    public void addGenerator(int id) {
        if (spendCoffees(generators.get(id).getCurrentPrice())) {
            generators.get(id).increaseQuantity();
        }
    }

    /**
     * Returns the list of all generators in this game session,
     * including those with zero quantity.
     * @return the full list of generators
     */
    public ArrayList<Generator> getGenerators() {
        return generators;
    }

    /**
     * Adds the given amount of coffees to the current count in a thread-safe manner,
     * then notifies all registered UI listeners of the updated count.
     * @param amount the number of coffees to add
     */
    public void addCoffees(double amount) {
        synchronized (Game.class) {
            numCoffees += amount;
        }
        notifyUI();
    }

    /**
     * Attempts to spend the given amount of coffees in a thread-safe manner.
     * If the current count is sufficient, deducts the amount and notifies listeners.
     * @param amount the number of coffees to spend
     * @return true if the coffees were successfully spent, false if the balance was insufficient
     */
    public boolean spendCoffees(double amount) {
        synchronized (Game.class) {
            if (numCoffees < amount) return false;
            numCoffees -= amount;
        }
        notifyUI();
        return true;
    }

    /**
     * Returns the current coffee count in a thread-safe manner.
     * @return the current number of coffees
     */
    public double getCoffees() {
        synchronized (Game.class) {
            return numCoffees;
        }
    }

    /**
     * Starts all generator threads, allowing them to produce coffees automatically
     * once per second based on their quantity and production rate.
     */
    public void startGame() {
        for (Generator g : generators) {
            Thread t = new Thread(g, "Gen-" + g.getType().getName());
            generatorThreads.add(t);
            t.start();
        }
    }

    /**
     * Stops all generator threads and clears the thread list,
     * halting all automatic coffee production.
     */
    public void stopGame() {
        for (Generator g : generators) g.stop();
        for (Thread t : generatorThreads) t.interrupt();
        generatorThreads.clear();
    }

    /**
     * Registers a listener to be notified whenever the coffee count changes.
     * @param listener the listener to add
     */
    public synchronized void addListener(GameListener listener){
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners with the current coffee count.
     * Takes a snapshot of the listener list to avoid concurrency issues
     * during iteration.
     */
    public void notifyUI(){
        double current;
        synchronized (Game.class){
            current = numCoffees;
        }
        ArrayList<GameListener> snapshot;
        synchronized (listeners){
            snapshot = new ArrayList<GameListener>(listeners);
        }
        for(GameListener s : snapshot){
            s.onCoffeeChange(current);
        }
    }

    /**
     * Returns the unique identifier of this game session.
     * @return the game ID
     */
    public int getGameId() { return gameId; }

    /**
     * Returns the ID of the user who owns this game session.
     * @return the user ID
     */
    public int getUserId() { return userId; }

    /**
     * Returns the time at which this game session originally started.
     * @return the start time
     */
    public LocalDateTime getStartTime() { return startTime; }

    /**
     * Returns the time at which this game session ended, or null if still ongoing.
     * @return the end time, or null
     */
    public LocalDateTime getEndTime() { return endTime; }

    /**
     * Returns the current coffee count.
     * @return the current number of coffees
     */
    public double getNumCoffees() { return numCoffees; }

    /**
     * Returns whether this game session has been marked as finished.
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() { return finished; }

    /**
     * Sets the unique identifier of this game session, typically after saving to the database.
     * @param gameId the new game ID
     */
    public void setGameId(int gameId) { this.gameId = gameId; }

    /**
     * Sets the current coffee count directly.
     * Used when loading a saved game state from the database.
     * @param numCoffees the coffee count to set
     */
    public void setNumCoffees(double numCoffees) { this.numCoffees = numCoffees; }

    /**
     * Sets the end time of this game session.
     * @param endTime the time at which the session ended
     */
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    /**
     * Marks this game session as finished or unfinished.
     * @param finished true to mark the game as finished, false otherwise
     */
    public void setFinished(boolean finished) { this.finished = finished; }

    /**
     * Applies a production multiplier to the generator matching the given name.
     * The comparison is case-insensitive and trims whitespace.
     * Does nothing if the generator name is null or no matching generator is found.
     * @param generatorName the name of the generator type to apply the multiplier to
     * @param multiplier    the multiplier to apply to the generator's current production rate
     */
    public void applyUpgrade(String generatorName, double multiplier) {
        if (generatorName == null || this.generators == null) return;

        for (Generator gen : this.generators) {
            if (gen.getType() != null && gen.getType().getName() != null) {
                if (gen.getType().getName().equalsIgnoreCase(generatorName.trim())) {
                    gen.applyMultiplier(multiplier);
                }
            }
        }
    }

    /**
     * Returns the list of names of all upgrades purchased in the current game session.
     * @return an ArrayList containing the names of purchased upgrades.
     */
    public ArrayList<String> getPurchasedUpgradeNames() {
        return this.purchasedUpgradeNames;
    }

    /**
     * Returns whether the upgrade with the given name has already been purchased
     * in this game session.
     * @param upgradeName the name of the upgrade to check
     * @return true if the upgrade has been purchased, false otherwise
     */
    public boolean isUpgradePurchased(String upgradeName) {
        return purchasedUpgradeNames.contains(upgradeName);
    }

    /**
     * Records the given upgrade name as purchased in this game session.
     * @param upgradeName the name of the upgrade to mark as purchased
     */
    public void markUpgradePurchased(String upgradeName) {
        purchasedUpgradeNames.add(upgradeName);
    }

    /**
     * Iterates through all previously purchased upgrade names, looks up their definition records,
     * and reapplies their production modifiers directly to the generator running threads.
     */
    public void reapplyPurchasedUpgrades() {
        if (this.purchasedUpgradeNames == null) return;

        for (String upgradeName : this.purchasedUpgradeNames) {
            if (upgradeName == null) continue;

            String cleanName = upgradeName.trim().toLowerCase();

            if (cleanName.contains("clerk")) {
                applyUpgrade("Gas Station Clerk", 2.0);
            } else if (cleanName.contains("barista") || cleanName.contains("starbucks")) {
                applyUpgrade("Starsbucks barista", 2.0);
            } else if (cleanName.contains("veteran")) {
                applyUpgrade("365 Veteran", 2.0);
            } else if (cleanName.contains("pourover") || cleanName.contains("galactic")) {
                applyUpgrade("Galactic Pourover", 2.0);
            }
        }
    }
}
