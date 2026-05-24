package Business.entities;

import java.util.List;

/**
 * Concrete implementation of {@link GeneratorFactory} that creates the four
 * coffee generator types available in the game, each with their own base cost,
 * production rate, and cost increment multiplier.
 */
public class CoffeeGeneratorFactory implements GeneratorFactory {

    /**
     * Creates and returns the list of coffee generator types available in the game.
     * The generators are ordered by increasing cost and production power:
     * Vivari Life, Starsbucks barista, 365 Veteran, and Fornet Supreme.
     * @return a list of {@link GeneratorType} instances representing all available generators
     */
    @Override
    public List<GeneratorType> createGeneratorTypes() {
        return List.of(
                new GeneratorType("Vivari Life", 10, 0.2, 1.07, null),
                new GeneratorType("Starsbucks barista", 150, 5.0, 1.10, null),
                new GeneratorType("365 Veteran", 1000, 30.0, 1.12, null),
                new GeneratorType("Fornet Supreme", 100000,1000,2.0,null)
        );
    }
}