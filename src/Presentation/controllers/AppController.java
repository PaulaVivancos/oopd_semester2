package Presentation.controllers;

import Presentation.views.LoginView;
import Presentation.views.MainFrame;

import javax.swing.*;

public class AppController {
    private MainFrame mainFrame;


    public AppController() {
        mainFrame = new MainFrame();
    }

    public void startSystem() {
        SwingUtilities.invokeLater(() -> {
            mainFrame.showMainFrame();
        });
    }

    public void switchCard(String cardName) {
        mainFrame.switchCard(cardName);
    }

    public int showConfirmationPopUp(String title, String message) {
        return JOptionPane.showConfirmDialog(this.mainFrame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public void showErrorPopUp(String title, String message) {
        JOptionPane.showMessageDialog(this.mainFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoPopUp(String title, String message) {
        JOptionPane.showMessageDialog(this.mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void addCardToMainFrame(JPanel panel, String cardName) {
        mainFrame.addNewCard(panel, cardName);
    }

    public void goBack() {
        mainFrame.showPrevious();
    }
}
