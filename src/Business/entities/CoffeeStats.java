package Business.entities;

/**
 * Immutable data class representing a single statistics entry for a game session,
 * storing the elapsed time and the coffee count recorded at that moment.
 * Used to populate the statistics chart in the stats view.
 */
public class CoffeeStats {
    private final double time;
    private final int coffees;

    /**
     * Creates a new CoffeeStats entry with the given time and coffee count.
     * @param time    the elapsed time in minutes at which this entry was recorded
     * @param coffees the number of coffees accumulated at that point in time
     */
    public CoffeeStats(double time, int coffees) {
        this.time = time;
        this.coffees = coffees;
    }

    /**
     * Returns the elapsed time in minutes at which this entry was recorded.
     * @return the elapsed time in minutes
     */
    public double getTime() {
        return time;
    }

    /**
     * Returns the number of coffees recorded at this point in time.
     * @return the coffee count
     */
    public int getNumCoffees() {
        return coffees;
    }
}
