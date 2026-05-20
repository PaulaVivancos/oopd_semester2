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
    private JButton arrowBackButton;
    private JPanel topBarGrid;
    private JPopupMenu popupMenu;
    private JImagePanel panel;

    protected JButton menuButton;
    protected boolean showMenuButton = true;

    // GLOBAL STATIC LOGOUT LISTENER SHARED BY ALL SUBCLASSES
    private static ActionListener globalLogoutListener;

    private final String BACKGROUND_URL = "resources/background.jpg";

    protected final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    protected final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    /**
     * Sets up the background panel, top bar, and calls subclass initialization.
     */
    public BaseView(boolean showMenuButton) {
        this.showMenuButton = showMenuButton;

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
     * Sets the static global logout listener for ALL views that extend BaseView.
     */
    public static void setGlobalLogoutListener(ActionListener listener) {
        globalLogoutListener = listener;
    }

    /**
     * Convenience method for subclasses to automatically attach the working global logout logic.
     * @param menu the popup menu to add the logout item to
     */
    protected void addGlobalLogoutItem(JPopupMenu menu) {
        JMenuItem logoutItem = new JMenuItem("Log out");
        logoutItem.addActionListener(e -> {
            if (globalLogoutListener != null) {
                globalLogoutListener.actionPerformed(e);
            }
        });
        menu.add(logoutItem);
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

        setArrowBackButton();
        arrowBackButton.addActionListener(e -> {
            if (listener != null) {
                listener.actionPerformed(e);
            }
        });
        topBarLeft.add(arrowBackButton);

        if (showMenuButton) {
            setMenuButton();

            popupMenu = new JPopupMenu();
            buildMenu(popupMenu);

            menuButton.addActionListener(e ->
                    popupMenu.show(menuButton, 0, menuButton.getHeight())
            );

            topBarRight.add(menuButton);
        }

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

    protected abstract void buildMenu(JPopupMenu menu);
    protected abstract void initComponents();

    protected void addMenuItem(JPopupMenu menu, String label, ActionListener action) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(action);
        menu.add(item);
    }

    protected void addToCenter(JComponent component) {
        panel.add(component, BorderLayout.CENTER);
    }

    protected void addToSouth(JComponent component) {
        panel.add(component, BorderLayout.SOUTH);
    }
}