package Business.managers;

import Persistence.StatsDAO;

public class StatsManager {
    private final StatsDAO statsDAO;

    public StatsManager(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }
}
