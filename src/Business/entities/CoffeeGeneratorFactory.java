package Business.entities;

import java.util.List;

public class CoffeeGeneratorFactory implements GeneratorFactory {
    @Override
    public List<GeneratorType> createGeneratorTypes() {
        return List.of(
                new GeneratorType("Gas Station Clerk", 10, 0.2, 1.07, null),
                new GeneratorType("Starsbucks barista", 150, 1.0, 1.15, null),
                new GeneratorType("365 Veteran", 2000, 15.0, 1.12, null)
        );
    }
}