package Business.entities;

public class CoffeeStats {
    private final double time;
    private final int coffees;

    public CoffeeStats(double time, int coffees) {
        this.time = time;
        this.coffees = coffees;
    }

    public double getTime() {
        return time;
    }

    public int getNumCoffees() {
        return coffees;
    }
}
