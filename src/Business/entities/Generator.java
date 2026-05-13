package Business.entities;

import Business.managers.GameManager;

import java.util.ArrayList;
import java.util.logging.Level;

public class Generator implements Runnable{
    private int id;
    private int quantity;
    private GeneratorType type;
    private double upgradeMultiplier = 1.0;
    private double currentPrice;

    private Game game;
    private volatile boolean running = true;
    private volatile boolean paused = false;

    public Generator(GeneratorType type, Game game) {
        this.type = type;
        this.quantity = 0;
        this.game = game;
        this.currentPrice = type.getBaseCost();
    }

    public void increaseQuantity(){
        this.quantity++;
        this.currentPrice *= type.getCostIncrement();
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

    public void applyMultiplier(double m) { upgradeMultiplier *= m; }
    public GeneratorType getType() { return type; }

    public int getQuantity() {return quantity;}
    public double getCurrentPrice() {return currentPrice;}
}
