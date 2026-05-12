package Business.entities;

import java.util.List;

public interface GeneratorFactory {
    List<GeneratorType> createGeneratorTypes();
}