package Business.entities;

/**
 * Represents a single generator owned by the player in a game session.
 * Each generator runs on its own thread, automatically producing coffees
 * every second based on its quantity, base production rate, and any applied upgrades.
 */
public class Generator implements Runnable{
    private int quantity;
    private GeneratorType type;
    private double upgradeMultiplier = 1.0;
    private double currentPrice;

    private Game game;
    private volatile boolean running = true;
    private volatile boolean paused = false;

    private static final int SECOND_MILIS = 1000;

    /**
     * Creates a new generator of the given type associated with the given game session.
     * Initializes the quantity to zero and the current price to the type's base cost.
     * @param type the generator type defining the name, base cost, production rate, and cost increment
     * @param game the game session this generator belongs to, used to add produced coffees
     */
    public Generator(GeneratorType type, Game game) {
        this.type = type;
        this.quantity = 0;
        this.game = game;
        this.currentPrice = type.getBaseCost();
    }

    /**
     * Increases the owned quantity of this generator by one and updates the current
     * purchase price by multiplying it by the type's cost increment multiplier.
     * Called each time the player successfully buys one unit of this generator.
     */
    public void increaseQuantity(){
        this.quantity++;
        this.currentPrice *= type.getCostIncrement();
    }

    /**
     * Runs the generator production loop on a separate thread.
     * Every second, if the generator is not paused and has at least one unit owned,
     * it calculates the total production as quantity * base production * upgrade multiplier
     * and adds it to the game's coffee count.
     * Stops cleanly when interrupted or when {@link #stop()} is called.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(SECOND_MILIS); // tick every second
                if (!paused && quantity > 0) {
                    double produced = quantity * type.getBaseProduction() * upgradeMultiplier;
                    game.addCoffees(produced);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Signals the generator thread to stop running after the current tick completes.
     */
    public void stop() { running = false; }

    /**
     * Applies a production multiplier to this generator by multiplying the current
     * upgrade multiplier by the given value. Called when an upgrade is purchased
     * that targets this generator type.
     * @param m the multiplier to apply, e.g. 2.0 to double production
     */
    public void applyMultiplier(double m) { upgradeMultiplier *= m; }

    /**
     * Returns the type definition of this generator, containing its name,
     * base cost, production rate, and cost increment.
     * @return the generator type
     */
    public GeneratorType getType() { return type; }

    /**
     * Returns the number of units of this generator currently owned by the player.
     * @return the owned quantity
     */
    public int getQuantity() {return quantity;}

    /**
     * Returns the current purchase price of this generator, which increases
     * each time a unit is bought according to the type's cost increment multiplier.
     * @return the current price
     */
    public double getCurrentPrice() {return currentPrice;}

    /**
     * Returns the current upgrade multiplier applied to this generator's production.
     * Starts at 1.0 and increases as upgrades are purchased.
     * @return the current upgrade multiplier
     */
    public double getUpgradeMultiplier() {return upgradeMultiplier;}

    /**
     * Sets the current quantity owned of this generator.
     * Used primarily when loading a saved game from the database.
     * @param quantity The number of generators loaded from the database state.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
