package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShopView extends BaseView {
    //Main parts
    private JPanel mainPanel;
    private JPanel jpNorth;
    private JPanel jpCenter;

    private JLabel jlTitle;

    //Generator parts
    private final List<JLabel> jlNames = new ArrayList<>();
    private final List<JLabel> jlCosts = new ArrayList<>();
    private final List<JLabel> jlProductions = new ArrayList<>();
    private final List<JLabel> jlOwned = new ArrayList<>();
    private final List<JButton> jbBuyButtons = new ArrayList<>();
    private final List<JImagePanel> jipImages = new ArrayList<>();


    private static final String[] GENERATOR_NAMES = {
            "Gas station clerk",
            "Starsbuck barista",
            "365 Veteran"
    };

    private static final String[] GENERATOR_IMAGES = {
            "src/Presentation/Images/coffee_cup.png",
            "src/Presentation/Images/coffee_cup.png",
            "src/Presentation/Images/coffee_cup.png"
    };

    //Values
    private static final double[] BASE_COSTS = {10, 150, 2000};
    private static final double[] BASE_PRODUCTIONS = {0.2, 1.0, 15.0};
    private static final double[] INCREMENT_COSTS  = {1.07, 1.15, 1.12};

    //TODO: link this to actual mechanism
    private final int[] ownedCounts = {0, 0, 0};

    // COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    // SIZE CONSTANTS
    private static final Dimension DIM_BUY_BUTTON = new Dimension(90, 36);

    public ShopView() {
        super();
    }

    //Menu stuff
    private ActionListener logoutListener;
    private ActionListener deleteListener;

    public void addLogoutListener(ActionListener l) { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l) { this.deleteListener = l; }

    //From Game View TODO: maybe remove redundancy
    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Guardar partida", e -> System.out.println("save"));
        addMenuItem(menu, "Cargar partida", e -> System.out.println("load"));
        menu.addSeparator();
        addMenuItem(menu, "Log out", e -> {
            if(logoutListener != null)
                logoutListener.actionPerformed(e);
        });

        addMenuItem(menu, "Delete account", e -> {
            if(deleteListener != null)
                deleteListener.actionPerformed(e);
        });
    }

    @Override
    protected void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));

        addToCenter(mainPanel);
    }
}
