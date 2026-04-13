package Presentation.controllers;

import Presentation.views.LoginView;
import Presentation.views.MainFrame;

import javax.swing.*;

public class AppController {
    static private MainFrame mainFrame;


    public AppController() {
        mainFrame = new MainFrame(this);
        mainFrame.showMainFrame();
    }

    public void switchToCard(String cardName) {
        mainFrame.switchCard(cardName);
    }
}
