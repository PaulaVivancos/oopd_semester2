package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends BaseView {
    private JImagePanel jipMain;
    private JPanel jpTop, jpBot, jpCentre, jpEast, jpWest;
    private JImagePanel jipTitle, jipCoffeeCup, jipCoffeeCupSmall;
    private JButton jbBuy, jbGen, jbUpg;
    private JLabel jlCounter;
    private JTable jtTable;

    // DIMENSION CONSTANTS
    private final Dimension DIMENSION_BUTTON = new Dimension(150, 50);

    // COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    // IMAGES
    private static final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";
    private static final String TITLE_URL = "src/Presentation/Images/title.png";
    private static final String COFFEE_CUP = "src/Presentation/Images/coffee_cup.png";

    public GameView() {
        super(); // llama a initMenu() + initComponents()
    }

    // ── Menú ─────────────────────────────────────────────────────

    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Guardar partida", e -> System.out.println("save"));
        addMenuItem(menu, "Cargar partida", e -> System.out.println("load"));
        menu.addSeparator();
        addMenuItem(menu, "Volver al menú", e -> System.out.println("go home"));
        addMenuItem(menu, "Salir", e -> System.exit(0));
    }

    @Override
    protected void initComponents() {
        // Inicialización de paneles
        jipMain = new JImagePanel(BACKGROUND_URL);
        jpTop = new JPanel();
        jpCentre = new JPanel();
        jpBot = new JPanel();
        jpEast = new JPanel();
        jpWest = new JPanel();

        // Inicialización de imágenes
        jipTitle = new JImagePanel(TITLE_URL);
        jipCoffeeCup = new JImagePanel(COFFEE_CUP);
        jipCoffeeCupSmall = new JImagePanel(COFFEE_CUP);

        // Inicialización de otros componentes
        jlCounter = new JLabel("100.00");
        jtTable = new JTable();

        // Botones
        jbBuy = new JButton("BUY COFFEE");
        jbGen = new JButton("GENERATORS");
        jbUpg = new JButton("UPGRADES");

        // Sets everything
        setJipMain();
    }

    // ── Layout interno ────────────────────────────────────────────

    private void setJipMain() {
        setJpTop();
        setJpCentre();
        setJpBot();
        setJpEast();
        setJpWest();

        jipMain.setLayout(new BorderLayout());
        jipMain.setOpacityValue(0.5f);
        jipMain.add(jpTop, BorderLayout.NORTH);
        jipMain.add(jpCentre, BorderLayout.CENTER);
        jipMain.add(jpBot, BorderLayout.SOUTH);
        jipMain.add(jpEast, BorderLayout.EAST);
        jipMain.add(jpWest, BorderLayout.WEST);

        // BaseView ya tiene BorderLayout y NORTH ocupado por el menuButton,
        // así que el contenido va en CENTER
        add(jipMain, BorderLayout.CENTER);
    }

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

    private void setJpBot() {
        jpBot.setPreferredSize(new Dimension(600, 200));
        jpBot.setMaximumSize(new Dimension(600, 200));
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

    private void setJpCentre() {
        jpCentre.setLayout(new BoxLayout(jpCentre, BoxLayout.Y_AXIS));
        jpCentre.setOpaque(false);

        jipCoffeeCup.setAlignmentX(Component.CENTER_ALIGNMENT);
        jipCoffeeCup.setOpaque(false);
        jipCoffeeCup.setPreferredSize(new Dimension(250, 300));
        jipCoffeeCup.setMaximumSize(new Dimension(250, 300));

        jpCentre.add(jipCoffeeCup);
    }

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

    private void setJpWest() {
        jpWest.setLayout(new FlowLayout());
        jpWest.setOpaque(false);
        jpWest.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 20));

        jlCounter.setFont(new Font("Arial", Font.PLAIN, 30));

        jipCoffeeCupSmall.setOpaque(false);
        jipCoffeeCupSmall.setPreferredSize(new Dimension(50, 50));
        jipCoffeeCupSmall.setMaximumSize(new Dimension(50, 50));

        jpWest.add(jipCoffeeCupSmall);
        jpWest.add(jlCounter);
    }

    private void setButton(JButton button, Dimension dimension) {
        button.setPreferredSize(dimension);
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

    // ── Getters para el controlador ───────────────────────────────

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
}