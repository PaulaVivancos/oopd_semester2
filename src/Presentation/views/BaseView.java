package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class BaseView extends JPanel {
    private ActionListener listener;
    private JButton menuButton, arrowBackButton;
    private JPanel topBarGrid;
    private JPopupMenu popupMenu;
    private JImagePanel panel;

    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    protected final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    protected final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    public BaseView() {
        setLayout(new BorderLayout());

        panel = new JImagePanel(BACKGROUND_URL);
        panel.setOpacityValue(0.5f);
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        initMenu(); // primero la barra superior
        panel.add(topBarGrid, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER); // <-- explícito y seguro

        initComponents(); // ahora las vistas hijas pueden añadir contenido
    }


    public void addBackListener(ActionListener l) {
        this.listener = l;
    }

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

    private void setArrowBackButton() {
        arrowBackButton = new JButton("Go back");
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