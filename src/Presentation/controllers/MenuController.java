package Presentation.controllers;

import Presentation.views.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.controllers.AuthController.CONFIG;
import static Presentation.controllers.GameController.GAME;
import static Presentation.controllers.StatsController.STATS;
import static Presentation.views.MenuView.*;

/**
 * Handles navigation from the main menu to other sections of the application,
 * including the game, stats, config, and logout.
 */
public class MenuController implements ActionListener {
    private final AppController appController;
    private final AuthController authController;
    private final GameController gameController;
    private final StatsController statsController;

    private final MenuView menuView;

    private static final String MENU = "menu";

    /**
     * Routes menu action events to the appropriate view or handler.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getActionCommand().equals(GO_GAME)) {
           appController.switchCard(GAME);
           gameController.onGameViewShown();
       } else if (e.getActionCommand().equals(GO_STATS)) {
           statsController.onViewOpened(authController.getLoggedInUsername());
           appController.switchCard(STATS);
       } else if (e.getActionCommand().equals(GO_CONFIG)) {
           appController.switchCard(CONFIG);
       } else if (e.getActionCommand().equals(LOGOUT)) {
           authController.handleLogout();
       }
    }

    /**
     * Initializes the menu view, registers it with the main frame,
     * and attaches this controller as its event listener.
     * @param appController the central app controller used for navigation
     * @param authController used to handle logout actions
     * @param gameController used to trigger game initialization when entering the game view
     * @param statsController used for stats view setup when navigation is implemented
     */
    public MenuController(AppController appController, AuthController authController, GameController gameController, StatsController statsController) {
        this.appController = appController;
        this.authController = authController;
        this.gameController = gameController;
        this.statsController = statsController;

        menuView = new MenuView();
        appController.addCardToMainFrame(menuView, MENU);

        menuView.addAuthListeners(this);
        menuView.addGameListener(this);
        menuView.addStatsListener(this);
    }
}
