package Business.entities;

import java.time.LocalDateTime;

public class Game {

    private int gameId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double numCoffees;
    private boolean finished;

    public Game(int userId, LocalDateTime startTime, double numCoffees, boolean finished) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = null;
        this.numCoffees = numCoffees;
        this.finished = finished;
    }

    public Game(int gameId, int userId, LocalDateTime startTime, LocalDateTime endTime, double numCoffees, boolean finished) {
        this.gameId = gameId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numCoffees = numCoffees;
        this.finished = finished;
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
