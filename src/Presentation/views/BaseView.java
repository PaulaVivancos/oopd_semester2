package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Abstract base class for all application views. Provides a shared layout with
 * a background image, a top bar containing a back button and a dropdown menu,
 * and extension points for subclasses to define their own components and menu items.
 */
public abstract class BaseView extends JPanel {
    private ActionListener listener;
    private JButton menuButton, arrowBackButton;
    private JPanel topBarGrid;
    private JPopupMenu popupMenu;
    private JImagePanel panel;

    private final String BACKGROUND_URL = "resources/background.jpg";

    protected final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    protected final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    /**
     * Sets up the background panel, top bar, and calls subclass initialization.
     */
    public BaseView() {
        setLayout(new BorderLayout());

        panel = new JImagePanel(BACKGROUND_URL);
        panel.setOpacityValue(0.5f);
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);


        initMenu();
        panel.add(topBarGrid, BorderLayout.NORTH);

        initComponents();


        add(panel, BorderLayout.CENTER);

    }

    /**
     * Registers a listener to be triggered when the back button is clicked.
     * @param l the ActionListener to attach to the back button
     */
    public void addBackListener(ActionListener l) {
        this.listener = l;
    }

    /**
     * Builds the top bar with a left-aligned back button and a right-aligned menu button
     * that opens a populated popup menu.
     */
    private void initMenu() {
        topBarGrid = new JPanel(new GridLayout(1, 2));
        topBarGrid.setOpaque(false);
        topBarGrid.setPreferredSize(new Dimension(0, 40));
        topBarGrid.setMinimumSize(new Dimension(0, 40));
        topBarGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel topBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        topBarRight.setOpaque(false);

        JPanel topBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        topBarLeft.setOpaque(false);

        setMenuButton();
        setArrowBackButton();

        popupMenu = new JPopupMenu();
        buildMenu(popupMenu);

        menuButton.addActionListener(e ->
                popupMenu.show(menuButton, 0, menuButton.getHeight())
        );

        arrowBackButton.addActionListener(e -> {
            if (listener != null) {
                listener.actionPerformed(e);
            }
        });

        topBarRight.add(menuButton);
        topBarLeft.add(arrowBackButton);

        topBarGrid.add(topBarLeft);
        topBarGrid.add(topBarRight);
    }

    /**
     * Configures the appearance of the dropdown menu button.
     */
    private void setMenuButton() {
        menuButton = new JButton("Menu");
        menuButton.setMargin(new Insets(2, 8, 2, 8));
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuButton.setOpaque(false);
    }

    /**
     * Configures the appearance of the back navigation button.
     */
    private void setArrowBackButton() {
        arrowBackButton = new JButton("Back");
        arrowBackButton.setMargin(new Insets(2, 8, 2, 8));
        arrowBackButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        arrowBackButton.setBorderPainted(false);
        arrowBackButton.setContentAreaFilled(false);
        arrowBackButton.setFocusPainted(false);
        arrowBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        arrowBackButton.setOpaque(false);
    }

    /**
     * Subclasses implement this to populate the top bar dropdown menu with items.
     * @param menu the popup menu to add items to
     */
    protected abstract void buildMenu(JPopupMenu menu);

    /**
     * Subclasses implement this to initialize and lay out their specific UI components.
     */
    protected abstract void initComponents();

    /**
     * Convenience method for adding a labeled menu item with an action to a popup menu.
     * @param menu the popup menu to add the item to
     * @param label the display text of the menu item
     * @param action the listener to invoke when the item is clicked
     */
    protected void addMenuItem(JPopupMenu menu, String label, ActionListener action) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(action);
        menu.add(item);
    }

    /**
     * Adds a component to the center region of the background panel.
     * @param component the component to add
     */
    protected void addToCenter(JComponent component) {
        panel.add(component, BorderLayout.CENTER);
    }

    /**
     * Adds a component to the south region of the background panel.
     * @param component the component to add
     */
    protected void addToSouth(JComponent component) {
        panel.add(component, BorderLayout.SOUTH);
    }
}