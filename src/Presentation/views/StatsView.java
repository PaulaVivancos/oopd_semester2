package Presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View for the statistics screen.
 * Shows player and game selectors alongside overall game count info.
 */
public class StatsView extends BaseView {
    private JPanel mainPanel;
    private JPanel topPanel, playersPanel, userGamePanel, numGamesPanel;
    private JLabel jlPlayers, jlGames, jlNumGames;
    private JComboBox<String> jcbPlayers, jcbGames;
    private String[] playersOptions, gamesOptions;

    private final String BACKGROUND_URL = "resources/background.jpg";

    private ActionListener logoutListener;
    public void addLogoutListener(ActionListener l) { this.logoutListener = l; }

    public StatsView() {
        super();
    }

    /**
     * Initializes all panels, labels, and combo boxes for the stats screen.
     */
    @Override
    protected void initComponents() {
        //mainPanel = new JImagePanel(BACKGROUND_URL);
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);

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

    /**
     * Populates the top bar menu with a save option and logout action.
     */
    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Guardar partida", e -> System.out.println("save"));
        menu.addSeparator();
        addMenuItem(menu, "Log out", e -> { if (logoutListener != null) logoutListener.actionPerformed(e); });
    }

    /**
     * Assembles the main panel with the top stats bar and adds it to the view.
     */
    private void setMainPanel() {
        setTopPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
       // mainPanel.setOpacityValue(0.5f);
        addToCenter(mainPanel);
    }

    /**
     * Lays out the players, games, and number-of-games panels horizontally with spacing.
     */
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

    /**
     * Builds the player selector panel with its label and combo box.
     */
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

    /**
     * Builds the game selector panel with its label and combo box.
     */
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

    /**
     * Builds the panel displaying the total number of games played.
     * @param numGames the number to display
     */
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