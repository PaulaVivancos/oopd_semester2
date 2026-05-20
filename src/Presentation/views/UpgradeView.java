package Presentation.views;

import Business.entities.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static Presentation.views.ShopView.SAVE_GAME;

/**
 * View for the upgrade shop.
 * Displays a list of purchasable upgrades with costs and purchase state.
 */
public class UpgradeView extends BaseView {

    //Main parts
    private JPanel mainPanel;
    private JPanel jpCenter;
    private JLabel jlTitle;

    private List<JButton> jbBuyButtons;
    private List<JLabel> jlCurrentImprovement;
    private List<JLabel> jlPurchasableImprovement;

    public static final Upgrade[] UPGRADES = {
            new Upgrade("Better Grinder", 100, 2.0, "Vivari Life"),
            new Upgrade("Lukewarm Special", 500, 3.0, "Vivari Life"),
            new Upgrade("Extra Shot", 2000, 3.0, "Starsbucks barista"),
            new Upgrade("Oat Milk", 10000, 3.5, "Starsbucks barista"),
            new Upgrade("Better beans", 25000, 5.0, "365 Veteran"),
            new Upgrade("Black Belt Brew", 150000, 5.0, "365 Veteran"),
    };

    // COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);
    private final Color BACKGROUND_ROW = new Color(210, 190, 165);
    private final Color BACKGROUND_ROW_DISABLED = new Color(190, 175, 158);
    private static final Color TEXT_DARK = new Color(60, 30, 10);

    private static final Dimension DIM_BUY_BUTTON = new Dimension(90, 36);

    //Menu listeners
    private ActionListener logoutListener;
    private ActionListener deleteListener;

    public UpgradeView() {
        super(false);

        for (int i = 0; i < jbBuyButtons.size(); i++) {
            JButton buyBtn = jbBuyButtons.get(i);

            styleButton(buyBtn, DIM_BUY_BUTTON);

            Container row = buyBtn.getParent().getParent();
            if (row instanceof JPanel) {
                JPanel panelRow = (JPanel) row;
                panelRow.setBackground(BACKGROUND_ROW);
                panelRow.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, BACKGROUND_BUTTON),
                        BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
        }
    }

    public void addLogoutListener(ActionListener l) { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l) { this.deleteListener = l; }

    /**
     * Populates the top bar menu with save/load options and account actions.
     */
    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Save game", e -> System.out.println(SAVE_GAME));
        menu.addSeparator();
        addMenuItem(menu, "Log out", e -> {
            if (logoutListener != null) logoutListener.actionPerformed(e);
        });
        addMenuItem(menu, "Delete account", e -> {
            if (deleteListener != null) deleteListener.actionPerformed(e);
        });
    }

    /**
     * Initializes the title, upgrade rows, and main layout panel.
     * */
    @Override
    protected void initComponents() {
        jbBuyButtons = new ArrayList<>();
        jlCurrentImprovement = new ArrayList<>();
        jlPurchasableImprovement = new ArrayList<>();

        jlTitle = new JLabel("Upgrade shop");
        jlTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        jlTitle.setForeground(BACKGROUND_BUTTON);
        jlTitle.setBackground(BACKGROUND_BUTTON_PRESSED);
        jlTitle.setOpaque(true);
        jlTitle.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));

        JPanel jpNorth = new JPanel();
        jpNorth.setOpaque(false);
        jpNorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        jpNorth.add(jlTitle);

        jpCenter = new JPanel();
        jpCenter.setLayout(new BoxLayout(jpCenter, BoxLayout.Y_AXIS));
        jpCenter.setOpaque(false);

        for (int i = 0; i < UPGRADES.length; i++) {
            jpCenter.add(buildUpgradeRow(i));
            if (i < UPGRADES.length - 1) jpCenter.add(Box.createVerticalStrut(12));
        }

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));
        mainPanel.add(jpNorth, BorderLayout.NORTH);
        mainPanel.add(jpCenter, BorderLayout.CENTER);

        addToCenter(mainPanel);
    }

    /**
     * Builds a single upgrade row with name, cost, and a buy button.
     * @param index the position of the upgrade in the UPGRADES array
     */
    private JPanel buildUpgradeRow(int index) {
        Upgrade upg = UPGRADES[index];

        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(true);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel nameLabel = new JLabel(upg.getName());
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_DARK);

        JLabel costLabel = new JLabel(String.format("Cost: %.0f", upg.getCost()));
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        costLabel.setForeground(TEXT_DARK);

        JLabel currentImprovLabel = new JLabel("Current: 1.0x");
        currentImprovLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        currentImprovLabel.setForeground(TEXT_DARK);
        jlCurrentImprovement.add(currentImprovLabel);

        JLabel purchasableImprovLabel = new JLabel(String.format("Upgrade: %.1fx", upg.getMultiplier()));
        purchasableImprovLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        purchasableImprovLabel.setForeground(TEXT_DARK);
        jlPurchasableImprovement.add(purchasableImprovLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(costLabel);
        infoPanel.add(Box.createVerticalStrut(2));
        infoPanel.add(currentImprovLabel);
        infoPanel.add(purchasableImprovLabel);

        JButton buyBtn = new JButton("BUY");
        styleButton(buyBtn, DIM_BUY_BUTTON);
        buyBtn.setEnabled(false);
        buyBtn.setActionCommand(String.valueOf(index));
        jbBuyButtons.add(buyBtn);

        JPanel eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setOpaque(false);
        eastPanel.add(buyBtn);

        row.add(infoPanel, BorderLayout.CENTER);
        row.add(eastPanel, BorderLayout.EAST);

        return row;
    }

    /**
     * Applies consistent size, color, and press effect styling to a button.
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
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BACKGROUND_BUTTON_PRESSED);
                    button.setForeground(Color.BLACK);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BACKGROUND_BUTTON);
                    button.setForeground(Color.WHITE);
                }
            }
        });
    }

    /**
     * Updates the affordability and purchase state of an upgrade row.
     * @param index the upgrade row to update
     * @param canAfford whether the player can currently afford this upgrade
     * @param purchased whether this upgrade has already been bought
     */
    public void updateUpgradeRow(int index, boolean canAfford, boolean purchased, double currentMultiplier) {
        JButton btn = jbBuyButtons.get(index);
        Container row = btn.getParent().getParent();

        btn.setEnabled(!purchased && canAfford);
        btn.setText(purchased ? "OWNED" : "BUY");

        jlCurrentImprovement.get(index).setText(String.format("Current: %.1fx", currentMultiplier));
        jlPurchasableImprovement.get(index).setVisible(!purchased);

        if (row instanceof JPanel) {
            row.setBackground(purchased || !canAfford ? BACKGROUND_ROW_DISABLED : BACKGROUND_ROW);
        }
    }

    /**
     * Attaches the given listener to all upgrade buy buttons.
     */
    public void addBuyUpgradeListener(ActionListener l) {
        for (JButton btn : jbBuyButtons) {
            btn.addActionListener(l);
        }
    }
}
