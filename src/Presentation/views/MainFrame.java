package Presentation.views;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Main application window that manages view navigation using a CardLayout.
 * Maintains a history stack to support backward navigation between panels.
 */
public class MainFrame extends JFrame {
    private LinkedList<String> viewsStack = new LinkedList<>();
    private CardLayout cardLayout;
    private static JPanel mainPanel;

    private final int WIDTH_MAIN_FRAME = 1150;
    private final int HEIGHT_MAIN_FRAME = 800;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setTitle("CoffeeClicker");
        setIconImage(new ImageIcon("resources/coffee_cup.png").getImage());

        add(mainPanel);
    }

    /**
     * Navigates to the specified card by name, adding it to the view history stack.
     * @param cardName the name of the card/panel to display
     */
    public void switchCard(String cardName) {
        viewsStack.addLast(cardName);
        cardLayout.show(mainPanel, cardName);
    }

    /**
     * Configures and displays the main application window, starting on the login view.
     */
    public void showMainFrame() {
        setSize(WIDTH_MAIN_FRAME, HEIGHT_MAIN_FRAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        switchCard("login");    //TEST VIEW
        setVisible(true);
    }

    /**
     * Navigates back to the previous view in the history stack, if one exists.
     */
    public void showPrevious() {
        if (viewsStack.size() <= 1) return;
        viewsStack.remove(viewsStack.size() - 1);
        String previous = viewsStack.get(viewsStack.size() - 1);
        cardLayout.show(mainPanel, previous);
    }

    public JPanel getContainer() {
        return mainPanel;
    }

    /**
     * Registers a new panel with the CardLayout under the given card name.
     * @param panel the JPanel to add
     * @param cardName the name used to reference and display this panel
     */
    public void addNewCard(JPanel panel, String cardName) {
        mainPanel.add(panel, cardName);
    }
}