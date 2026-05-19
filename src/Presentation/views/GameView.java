package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Presentation.views.ShopView.SAVE_GAME;
import static java.lang.Math.round;

/**
 * View for the main gameplay screen, showing the coffee cup, counter, shop buttons, and a data table.
 */
public class GameView extends BaseView {
    private JPanel mainPanel;
    private JPanel jpTop, jpBot, jpCentre, jpEast, jpWest;
    private JImagePanel jipTitle, jipCoffeeCupSmall;
    private JButton jbBuy, jbGen, jbUpg, jipCoffeeCup;
    private JLabel jlCounter;
    private JTable jtTable;

    // DIMENSION CONSTANTS
    private final Dimension DIMENSION_BUTTON = new Dimension(150, 50);

    // COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    // IMAGES
    private final String BACKGROUND_URL = "resources/background.jpg";
    private final String TITLE_URL = "resources/title.png";
    private final String COFFEE_CUP = "resources/coffee_cup.png";

    public static final String NEW_GAME = "NEW_GAME";
    public static final String LOAD_GAME = "LOAD_GAME";
    public static final String VIEW_GAME = "VIEW_GAME";
    public static final String BUY_COFFEE = "BUY_COFFEE";
    public static final String GO_SHOP = "GO_TO_SHOP";


    public GameView() {
        super(); // initMenu() + initComponents()
    }

    private ActionListener logoutListener;
    private ActionListener deleteListener;
    private ActionListener newGameListener;
    private ActionListener loadGameListener;
    private ActionListener viewGameListener;

    /**
     * Populates the top bar menu with save/load game options and account actions.
     */
    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Save game", e-> System.out.println(SAVE_GAME));
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
     * Initializes all game UI components including panels, buttons, the counter label, and the coffee cup.
     */
    @Override
    protected void initComponents() {
        // Panels
        mainPanel = new JPanel();
        jpTop = new JPanel();
        jpCentre = new JPanel();
        jpBot = new JPanel();
        jpEast = new JPanel();
        jpWest = new JPanel();

        // Images
        jipTitle = new JImagePanel(TITLE_URL);
        //jipCoffeeCup = new JImagePanel(COFFEE_CUP);
        jipCoffeeCupSmall = new JImagePanel(COFFEE_CUP);

        // COFFEE clickable
        ImageIcon raw = new ImageIcon(COFFEE_CUP);
        Image scaled = raw.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        jipCoffeeCup = new JButton(new ImageIcon(scaled)); //assign to field
        jipCoffeeCup.setContentAreaFilled(false);
        jipCoffeeCup.setBorderPainted(false);
        jipCoffeeCup.setFocusPainted(false);
        jipCoffeeCup.setActionCommand(BUY_COFFEE);

        // Other components
        jlCounter = new JLabel();
        jtTable = new JTable();

        // Buttons
        jbBuy = new JButton("BUY COFFEE");
        jbBuy.setActionCommand(BUY_COFFEE);
        jbGen = new JButton("GENERATORS");
        jbGen.setActionCommand(GO_SHOP);
        jbUpg = new JButton("UPGRADES");

        // Sets everything
        setJipMain();
    }

    /**
     * Assembles the main BorderLayout panel from all sub-panels and adds it to the view.
     */
    private void setJipMain() {
        setJpTop();
        setJpCentre();
        setJpBot();
        setJpEast();
        setJpWest();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.add(jpTop, BorderLayout.NORTH);
        mainPanel.add(jpCentre, BorderLayout.CENTER);
        mainPanel.add(jpBot, BorderLayout.SOUTH);
        mainPanel.add(jpEast, BorderLayout.EAST);
        mainPanel.add(jpWest, BorderLayout.WEST);


        addToCenter(mainPanel);
    }

    /**
     * Builds the top panel containing the game title image.
     */
    private void setJpTop() {
        jpTop.setLayout(new BoxLayout(jpTop, BoxLayout.Y_AXIS));
        jpTop.setOpaque(false);
        jpTop.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        jpTop.setPreferredSize(new Dimension(getWidth(), 200));

        jipTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jipTitle.setOpaque(false);
        jipTitle.setPreferredSize(new Dimension(600, 100));
        jipTitle.setMaximumSize(new Dimension(600, 100));

        jpTop.add(jipTitle);
    }

    /**
     * Builds the bottom panel containing the generators table.
     */
    private void setJpBot() {
        jpBot.setPreferredSize(new Dimension(600, 200));
        jpBot.setMaximumSize(new Dimension(600, 200));
        jpBot.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jpBot.setOpaque(false);

        String[] columns = {"ID", "Name", "Price"};
        Object[][] data = {
                {1, "Coffee", "$2.99"},
                {2, "Tea", "$1.99"},
                {3, "Cake", "$3.99"}
        };

        jtTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(jtTable);

        //instance table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        jtTable.getTableHeader().setReorderingAllowed(false);
        jtTable.setModel(tableModel);

        jpBot.setLayout(new BorderLayout());
        jpBot.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Builds the center panel containing the clickable coffee cup image.
     */
    private void setJpCentre() {
        jpCentre.setLayout(new BoxLayout(jpCentre, BoxLayout.Y_AXIS));
        jpCentre.setOpaque(false);

        jipCoffeeCup.setAlignmentX(Component.CENTER_ALIGNMENT);
        jipCoffeeCup.setOpaque(false);
        jipCoffeeCup.setPreferredSize(new Dimension(250, 300));
        jipCoffeeCup.setMaximumSize(new Dimension(250, 300));

        jpCentre.add(jipCoffeeCup);
    }

    /**
     * Builds the east panel with the buy, generators, and upgrades buttons.
     */
    private void setJpEast() {
        jpEast.setLayout(new BoxLayout(jpEast, BoxLayout.Y_AXIS));
        jpEast.setOpaque(false);
        jpEast.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 60));

