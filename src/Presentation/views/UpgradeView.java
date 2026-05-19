package Presentation.views;

import Business.entities.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UpgradeView extends BaseView {

    //Main parts
    private JPanel mainPanel;
    private JPanel jpCenter;
    private JLabel jlTitle;

    private List<JButton> jbBuyButtons;

    public static final Upgrade[] UPGRADES = {
            new Upgrade("Better Grinder", 100, 2.0, "Gas station clerk"),
            new Upgrade("Lukewarm Special", 500, 3.0, "Gas station clerk"),
            new Upgrade("Extra Shot", 2000, 3.0, "Starsbuck barista"),
            new Upgrade("Oat Milk", 10000, 3.5, "Starsbuck barista"),
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
        super();
    }

    public void addLogoutListener(ActionListener l) { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l) { this.deleteListener = l; }

    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Guardar partida", e -> System.out.println("save"));
        addMenuItem(menu, "Cargar partida", e -> System.out.println("load"));
        menu.addSeparator();
        addMenuItem(menu, "Log out", e -> {
            if (logoutListener != null) logoutListener.actionPerformed(e);
        });
        addMenuItem(menu, "Delete account", e -> {
            if (deleteListener != null) deleteListener.actionPerformed(e);
        });
    }

    @Override
    protected void initComponents() {
        jbBuyButtons = new ArrayList<>();

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

    private JPanel buildUpgradeRow(int index) {
        Upgrade upg = UPGRADES[index];

        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(BACKGROUND_ROW);
        row.setOpaque(true);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, BACKGROUND_BUTTON),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel nameLabel = new JLabel(upg.getName());
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_DARK);

        JLabel costLabel = new JLabel(String.format("Cost: %.0f", upg.getCost()));
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        costLabel.setForeground(TEXT_DARK);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(costLabel);

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

    public void updateUpgradeRow(int index, boolean canAfford, boolean purchased) {
        JButton btn = jbBuyButtons.get(index);
        Container row = btn.getParent().getParent();

        btn.setEnabled(!purchased && canAfford);
        btn.setText(purchased ? "OWNED" : "BUY");

        if (row instanceof JPanel) {
            row.setBackground(purchased || !canAfford ? BACKGROUND_ROW_DISABLED : BACKGROUND_ROW);
        }
    }

    public void addBuyUpgradeListener(ActionListener l) {
        for (JButton btn : jbBuyButtons) {
            btn.addActionListener(l);
        }
    }
}
