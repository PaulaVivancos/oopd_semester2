package Business.entities;

import java.util.List;

public class CoffeeGeneratorFactory implements GeneratorFactory {
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