        setButton(jbBuy, DIMENSION_BUTTON);
        setButton(jbGen, DIMENSION_BUTTON);
        setButton(jbUpg, DIMENSION_BUTTON);

        jpEast.add(jbBuy);
        jpEast.add(Box.createVerticalStrut(20));
        jpEast.add(jbGen);
        jpEast.add(Box.createVerticalStrut(20));
        jpEast.add(jbUpg);
    }

    /**
     * Builds the west panel with the small coffee cup icon and the coffee counter label.
     */
    private void setJpWest() {
        jpWest.setLayout(new FlowLayout());
        jpWest.setOpaque(false);
        jpWest.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 20));

        jlCounter.setFont(new Font("Arial", Font.PLAIN, 30));
        jlCounter.setPreferredSize(new Dimension(120, 40)); //wide enough for max
        jlCounter.setHorizontalAlignment(SwingConstants.LEFT);

        jipCoffeeCupSmall.setOpaque(false);
        jipCoffeeCupSmall.setPreferredSize(new Dimension(50, 50));
        jipCoffeeCupSmall.setMaximumSize(new Dimension(50, 50));

        jpWest.add(jipCoffeeCupSmall);
        jpWest.add(jlCounter);
    }

    /**
     * Applies consistent size, color, and press effect styling to a button.
     */
    private void setButton(JButton button, Dimension dimension) {
        button.setPreferredSize(dimension);
        button.setMinimumSize(dimension);
        button.setMaximumSize(dimension);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        button.setBackground(BACKGROUND_BUTTON);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(BACKGROUND_BUTTON_PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    /**
     * Opens a modal dialog offering new game, load game, and view game options.
     * @param actionListener forwarded to internal buttons for external handling
     */
    public void showGamesPopUp(ActionListener actionListener) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);

        JPanel dialogContent = new JPanel();
        dialogContent.setLayout(new BoxLayout(dialogContent, BoxLayout.Y_AXIS));
        dialogContent.setBackground(BACKGROUND_BUTTON_PRESSED);
        dialogContent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel title = new JLabel("WHAT DO YOU WANT TO DO");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 16));

        JButton newGame = createDialogButton("NEW GAME");
        JButton loadGame = createDialogButton("LOAD GAME");

        newGame.setActionCommand(NEW_GAME);
        newGame.addActionListener(e -> {
            dialog.dispose();
            if (newGameListener != null) {
                newGameListener.actionPerformed(e);
            }
        });

        loadGame.setActionCommand(LOAD_GAME);
        loadGame.addActionListener(e -> {
            dialog.dispose();
            if (loadGameListener != null) {
                loadGameListener.actionPerformed(e);
            }
        });

        dialogContent.add(title);
        dialogContent.add(Box.createVerticalStrut(15));
        dialogContent.add(newGame);
        dialogContent.add(Box.createVerticalStrut(10));
        dialogContent.add(loadGame);

        dialog.add(dialogContent);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JButton createDialogButton(String title) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 45));
        button.setBackground(BACKGROUND_BUTTON);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setOpaque(false);

        return button;
    }

    public void addLogoutListener(ActionListener l)  { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l)  { this.deleteListener = l; }

    public void addBuyListener(ActionListener actionListener) {
        jbBuy.addActionListener(actionListener);
        jipCoffeeCup.addActionListener(actionListener);
    }

    public void addShopListener(ActionListener actionListener) {
        jbGen.addActionListener(actionListener);
    }

    public void addNewGameListener(ActionListener actionListener) {
        this.newGameListener = actionListener;
    }

    public void addLoadGameListener(ActionListener actionListener) {
        this.loadGameListener = actionListener;
    }

    public void addViewGameListener(ActionListener actionListener) {
        this.viewGameListener = actionListener;
    }


    // Getters

    public JButton getJbBuy() {
        return jbBuy;
    }

    public JButton getJbGen() {
        return jbGen;
    }

    public JButton getJbUpg() {
        return jbUpg;
    }

    public JLabel getJlCounter() {
        return jlCounter;
    }

    public JTable getJtTable() {
        return jtTable;
    }

    /**
     * Updates the counter label to display the given coffee count.
     * @param numCoffees the current coffee count to display
     */
    public void updateCounter(double numCoffees) {
        jlCounter.setText(String.format("%.2f", numCoffees));
        jlCounter.revalidate();
        jlCounter.repaint();
    }
}