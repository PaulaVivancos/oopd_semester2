package Business.entities;

import Business.managers.GameManager;

import java.util.ArrayList;
import java.util.logging.Level;

public class Generator implements Runnable{
    private int id;
    private int quantity;
    private GeneratorType type;
    private double upgradeMultiplier = 1.0;

    private Game game;
    private volatile boolean running = true;
    private volatile boolean paused = false;

    public Generator(GeneratorType type, Game game) {
        this.type = type;
        this.quantity = 1;// TODO remove this when buying gens implemented
        this.game = game;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // tick every second
                if (!paused && quantity > 0) {
                    double produced = quantity * type.getBaseProduction() * upgradeMultiplier;
                    game.addCoffees(produced);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    public void stop() { running = false; }
    public void pause() { paused = true; }
    public void resume() { paused = false; }

    public GeneratorType getType() {
        return type;
    }
}
