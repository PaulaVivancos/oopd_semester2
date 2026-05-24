package Business.entities;

import java.util.List;

/**
 * Factory interface for creating the list of generator types available in the game.
 * Implementations of this interface define which generators exist and their base stats,
 * allowing different sets of generators to be provided depending on the game configuration.
 */
public interface GeneratorFactory {

    /**
     * Creates and returns the full list of generator types available in the game,
     * each containing the base cost, base production rate, cost increment, and image path.
     * @return a list of {@link GeneratorType} instances representing all available generators
     */
    List<GeneratorType> createGeneratorTypes();
}