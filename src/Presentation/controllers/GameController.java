package Presentation.controllers;

import Business.StatsTracker;
import Business.entities.Game;
import Business.managers.GameManager;
import Business.managers.UserManager;
import Presentation.views.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.GameView.BUY_COFFEE;
import static Presentation.views.GameView.NEW_GAME;
import static Presentation.views.MenuView.GO_GAME;

public class GameController implements ActionListener {
    private final AppController appController;
    private final StatsController statsController;

    private final GameManager gameManager;
    private final UserManager userManager;

    // views
    private final GameView gameView;

    private StatsTracker statsTracker;
    private Thread statsThread;

    private static final String UNFINISHED_GAME_WARN = "No unfinished game found, creating a new one...";
    private static final String CREATING_NEW_GAME = "Starting a new game!";
    private static final String ERROR = "ERROR";
    private static final String NEW_GAME = "New game creation";

    protected static final String GAME = "game";

    public GameController(AppController appController, GameManager gameManager, UserManager userManager, StatsController statsController) {
        this.appController = appController;
        this.statsController = statsController;

        this.gameManager = gameManager;
        this.userManager = userManager;

        gameView = new GameView();
        appController.addCardToMainFrame(gameView, GAME);


        // add listeners from game view
        gameView.addBuyListener(this);
        gameView.addNewGameListener(e -> handleNewGame());
        gameView.addLoadGameListener(e -> handleLoadGame());
        //gameView.addViewGameListener();
        gameView.addBackListener(e -> appController.goBack());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(GO_GAME)) {
            appController.switchCard(GAME);

            SwingUtilities.invokeLater(() -> {
                gameView.showGamesPopUp(this);
            });
        } else if (e.getActionCommand().equals(BUY_COFFEE)) {
            handleBuyCoffee();
        }
    }

    private void handleLoadGame () {
        int userId = userManager.getCurrentUser().getId();
        appController.showErrorPopUp(ERROR, UNFINISHED_GAME_WARN);

        if (gameManager.hasUnfinishedGame(userId)) {
            gameManager.loadGame(userId);
            Game loadGame = gameManager.getCurrentGame();
            setGameInView(loadGame);
        } else {
            handleNewGame();
        }
    }

    private void handleNewGame () {
        int userId = userManager.getCurrentUser().getId();
        appController.showInfoPopUp(NEW_GAME, CREATING_NEW_GAME);

        gameManager.createNewGame(userId);
        Game newGame = gameManager.getCurrentGame();

        setInitialConditions();
        startStatsTracker(newGame);
    }

    private void startStatsTracker(Game game) {
        if (statsTracker != null) {
            statsTracker.stop();
        }

        statsTracker = new StatsTracker(statsController, game);
        statsThread = new Thread(statsTracker);
        statsThread.start();
    }

    private void setInitialConditions() {
        gameView.getJlCounter().setText("0");
    }

    public void onGameViewShown() {
        gameView.showGamesPopUp(this);
    }

    private void setGameInView(Game loadGame) {
       // gameView.getJlCounter().setText(String.valueOf(loadGame.getNumCoffees()));
        gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());
    }

    private void handleBuyCoffee() {
        gameManager.addCoffee();
        gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());

        if (statsTracker != null) {
            statsTracker.onSignificantEvent();
        }
    }
}
