package Presentation.views;

import Presentation.controllers.AppController;
import Presentation.controllers.AuthController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainFrame extends JFrame {
    private LinkedList<String> viewsStack = new LinkedList<>();
    private CardLayout cardLayout;
    private static JPanel mainPanel;

//    private LoginView loginView;
//    private RegisterView registerView;
//    private MenuView menuView;
//    private GameView gameView;
//    private ConfigView configView;
//    private StatsView statsView;
//    private ForgotPasswordView forgotPasswordView;

    private final int WIDTH_MAIN_FRAME = 1150;
    private final int HEIGHT_MAIN_FRAME = 800;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

//        loginView = new LoginView();
//        registerView = new RegisterView();
//        menuView = new MenuView();
//        gameView = new GameView();
//        configView = new ConfigView();
//        statsView = new StatsView();
//        forgotPasswordView = new ForgotPasswordView();
        // ...


        // TODO: Create a method to add cards.
//        mainPanel.add(loginView, "login");
//        mainPanel.add(registerView, "register");
//        mainPanel.add(menuView, "menu");
//        mainPanel.add(gameView, "game");
//        mainPanel.add(statsView, "stats");
//        mainPanel.add(configView, "config");
//        mainPanel.add(forgotPasswordView, "forgotPassword");

        // DO THE METHOD IN THE VIEW ITSELF
//        loginView.getSingUpButton().addActionListener(e -> switchCard("register"));
//        registerView.getLogInButton().addActionListener(e -> switchCard("login"));
//        menuView.getConfigButton().addActionListener(e -> switchCard("config"));
//        menuView.getStatsButton().addActionListener(e -> switchCard("stats"));



        /*
        configView.addBackListener(e -> showPrevious());
        statsView.addBackListener(e -> showPrevious());
        gameView.addBackListener(e -> showPrevious());
        loginView.addForgotPasswordListener(e -> {
            cardLayout.show(mainPanel, "forgotPassword");
        });
*/

        setTitle("CoffeeClicker");
        setIconImage(new ImageIcon("resources/coffee_cup.png").getImage());

        add(mainPanel);
    }

    public void switchCard(String cardName) {
        viewsStack.addLast(cardName);
        cardLayout.show(mainPanel, cardName);
    }

    public void showMainFrame() {
        setSize(WIDTH_MAIN_FRAME, HEIGHT_MAIN_FRAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        switchCard("login");
        setVisible(true);
    }

    public void showPrevious() {
        if (viewsStack.size() <= 1) return;
        viewsStack.remove(viewsStack.size() - 1);
        String previous = viewsStack.get(viewsStack.size() - 1);
        cardLayout.show(mainPanel, previous);
    }

    public JPanel getContainer() {
        return mainPanel;
    }

    public void addNewCard(JPanel panel, String cardName) {
        mainPanel.add(panel, cardName);
    }
}