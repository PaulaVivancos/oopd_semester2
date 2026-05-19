package Presentation.controllers;

import Business.StatsTracker;
import Business.entities.Game;
import Business.entities.*;
import Business.managers.GameManager;
import Business.managers.UserManager;
import Presentation.views.GameView;
import Presentation.views.MenuView;
import Presentation.views.UpgradeView;
import Presentation.views.ShopView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Presentation.views.GameView.*;
import static Presentation.views.MenuView.GO_GAME;
import static Presentation.views.ShopView.*;

public class GameController implements ActionListener, GameListener {
    private final AppController appController;
    private final StatsController statsController;

    private final GameManager gameManager;
    private final UserManager userManager;

    public static int NUM_GENERATORS = 4;

    // views
    private final GameView gameView;
    private final UpgradeView upgradeView;
    private final ShopView shopView;

    private StatsTracker statsTracker;
    private Thread statsThread;

    private static final String UNFINISHED_GAME_WARN = "No unfinished game found, creating a new one...";
    private static final String CREATING_NEW_GAME = "Starting a new game!";
    private static final String ERROR = "ERROR";
    private static final String NEW_GAME = "New game creation";

    protected static final String GAME = "game";
    protected static final String UPGRADE = "upgrade";
    protected static final String SHOP = "shop";

    public GameController(AppController appController, GameManager gameManager, UserManager userManager, StatsController statsController) {
        this.appController = appController;
        this.statsController = statsController;

        this.gameManager = gameManager;
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
        gameView.getJbUpg().addActionListener(e -> {
            appController.switchCard(UPGRADE);
            if (gameManager.getCurrentGame() != null)
                refreshUpgradeView(gameManager.getCurrentGame().getCoffees());
        });

        // upgrade view
        upgradeView = new UpgradeView();
        appController.addCardToMainFrame(upgradeView, UPGRADE);
        upgradeView.addBackListener(e -> appController.goBack());
        upgradeView.addBuyUpgradeListener(this);


        shopView.addBackListener(e -> appController.goBack());


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(GO_GAME)) {
            appController.switchCard(GAME);

            SwingUtilities.invokeLater(() -> {
                gameView.showGamesPopUp(this);
            });
        } else if (cmd.equals(BUY_COFFEE)) {
            handleBuyCoffee();

        } else if (cmd.equals(GO_SHOP)) {
            appController.switchCard(SHOP);

        } else {
            try {
                int i = Integer.parseInt(cmd);
                if (i >= 0 && i < UpgradeView.UPGRADES.length && gameManager.buyUpgrade(UpgradeView.UPGRADES[i])) {
                    double coffees = gameManager.getCurrentGame().getCoffees();
                    SwingUtilities.invokeLater(() -> { gameView.updateCounter(coffees); refreshUpgradeView(coffees); });
                }
            } catch (NumberFormatException ignored) { }
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

    private void handleNewGame() {
        int userId = userManager.getCurrentUser().getId();
        appController.showInfoPopUp(NEW_GAME, CREATING_NEW_GAME);

        gameManager.createNewGame(userId);
        Game newGame = gameManager.getCurrentGame();

        newGame.addListener(this);
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
        //newGame.startGame();

        // now game exists, safe to populate and wire
        SwingUtilities.invokeLater(() -> {
            shopView.initGenerators(gameManager.getGeneratorTypes());
            attachGenListeners();
        });
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
        gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());

        if (statsTracker != null) {
            statsTracker.onSignificantEvent();
        }
        SwingUtilities.invokeLater(() ->{gameView.updateCounter(gameManager.getCurrentGame().getNumCoffees());});
    }

    private void handleBuyGenerator(int id) {
        gameManager.addGenerator(id);
    }

    private void attachGenListeners() {
        for (int i = 0; i < gameManager.getGeneratorTypes().size(); i++) {
            final int index = i;
            shopView.addGenBuyListener(index, e -> {
                handleBuyGenerator(index);
            });
        }
    }

    @Override
    public void onCoffeeChange(double newAmount) {
        SwingUtilities.invokeLater(() -> {
            gameView.updateCounter(newAmount);
            refreshUpgradeView(newAmount);
            refreshShopView(newAmount);
        });
    }

    private void refreshUpgradeView(double currentCoffees) {
        Game game = gameManager.getCurrentGame();
        for (int i = 0; i < UpgradeView.UPGRADES.length; i++) {
            Upgrade upg = UpgradeView.UPGRADES[i];
            boolean purchased = game != null && game.isUpgradePurchased(upg.getName());
            boolean canAfford = currentCoffees >= upg.getCost();
            upgradeView.updateUpgradeRow(i, canAfford, purchased);
        }
    }

    private void refreshShopView(double currentCoffees) {
        Game game = gameManager.getCurrentGame();
        for (int i = 0; i < NUM_GENERATORS; i++) {
            Generator gen = game.getGenerators().get(i);
            boolean canAfford = currentCoffees >= gen.getCurrentPrice();
            shopView.updateGeneratorRow(i,gen.getCurrentPrice(),gen.getQuantity(),canAfford);
        }
    }
}
