package Business.entities;

public class GeneratorType {

    private String name;
    private double baseCost;
    private double baseProduction;
    private String imagePath;
    private double costIncrement;

    public GeneratorType(String name, double baseCost, double baseProduction,
                         double costIncrement, String imagePath) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseProduction = baseProduction;
        this.costIncrement = costIncrement;
        this.imagePath = imagePath;
    }

    public double getBaseProduction() {
        return baseProduction;
    }

    public String getName() {
        return name;
    }
}
