package Business.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Game(int userId, LocalDateTime startTime, double numCoffees, boolean finished, GeneratorFactory factory) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = null;
        this.numCoffees = numCoffees;
        this.finished = finished;
        for (GeneratorType type : factory.createGeneratorTypes()) {
            this.generators.add(new Generator(type, this));
        }        //this.generators = generators;
    }

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
        }           //this.generators = generators;
    }


    public void addGenerator(int id) {
        if (spendCoffees(generators.get(id).getCurrentPrice())) {
            generators.get(id).increaseQuantity();
        }
    }

    public ArrayList<Generator> getGenerators() {
        return generators;
    }

    public void addCoffees(double amount) {
        synchronized (Game.class) {
            numCoffees += amount;
        }
        notifyUI(); // tell the view to refresh
    }
    public boolean spendCoffees(double amount) {
        synchronized (Game.class) {
            if (numCoffees < amount) return false;
            numCoffees -= amount;
        }
        notifyUI();
        return true;
    }

    public double getCoffees() {
        synchronized (Game.class) {
            return numCoffees;
        }
    }

    public void startGame() {
        for (Generator g : generators) {
            Thread t = new Thread(g, "Gen-" + g.getType().getName());
            generatorThreads.add(t);
            t.start();
        }
    }

    public void stopGame() {
        for (Generator g : generators) g.stop();
        for (Thread t : generatorThreads) t.interrupt();
        generatorThreads.clear();
    }

    public synchronized void addListener(GameListener listener){
        listeners.add(listener);
    }
    public synchronized void removeListener(GameListener listener){
        listeners.remove(listener);
    }

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

    public int getGameId() { return gameId; }
    public int getUserId() { return userId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getNumCoffees() { return numCoffees; }
    public boolean isFinished() { return finished; }

    public void setGameId(int gameId) { this.gameId = gameId; }
    public void setNumCoffees(double numCoffees) { this.numCoffees = numCoffees; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setFinished(boolean finished) { this.finished = finished; }

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

    public boolean isUpgradePurchased(String upgradeName) {
        return purchasedUpgradeNames.contains(upgradeName);
    }

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

            // Map your database upgrade strings directly to your Generator Type names
            if (cleanName.contains("clerk")) {
                applyUpgrade("Gas Station Clerk", 2.0);
            } else if (cleanName.contains("barista") || cleanName.contains("starbucks")) {
                applyUpgrade("Starsbucks barista", 2.0); // Keep matching your factory spelling!
            } else if (cleanName.contains("veteran")) {
                applyUpgrade("365 Veteran", 2.0);
            } else if (cleanName.contains("pourover") || cleanName.contains("galactic")) {
                applyUpgrade("Galactic Pourover", 2.0);
            }
        }
    }
}
