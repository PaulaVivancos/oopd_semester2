package Presentation.views;

import Business.entities.GeneratorType;
import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static Presentation.controllers.GameController.NUM_GENERATORS;

/**
 * View for the generator shop.
 * Displays a scrollable list of purchasable generators with costs and ownership info.
 */
public class ShopView extends BaseView {
    //Main parts
    private JPanel mainPanel;
    private JPanel jpNorth;
    private JPanel jpCenter;

    private JLabel jlTitle;

    //Generator parts
    private List<JLabel> jlNames;
    private List<JLabel> jlCosts;
    private List<JLabel> jlProductions;
    private List<JLabel> jlOwned;
    private List<JButton> jbBuyButtons;
    private List<JImagePanel> jipImages;

    protected final static String SAVE_GAME = "SAVE_GAME";

    //decides how many generators there are in other parts as well

    private static final String[] GENERATOR_IMAGES = {
            "src/Presentation/Images/coffee_cup.png",
            "src/Presentation/Images/coffee_cup.png",
            "src/Presentation/Images/coffee_cup.png"
    };


    //TODO: link this to actual mechanism
    private final int[] ownedCounts = {0, 0, 0,0};

    // COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_MEDIUM = new Color(156, 98, 74);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    private final Color BACKGROUND_ROW = new Color(210, 190, 165);
    private final Color BACKGROUND_ROW_DISABLED = new Color(190, 175, 158);
    private static final Color TEXT_DARK = new Color(60, 30, 10);


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
    /**
     * Populates the top bar menu with save/load options and account actions.
     */
    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Save game", e -> SAVE_GAME);
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

    /**
     * Initializes the list components and builds the north title and center generator rows.
     */
    @Override
    protected void initComponents() {
        jlNames = new ArrayList<>();
        jlCosts = new ArrayList<>();
        jlProductions = new ArrayList<>();
        jlOwned = new ArrayList<>();
        jbBuyButtons = new ArrayList<>();
        jipImages = new ArrayList<>();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));

        buildNorth();
        buildCenter();

        mainPanel.add(jpNorth,  BorderLayout.NORTH);
        mainPanel.add(jpCenter, BorderLayout.CENTER);

        addToCenter(mainPanel);
    }

    /**
     * Builds the title label panel at the top of the shop.
     */
    private void buildNorth() {
        jpNorth = new JPanel();
        jpNorth.setOpaque(false);
        jpNorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        jlTitle = new JLabel("Generators shop");
        jlTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        jlTitle.setForeground(BACKGROUND_BUTTON);
        jlTitle.setBackground(BACKGROUND_BUTTON_PRESSED);
        jlTitle.setOpaque(true);
        jlTitle.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));

        jpNorth.add(jlTitle);
    }

    /**
     * Builds the scrollable list of generator rows.
     */
    private void buildCenter() {
        jpCenter = new JPanel();
        jpCenter.setLayout(new BoxLayout(jpCenter, BoxLayout.Y_AXIS));
        jpCenter.setOpaque(false);

        // build empty rows at startup
        for (int i = 0; i < NUM_GENERATORS; i++) {
            jpCenter.add(buildGeneratorRow(i));
            if (i < NUM_GENERATORS - 1) {
                jpCenter.add(Box.createVerticalStrut(12));
            }
        }

    }

    /**
     * Builds a single generator row with image, name, cost, production, owned count, and a buy button.
     * @param index the position of the generator in the list
     */
    private JPanel buildGeneratorRow(int index) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(BACKGROUND_ROW);
        row.setOpaque(true);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, BACKGROUND_BUTTON),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JImagePanel img = new JImagePanel(null);
        img.setPreferredSize(new Dimension(60, 60));
        img.setMaximumSize(new Dimension(60, 60));
        img.setOpaque(false);
        jipImages.add(img);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("...");
        JLabel costLabel = new JLabel("Cost: -");
        JLabel prodLabel = new JLabel("Production: -");

        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_DARK);
        jlNames.add(nameLabel);

        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        costLabel.setForeground(TEXT_DARK);
        jlCosts.add(costLabel);

        prodLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        prodLabel.setForeground(TEXT_DARK);
        jlProductions.add(prodLabel);

        JLabel ownedLabel = new JLabel("Owned: 0");
        ownedLabel.setFont(new Font("Times New Roman", Font.ITALIC, 12));
        ownedLabel.setForeground(BACKGROUND_BUTTON);
        jlOwned.add(ownedLabel);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(costLabel);
        infoPanel.add(prodLabel);
        infoPanel.add(ownedLabel);

        JButton buyBtn = new JButton("BUY");
        styleButton(buyBtn, DIM_BUY_BUTTON);
        buyBtn.setEnabled(true);
        jbBuyButtons.add(buyBtn);

        JPanel eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setOpaque(false);
        eastPanel.add(buyBtn);

        row.add(img, BorderLayout.WEST);
        row.add(infoPanel, BorderLayout.CENTER);
        row.add(eastPanel, BorderLayout.EAST);

        return row;
    }

    /**
     *
     * @param button
     * @param dimension
     */
    private void styleButton(JButton button, Dimension dimension) {
        button.setPreferredSize(dimension);
        button.setMinimumSize(dimension);
        button.setMaximumSize(dimension);
        button.setBackground(BACKGROUND_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Times New Roman", Font.BOLD, 13));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(BACKGROUND_BUTTON_PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    /**
     * Updates the cost, owned count, and affordability state of a generator row.
     * @param index the generator row to update
     * @param nextCost the updated purchase cost
     * @param owned the number currently owned
     * @param canAfford whether the player can currently afford this generator
     */
    public void updateGeneratorRow(int index, double nextCost, int owned, boolean canAfford) {
        ownedCounts[index] = owned;
        jlCosts.get(index).setText(String.format("Cost: %.2f", nextCost));
        jlOwned.get(index).setText("Owned: " + owned);

        JButton btn = jbBuyButtons.get(index);
        btn.setEnabled(canAfford);

        Container row = btn.getParent().getParent();
        if (row instanceof JPanel) {
            ((JPanel) row).setBackground(canAfford ? BACKGROUND_ROW : BACKGROUND_ROW_DISABLED);
        }
    }

    public JButton getBuyButton(int index) {
        return jbBuyButtons.get(index);
    }

    public List<JButton> getAllBuyButtons() {
        return jbBuyButtons;
    }

    /**
     * Populates all generator rows with data from the given type list.
     * @param types the list of generator types to display
     */
    public void initGenerators(List<GeneratorType> types) {
        for (int i = 0; i < types.size(); i++) {
            GeneratorType t = types.get(i);
            jlNames.get(i).setText(t.getName());
            jlCosts.get(i).setText(String.format("Cost: %.0f", t.getBaseCost()));
            jlProductions.get(i).setText(String.format("Production: %.1f /s", t.getBaseProduction()));
            jipImages.get(i).setImage(t.getImagePath());
        }

        jpCenter.revalidate();
        jpCenter.repaint();

    }

    //listeners
    // GameView
    public void addGenBuyListener(int index, ActionListener listener) {
        jbBuyButtons.get(index).addActionListener(listener);
    }

    //TODO: move to logic?

}


