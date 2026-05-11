package Business.entities;

public class Upgrade {
    private final String name;
    private final double cost;
    private final double multiplier;
    private final String targetGeneratorName;

    public Upgrade(String name, double cost, double multiplier, String targetGeneratorName) {
        this.name = name;
        this.cost = cost;
        this.multiplier = multiplier;
        this.targetGeneratorName = targetGeneratorName;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }
    public double getMultiplier() { return multiplier; }
    public String getTargetGeneratorName() { return targetGeneratorName; }
}
