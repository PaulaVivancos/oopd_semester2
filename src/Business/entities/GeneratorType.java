package Business.entities;

/**
 * Immutable definition of a generator type, containing all the base stats
 * used to create and price generator instances during a game session.
 * Each generator type is defined once and shared across all game instances
 */
public class GeneratorType {

    private String name;
    private double baseCost;
    private double baseProduction;
    private String imagePath;
    private double costIncrement;

    /**
     * Creates a new generator type with the given information.
     * @param name           the display name of this generator type
     * @param baseCost       the initial purchase price of the first unit
     * @param baseProduction the number of coffees produced per second per unit owned
     * @param costIncrement  the multiplier applied to the price after each purchase,
     *                       e.g. 1.15 means each unit costs 15% more than the previous one
     * @param imagePath      the file path to the image representing this generator,
     *                       or null if no image is assigned
     */
    public GeneratorType(String name, double baseCost, double baseProduction,
                         double costIncrement, String imagePath) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseProduction = baseProduction;
        this.costIncrement = costIncrement;
        this.imagePath = imagePath;
    }

    /**
     * Returns the number of coffees this generator produces per second per unit owned.
     * @return the base production rate
     */
    public double getBaseProduction() {
        return baseProduction;
    }

    /**
     * Returns the initial purchase price of the first unit of this generator type.
     * @return the base cost
     */
    public double getBaseCost() {return baseCost;}

    /**
     * Returns the price multiplier applied after each unit is purchased.
     * @return the cost increment multiplier
     */
    public double getCostIncrement() {
        return costIncrement;
    }

    /**
     * Returns the file path to the image representing this generator type,
     * or null if no image has been assigned.
     * @return the image file path, or null
     */
    public String getImagePath() {return imagePath;}

    /**
     * Returns the display name of this generator type.
     * @return the generator name
     */
    public String getName() {
        return name;
    }
}
