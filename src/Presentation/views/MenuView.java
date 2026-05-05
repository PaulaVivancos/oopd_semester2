package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JPanel {
    private JImagePanel jipMain;
    private JPanel jpTop, jpCentre, jpBot;
    private JImagePanel jipTitleTop, jipTitleBottom, jipCoffeeCup;
    private JButton jbPlay, jbStats, jbConfig, jbLogOut;

    //DIMENSION CONSTANTS
    private final Dimension DIMENSION_BUTTON = new Dimension(150, 50);

    //COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    public static final String GO_GAME = "GO_TO_GAME";
    public static final String GO_STATS = "GO_TO_STATS";
    public static final String GO_CONFIG = "GO_TO_CONFIG";
    public static final String LOGOUT = "LOGOUT";

    //IMAGES
    private final String BACKGROUND_URL = "resources/background.jpg";
    private final String TITLE_TOP_URL = "resources/welcome_to.png";
    private final String TITLE_BOT_URL = "resources/title.png";
    private final String COFFEE_CUP = "resources/coffee_cup.png";

    public MenuView() {
        setLayout(new BorderLayout());

        //panels
        jipMain    = new JImagePanel(BACKGROUND_URL);
        jpTop      = new JPanel();
        jpCentre   = new JPanel();
        jpBot      = new JPanel();

        //images
        jipTitleTop    = new JImagePanel(TITLE_TOP_URL);
        jipTitleBottom = new JImagePanel(TITLE_BOT_URL);
        jipCoffeeCup   = new JImagePanel(COFFEE_CUP);

        //buttons
        jbPlay    = new JButton("PLAY");
        jbStats   = new JButton("STATS");
        jbConfig  = new JButton("CONFIG");
        jbLogOut  = new JButton("LOG OUT");

        jbPlay.setActionCommand(GO_GAME);
        jbStats.setActionCommand(GO_STATS);
        jbConfig.setActionCommand(GO_CONFIG);
        jbLogOut.setActionCommand(LOGOUT);

        setJipMain();
    }

    public void addAuthListeners(ActionListener actionListener) {
        jbLogOut.addActionListener(actionListener);
        jbConfig.addActionListener(actionListener);
    }

    public void addStatsListener(ActionListener actionListener) {
        jbStats.addActionListener(actionListener);
    }

    public void addGameListener(ActionListener actionListener) {
        jbPlay.addActionListener(actionListener);
    }

    private void setJipMain() {
        setJpTop();
        setJpCentre();
        setJpBot();

        jipMain.setLayout(new BorderLayout());
        jipMain.setOpacityValue(0.5f);
        jipMain.add(jpTop,    BorderLayout.NORTH);
        jipMain.add(jpCentre, BorderLayout.CENTER);
        jipMain.add(jpBot,    BorderLayout.SOUTH);

        add(jipMain, BorderLayout.CENTER);
    }

    private void setJpTop() {
        jpTop.setLayout(new BoxLayout(jpTop, BoxLayout.Y_AXIS));
        jpTop.setOpaque(false);
        jpTop.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        jpTop.setPreferredSize(new Dimension(getWidth(), 200));

        jipTitleTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        jipTitleTop.setOpaque(false);
        jipTitleBottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        jipTitleBottom.setOpaque(false);
        jipTitleTop.setPreferredSize(new Dimension(400, 80));
        jipTitleTop.setMaximumSize(new Dimension(400, 80));
        jipTitleBottom.setPreferredSize(new Dimension(600, 100));
        jipTitleBottom.setMaximumSize(new Dimension(600, 100));

        jpTop.add(jipTitleTop);
        jpTop.add(Box.createVerticalStrut(10));
        jpTop.add(jipTitleBottom);
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

    private void setJpBot() {
        jpBot.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
        jpBot.setOpaque(false);
        jpBot.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        setButton(jbPlay,    DIMENSION_BUTTON);
        jbPlay.setBackground(BACKGROUND_BUTTON);
        setButton(jbStats,   DIMENSION_BUTTON);
        setButton(jbConfig,  DIMENSION_BUTTON);
        setButton(jbLogOut,  DIMENSION_BUTTON);

        jpBot.add(jbPlay);

        jpBot.add(jbStats);
        jpBot.add(jbConfig);
        jpBot.add(jbLogOut);
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

    private void setButton(JButton button, Dimension dimension) {
        button.setPreferredSize(dimension);
        button.setMaximumSize(dimension);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);

        button.setOpaque(true);
        button.setBackground(BACKGROUND_BUTTON);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(true);

    }

    public void addPlayListener(ActionListener actionListener) {
        jbPlay.addActionListener(actionListener);
    }

    public JButton getGameButton()   { return jbPlay; }
    public JButton getStatsButton()  { return jbStats; }
    public JButton getConfigButton() { return jbConfig; }
    public JButton getLogOutButton() { return jbLogOut; }

}