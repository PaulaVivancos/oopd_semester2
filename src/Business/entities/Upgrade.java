package Business.entities;

/**
 * Immutable definition of an upgrade that can be purchased during a game session
 * to permanently increase the production multiplier of a specific generator type.
 * Each upgrade can only be purchased once per game session.
 */
public class Upgrade {
    private final String name;
    private final double cost;
    private final double multiplier;
    private final String targetGeneratorName;

    /**
     * Creates a new upgrade definition with the given properties.
     * @param name                the unique display name of this upgrade
     * @param cost                the coffee cost required to purchase this upgrade
     * @param multiplier          the production multiplier applied to the target generator
     *                            when this upgrade is purchased, e.g. 2.0 to double production
     * @param targetGeneratorName the name of the generator type this upgrade affects,
     *                            matched case-insensitively against generator type names
     */
    public Upgrade(String name, double cost, double multiplier, String targetGeneratorName) {
        this.name = name;
        this.cost = cost;
        this.multiplier = multiplier;
        this.targetGeneratorName = targetGeneratorName;
    }

    /**
     * Returns the unique display name of this upgrade.
     * @return the upgrade name
     */
    public String getName() { return name; }

    /**
     * Returns the coffee cost required to purchase this upgrade.
     * @return the upgrade cost
     */
    public double getCost() { return cost; }

    /**
     * Returns the production multiplier applied to the target generator
     * when this upgrade is purchased.
     * @return the production multiplier
     */
    public double getMultiplier() { return multiplier; }

    /**
     * Returns the name of the generator type this upgrade targets.
     * Used to find and apply the multiplier to the correct generator in the game session.
     * @return the target generator type name
     */
    public String getTargetGeneratorName() { return targetGeneratorName; }
}
