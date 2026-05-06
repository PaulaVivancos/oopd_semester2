package Presentation.controllers;

import Business.entities.Game;
import Business.managers.GameManager;
import Business.managers.UserManager;
import Presentation.views.GameView;
import Presentation.views.MenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.GameView.BUY_COFFEE;
import static Presentation.views.GameView.NEW_GAME;
import static Presentation.views.MenuView.GO_GAME;

public class GameController implements ActionListener {
    private final AppController appController;

    private final UserManager userManager;

    // views
    private final GameView gameView;

    protected static final String GAME = "game";

    private final GameManager gameManager;

    public GameController(AppController appController, GameManager gameManager, UserManager userManager) {
        this.appController = appController;

        this.userManager = userManager;

        gameView = new GameView();
        appController.addCardToMainFrame(gameView, GAME);


        // add listeners from game view
        gameView.addBuyListener(this);

        gameView.addNewGameListener(e -> handleNewGame());
        gameView.addLoadGameListener(e -> handleLoadGame());
        //gameView.addViewGameListener();

        gameView.addBackListener(e -> appController.goBack());

        this.gameManager = gameManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case GO_GAME:
                appController.switchCard(GAME);

                SwingUtilities.invokeLater(() -> {
                    gameView.showGamesPopUp(this);
                });
                break;

            case NEW_GAME:
                gameView.showNewGameDialog();
                break;

            case BUY_COFFEE:
                handleBuyCoffee();
                break;


        }
    }

    private void handleLoadGame () {
        int userId = userManager.getCurrentUser().getId();

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
        appController.showErrorPopUp("ERROR", "No unfinished game found, creating a new one...");
        gameManager.createNewGame(userId);
        setInitialConditions();
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
    }
}
