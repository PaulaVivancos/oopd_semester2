package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;

public class StatsView extends BaseView {
    private JImagePanel mainPanel;
    private JPanel topPanel, playersPanel, userGamePanel, numGamesPanel;
    private JLabel jlPlayers, jlGames, jlNumGames;
    private JComboBox<String> jcbPlayers, jcbGames;
    private String[] playersOptions, gamesOptions;

    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    public StatsView() {
        super();
    }

    @Override
    protected void initComponents() {
        mainPanel = new JImagePanel(BACKGROUND_URL);

        topPanel = new JPanel();
        playersPanel = new JPanel();
        userGamePanel = new JPanel();
        numGamesPanel = new JPanel();

        jcbPlayers = new JComboBox<>(); // setter
        jcbGames = new JComboBox<>(); // setter

        jlPlayers = new JLabel("Select a player");
        jlGames = new JLabel("Select a game");
        jlNumGames = new JLabel();

        setMainPanel();
    }

    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Guardar partida", e -> System.out.println("save"));
    }

    private void setMainPanel() {
        setTopPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        setVisible(true);
    }

    private void setTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        setPlayersPanel();
        setGamesPanel();
        setNumGamesPanel(10);

        topPanel.setBorder(BorderFactory.createEmptyBorder(100, 130, 0, 100));
        topPanel.setOpaque(false);
        topPanel.add(playersPanel);
        topPanel.add(Box.createHorizontalStrut(100));
        topPanel.add(userGamePanel);
        topPanel.add(Box.createHorizontalStrut(100));
        topPanel.add(numGamesPanel);
    }

    private void setPlayersPanel() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setPreferredSize(new Dimension(200, 100));
        playersPanel.setMaximumSize(new Dimension(200, 100));


        jlPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);
        jlPlayers.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jcbPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);

        playersPanel.setOpaque(false);
        playersPanel.add(jlPlayers);
        playersPanel.add(jcbPlayers);
    }

    private void setGamesPanel() {
        userGamePanel.setLayout(new BoxLayout(userGamePanel, BoxLayout.Y_AXIS));
        userGamePanel.setPreferredSize(new Dimension(200, 100));
        userGamePanel.setMaximumSize(new Dimension(200, 100));

        jlGames.setAlignmentX(Component.LEFT_ALIGNMENT);
        jlGames.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jcbGames.setAlignmentX(Component.LEFT_ALIGNMENT);

        userGamePanel.setOpaque(false);
        userGamePanel.add(jlGames);
        userGamePanel.add(jcbGames);
    }

    private void setNumGamesPanel(int numGames) {
        numGamesPanel.setPreferredSize(new Dimension(200, 70));
        numGamesPanel.setMaximumSize(new Dimension(200, 70));
        numGamesPanel.setLayout(new GridBagLayout());

        jlNumGames.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jlNumGames.setText("Number of games: " + numGames);

        jlNumGames.setForeground(Color.WHITE);

        numGamesPanel.setBackground(BACKGROUND_BUTTON);
        numGamesPanel.add(jlNumGames, new GridBagConstraints());
    }

    public void setPlayersOptions(String[] players) {
        this.playersOptions = players;
    }

    public void setGamesOptions(String[] games) {
        this.gamesOptions = games;
    }

}