package Presentation.views;

import Presentation.JImagePanel;
import Presentation.PaintChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;

public class StatsView extends BaseView {
    private JPanel mainPanel;
    private JPanel topPanel, playersPanel, userGamePanel, numGamesPanel;
    private JLabel jlPlayers, jlGames, jlNumGames;

    // 1. Configurados estrictamente como Integer
    private JComboBox<Integer> jcbPlayers, jcbGames;

    private PaintChart paintChart;
    private final String BACKGROUND_URL = "resources/background.jpg";

    private ActionListener logoutListener;
    public void addLogoutListener(ActionListener l) { this.logoutListener = l; }

    public StatsView() {
        super();
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

    private void setMainPanel() {
        setTopPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

        paintChart = new PaintChart();
        mainPanel.add(paintChart, BorderLayout.CENTER);

        addToCenter(mainPanel);
    }

    public void setChart(PaintChart chart) {
        if (paintChart != null) {
            mainPanel.remove(paintChart);
        }
        paintChart = chart;
        mainPanel.add(paintChart, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

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

    public int getSelectedPlayerId() {
        Integer selected = (Integer) jcbPlayers.getSelectedItem();
        return selected != null ? selected : -1;
    }

    public int getSelectedGameId() {
        Integer selected = (Integer) jcbGames.getSelectedItem();
        return selected != null ? selected : -1;
    }

    public void setPlayersOptions(List<Integer> players) {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (Integer player : players) {
            model.addElement(player);
        }
        jcbPlayers.setModel(model);
        jcbPlayers.setSelectedIndex(-1);
    }

    public void setGamesOptions(List<Integer> games) {
        jcbGames.removeAllItems();

        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (Integer game : games) {
            model.addElement(game);
        }
        jcbGames.setModel(model);

        jcbGames.setSelectedIndex(-1);
    }

    public void addComboBoxListeners(ActionListener playerListener, ActionListener gameListener) {
        jcbPlayers.addActionListener(playerListener);
        jcbGames.addActionListener(gameListener);
    }

    public void removeComboBoxListeners(ActionListener playerListener, ActionListener gameListener) {
        jcbPlayers.removeActionListener(playerListener);
        jcbGames.removeActionListener(gameListener);
    }

    public void setTotalGamesCount(int count) {
        jlNumGames.setText("Number of games: " + count);
    }

    public void setGameComboBoxEnabled(boolean enabled) {
        jcbGames.setEnabled(enabled);
    }

    public void clearGamesOptions() {
        jcbGames.removeAllItems();
    }

}