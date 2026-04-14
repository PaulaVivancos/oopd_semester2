package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class BaseView extends JPanel {
    private JButton menuButton;
    private JPopupMenu popupMenu;
    private JImagePanel panel;

    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    public BaseView() {
        panel = new JImagePanel(BACKGROUND_URL);
        panel.setOpacityValue(0.5f);
        setLayout(new BorderLayout());
        initMenu();
        initComponents();
    }

    private void initMenu() {
        setLayout(new OverlayLayout(this));
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        topBar.setOpaque(false);

        menuButton = new JButton("Menu");
        menuButton.setMargin(new Insets(2, 8, 2, 8));
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuButton.setOpaque(false);

        popupMenu = new JPopupMenu();
        buildMenu(popupMenu);   // las subclases rellenan el menú

        menuButton.addActionListener(e ->
                popupMenu.show(menuButton, 0, menuButton.getHeight())
        );

        topBar.add(menuButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);
    }

    // ── API para las subclases ───────────────────────────────────

    /**
     * Añade ítems al menú popup.
     * Llama a addMenuItem() para cada opción que necesites.
     */
    protected abstract void buildMenu(JPopupMenu menu);

    /**
     * Construye y añade el contenido propio de cada pantalla
     * en la zona CENTER del BorderLayout.
     */
    protected abstract void initComponents();

    /**
     * Helper: crea un JMenuItem y lo añade al menú.
     */
    protected void addMenuItem(JPopupMenu menu, String label, ActionListener action) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(action);
        menu.add(item);
    }
}