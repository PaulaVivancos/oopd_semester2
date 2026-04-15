package Presentation.controllers;

import Presentation.views.MainFrame;

import javax.swing.*;

public class AppController {
    private MainFrame mainFrame;

    public AppController() {
        mainFrame = new MainFrame(this);
    }

    public void startSystem() {
        SwingUtilities.invokeLater(() -> {
            mainFrame.showMainFrame();
        });
    }

    public void switchCard(String cardName) {
        mainFrame.switchCard(cardName);
    }

}
