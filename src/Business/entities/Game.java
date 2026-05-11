package Business.entities;

import java.lang.reflect.GenericArrayType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int gameId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double numCoffees;
    private boolean finished;
    private ArrayList<Generator> generators;
    private final List<Thread> generatorThreads = new ArrayList<>();

    private ArrayList<GameListener> listeners = new ArrayList<>();

    public Game(int userId, LocalDateTime startTime, double numCoffees, boolean finished, ArrayList<Generator> generators) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = null;
        this.numCoffees = numCoffees;
        this.finished = finished;
        this.generators = new ArrayList<>();
        //this.generators.add(new Generator(new GeneratorType("Espresso Machine", 10,   0.2,  1.07, null), this));
        //this.generators = generators;
    }

    public Game(int gameId, int userId, LocalDateTime startTime, LocalDateTime endTime,
                double numCoffees, boolean finished) {
        this.gameId = gameId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numCoffees = numCoffees;
        this.finished = finished;
        this.generators.add(new Generator(new GeneratorType("Espresso Machine", 10,   0.2,  1.07, null), this));
        //this.generators = generators;
    }


    public void addGenerator() {
        generators.add(new Generator(new GeneratorType("Espresso Machine", 10, 0.2, 1.07, null), this));
    }

    public void addCoffees(double amount) {
        synchronized (Game.class) {
            numCoffees += amount;
        }
        notifyUI(); // tell the view to refresh
    }
    public boolean spendCoffees(double amount) {
        synchronized (Game.class) {
            if (numCoffees < amount) return false;
            numCoffees -= amount;
        }
        notifyUI();
        return true;
    }

    public double getCoffees() {
        synchronized (Game.class) {
            return numCoffees;
        }
    }

    public void startGame() {
        for (Generator g : generators) {
            Thread t = new Thread(g, "Gen-" + g.getType().getName());
            generatorThreads.add(t);
            t.start();
        }
    }

    public void stopGame() {
        for (Generator g : generators) g.stop();
        for (Thread t : generatorThreads) t.interrupt();
    }

    public synchronized void addListener(GameListener listener){
        listeners.add(listener);
    }
    public synchronized void removeListener(GameListener listener){
        listeners.remove(listener);
    }

    public void notifyUI(){
        double current;
        synchronized (Game.class){
            current = numCoffees;
        }
        ArrayList<GameListener> snapshot;
        synchronized (listeners){
            snapshot = new ArrayList<GameListener>(listeners);
        }
        for(GameListener s : snapshot){
            s.onCoffeeChange(current);
        }
    }

    public int getGameId() { return gameId; }
    public int getUserId() { return userId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getNumCoffees() { return numCoffees; }
    public boolean isFinished() { return finished; }

    public void setGameId(int gameId) { this.gameId = gameId; }
    public void setNumCoffees(double numCoffees) { this.numCoffees = numCoffees; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setFinished(boolean finished) { this.finished = finished; }
}
