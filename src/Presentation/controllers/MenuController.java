package Presentation.controllers;

import Presentation.views.LoginView;
import Presentation.views.MenuView;
import Presentation.views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.controllers.AuthController.CONFIG;
import static Presentation.controllers.GameController.GAME;
import static Presentation.controllers.StatsController.STATS;
import static Presentation.views.MenuView.*;

public class MenuController implements ActionListener {
    private final AppController appController;
    private final AuthController authController;
    private final GameController gameController;
    private final StatsController statsController;

    private final MenuView menuView;

    private static final String MENU = "menu";

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getActionCommand().equals(GO_GAME)) {
           appController.switchCard(GAME);
           gameController.onGameViewShown();
       } else if (e.getActionCommand().equals(GO_STATS)) {
           appController.switchCard(STATS);
           //statsController.onStatsViewShown();
       } else if (e.getActionCommand().equals(GO_CONFIG)) {
           appController.switchCard(CONFIG);
       } else if (e.getActionCommand().equals(LOGOUT)) {
           authController.handleLogout();
       }
    }

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
