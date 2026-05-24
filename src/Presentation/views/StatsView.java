package Presentation.views;

import Presentation.PaintChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

/**
 * View for the statistics screen, showing player and game selectors alongside a coffee chart.
 */
public class StatsView extends BaseView {
    private JPanel mainPanel;
    private JPanel topPanel, playersPanel, userGamePanel, numGamesPanel;
    private JLabel jlPlayers, jlGames, jlNumGames;

    private JComboBox<String> jcbPlayers;
    private JComboBox<Integer> jcbGames;

    private PaintChart paintChart;

    private ActionListener logoutListener;

    /**
     * Constructs the stats view.
     */
    public StatsView() {
        super(false);
    }

    @Override
    protected void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);

        topPanel = new JPanel();
        playersPanel = new JPanel();
        userGamePanel = new JPanel();
        numGamesPanel = new JPanel();

        jcbPlayers = new JComboBox<>();
        jcbGames = new JComboBox<>();

        jlPlayers = new JLabel("Select a player");
        jlGames = new JLabel("Select a game");
        jlNumGames = new JLabel();

        setMainPanel();
    }

    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Log out", e -> { if (logoutListener != null) logoutListener.actionPerformed(e); });
    }

    /**
     * Assembles the main panel with the top selector bar and chart area.
     */
    private void setMainPanel() {
        setTopPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

        paintChart = new PaintChart();
        mainPanel.add(paintChart, BorderLayout.CENTER);

        addToCenter(mainPanel);
    }

    /**
     * Replaces the current chart with the given one and repaints the panel.
     * @param chart the new {@link PaintChart} to display
     */
    public void setChart(PaintChart chart) {
        if (paintChart != null) {
            mainPanel.remove(paintChart);
        }
        paintChart = chart;
        mainPanel.add(paintChart, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Builds the top panel containing player, game, and total games count selectors.
     */
    private void setTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        setPlayersPanel();
        setGamesPanel();
        setNumGamesPanel(10);

        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 130, 30, 100));
        topPanel.setOpaque(false);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        topPanel.setPreferredSize(new Dimension(0, 120));

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
     * Builds the total games count panel displaying the given count.
     * @param numGames the number of games to display initially
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

    /**
     * @return the selected game ID, or -1 if none is selected
     */
    public int getSelectedGameId() {
        Integer selected = (Integer) jcbGames.getSelectedItem();
        return selected != null ? selected : -1;
    }

    /**
     * Populates the players combo box with the given list of usernames.
     * @param players the list of player usernames
     */
    public void setPlayersOptions(List<String> players) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String player : players) {
            model.addElement(player);
        }
        jcbPlayers.setModel(model);
        jcbPlayers.setSelectedIndex(-1);
    }

    /**
     * @return the currently selected player username, or null if none
     */
    public String getSelectedPlayer() {
        return (String) jcbPlayers.getSelectedItem();
    }

    /**
     * Pre-selects the given username in the players combo box.
     * @param username the username to select
     */
    public void selectPlayer(String username) {
            jcbPlayers.setSelectedItem(username != null ? username.trim() : null);
    }

    /**
     * Populates the games combo box with the given list of game IDs.
     * @param games the list of game IDs to display
     */
    public void setGamesOptions(List<Integer> games) {
        jcbGames.removeAllItems();

        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        if (games.isEmpty()) {
            jcbGames.setModel(model);
            jcbGames.setEnabled(false);
        } else {
            for (Integer game : games) {
                model.addElement(game);
            }
            jcbGames.setEnabled(true);
            jcbGames.setModel(model);
            jcbGames.setSelectedIndex(-1);
        }
    }

    /**
     * Attaches listeners to the player and game combo boxes.
     * @param playerListener the listener for player selection changes
     * @param gameListener   the listener for game selection changes
     */
    public void addComboBoxListeners(ActionListener playerListener, ActionListener gameListener) {
        jcbPlayers.addActionListener(playerListener);
        jcbGames.addActionListener(gameListener);
    }

    /**
     * Removes listeners from the player and game combo boxes.
     * @param playerListener the listener to remove from the player combo box
     * @param gameListener   the listener to remove from the game combo box
     */
    public void removeComboBoxListeners(ActionListener playerListener, ActionListener gameListener) {
        jcbPlayers.removeActionListener(playerListener);
        jcbGames.removeActionListener(gameListener);
    }

    /**
     * Updates the total games count label.
     * @param count the number of games to display
     */
    public void setTotalGamesCount(int count) {
        jlNumGames.setText("Number of games: " + count);
    }

    /**
     * Enables or disables the game combo box.
     * @param enabled true to enable, false to disable
     */
    public void setGameComboBoxEnabled(boolean enabled) {
        jcbGames.setEnabled(enabled);
    }

    /**
     * Removes all items from the games combo box.
     */
    public void clearGamesOptions() {
        jcbGames.removeAllItems();
    }

}