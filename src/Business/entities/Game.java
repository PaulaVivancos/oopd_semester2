package Business.entities;

import java.lang.reflect.GenericArrayType;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Game {

    private int gameId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double numCoffees;
    private boolean finished;
    private ArrayList<Generator> generators;

    public Game(int userId, LocalDateTime startTime, double numCoffees, boolean finished, ArrayList<Generator> generators) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = null;
        this.numCoffees = numCoffees;
        this.finished = finished;
        this.generators = generators;
    }

    public Game(int gameId, int userId, LocalDateTime startTime, LocalDateTime endTime,
                double numCoffees, boolean finished) {
        this.gameId = gameId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numCoffees = numCoffees;
        this.finished = finished;
        //this.generators = generators;
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
