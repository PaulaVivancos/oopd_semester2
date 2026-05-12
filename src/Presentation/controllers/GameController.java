package Presentation.controllers;

import Business.entities.Game;
import Business.entities.GameListener;
import Business.managers.GameManager;
import Business.managers.UserManager;
import Presentation.views.GameView;
import Presentation.views.MenuView;
import Presentation.views.ShopView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.GameView.*;
import static Presentation.views.MenuView.GO_GAME;
import static Presentation.views.ShopView.*;

public class GameController implements ActionListener, GameListener {
    private final AppController appController;

    private final UserManager userManager;

    // views
    private final GameView gameView;
    private final ShopView shopView;

    protected static final String GAME = "game";
    protected static final String SHOP = "shop";

    private final GameManager gameManager;

    public GameController(AppController appController, GameManager gameManager, UserManager userManager) {
        this.appController = appController;

        this.userManager = userManager;


        shopView = new ShopView();
        appController.addCardToMainFrame(shopView, SHOP);
        gameView = new GameView();
        appController.addCardToMainFrame(gameView, GAME);


        // add listeners from game view
        gameView.addBuyListener(this);
        gameView.addShopListener(this);

        gameView.addNewGameListener(e -> handleNewGame());
        gameView.addLoadGameListener(e -> handleLoadGame());
        //gameView.addViewGameListener();

        gameView.addBackListener(e -> appController.goBack());

        //add listeners from shop view
        shopView.addGenBuyListener(this);
        shopView.addBackListener(e -> appController.goBack());

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

            case GO_SHOP:
                appController.switchCard(SHOP);
                break;

            case BUY_GEN1:
                handleBuyGenerator(1);
                break;
            case BUY_GEN2:
                handleBuyGenerator(2);
                break;
            case BUY_GEN3:
                handleBuyGenerator(3);
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
        gameManager.createNewGame(userId);

        Game newGame = gameManager.getCurrentGame();
        newGame.addListener(this);
        setInitialConditions();

        newGame.startGame();
    }


    private void setInitialConditions() {
        gameView.getJlCounter().setText("0");
    }

    public void onGameViewShown() {
        gameView.showGamesPopUp(this);
    }

    private void setGameInView(Game loadGame) {
       // gameView.getJlCounter().setText(String.valueOf(loadGame.getNumCoffees()));

        loadGame.addListener(this);
        gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());
        loadGame.startGame();
    }

    private void handleBuyCoffee() {
        gameManager.addCoffee();
        SwingUtilities.invokeLater(() ->{gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());});
    }

    private void handleBuyGenerator(int id) {
        gameManager.addGenerator(id);
    }

    @Override
    public void onCoffeeChange(double newAmount) {
        SwingUtilities.invokeLater(() ->{gameView.updateCounter(newAmount);});
    }
}
