package Presentation.views;

import Presentation.controllers.AppController;
import Presentation.controllers.UserController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // ALL VIEWS
    private LoginView loginView;
    private RegisterView registerView;
    private MenuView menuView;
    private GameView gameView;
    private ConfigView configView;
    private StatsView statsView;

    private final int WIDTH_MAIN_FRAME = 1150;
    private final int HEIGHT_MAIN_FRAME = 800;

    public MainFrame(AppController appController) {

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create views
        loginView = new LoginView();
        registerView = new RegisterView();
        menuView = new MenuView();
        gameView = new GameView();
        configView = new ConfigView();
        statsView = new StatsView();
        // ...

        // Create controllers and passing as the navigator
        new UserController(appController, loginView, registerView, gameView);

        // Add cards
        mainPanel.add(loginView, "login");
        mainPanel.add(registerView, "register");
        mainPanel.add(menuView, "menu");
        mainPanel.add(gameView, "game");
        mainPanel.add(statsView, "stats");
        mainPanel.add(configView, "config");

        loginView.getSingUpButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "register");
        });

        registerView.getLogInButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "login");
        });

        menuView.getConfigButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "config");
        });

        menuView.getStatsButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "stats");
        });

        menuView.addPlayListener(e ->
                menuView.showGamesPopUp(
                        e1 -> {cardLayout.show(mainPanel, "game"); gameView.showNewGameDialog(); },
                        e2 -> cardLayout.show(mainPanel, "game"),
                        e3 -> cardLayout.show(mainPanel, "game")
                )
        );


        setTitle("CoffeeClicker");
        setIconImage(new ImageIcon("src/Presentation/Images/coffee_cup.png").getImage());

        add(mainPanel);
        switchCard("login"); //start on login
    }

    public void switchCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    private void configureFrame() {
        setSize(WIDTH_MAIN_FRAME, HEIGHT_MAIN_FRAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showMainFrame() {
        configureFrame();
        setVisible(true);
    }

}
