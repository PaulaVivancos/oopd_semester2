package Business.entities;

/**
 * Listener interface for receiving notifications when the coffee count changes in a game session.
 * Classes that need to react to coffee count updates, such as UI controllers,
 * should implement this interface and register themselves with the relevant Game instance.
 */
public interface GameListener {

    /**
     * Called whenever the coffee count in the game changes, either by clicking
     * the coffee cup or by generator production.
     * @param newAmount the updated coffee count after the change
     */
    void onCoffeeChange(double newAmount);
}
