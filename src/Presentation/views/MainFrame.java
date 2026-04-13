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
        // ...

        // Create controllers and passing as the navigator
        new UserController(appController, loginView);

        // Add cards
        mainPanel.add(loginView, "login");
        mainPanel.add(registerView, "register");
        mainPanel.add(menuView, "menu");

        loginView.getSingUpButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "register");
        });

        registerView.getLogInButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "login");
        });

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
