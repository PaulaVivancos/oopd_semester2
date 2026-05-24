package Presentation.controllers;

import Presentation.views.MainFrame;

import javax.swing.*;

/**
 * Central controller that manages the main application window and provides
 * shared navigation and dialog utilities to all other controllers.
 */
public class AppController {
    private MainFrame mainFrame;

    /**
     * Initializes the main application window.
     */
    public AppController() {
        mainFrame = new MainFrame();
    }

    /**
     * Launches the main frame on the Swing event dispatch thread.
     */
    public void startSystem() {
        SwingUtilities.invokeLater(() -> {
            mainFrame.showMainFrame();
        });
    }

    /**
     * Navigates to the specified card in the main frame.
     * @param cardName the name of the card/panel to display
     */
    public void switchCard(String cardName) {
        mainFrame.switchCard(cardName);
    }

    /**
     * Displays a yes/no confirmation dialog and returns the user's choice.
     * @param title the dialog window title
     * @param message the message to display
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION
     */
    public int showConfirmationPopUp(String title, String message) {
        return JOptionPane.showConfirmDialog(this.mainFrame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays an error message dialog.
     * @param title the dialog window title
     * @param message the error message to display
     */
    public void showErrorPopUp(String title, String message) {
        JOptionPane.showMessageDialog(this.mainFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an informational message dialog.
     * @param title   the dialog window title
     * @param message the message to display
     */
    public void showInfoPopUp(String title, String message) {
        JOptionPane.showMessageDialog(this.mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Registers a panel with the main frame's CardLayout under the given name.
     * @param panel the panel to add
     * @param cardName the name used to reference and display this panel
     */
    public void addCardToMainFrame(JPanel panel, String cardName) {
        mainFrame.addNewCard(panel, cardName);
    }

    /**
     * Navigates back to the previously displayed card.
     */
    public void goBack() {
        mainFrame.showPrevious();
    }



}
