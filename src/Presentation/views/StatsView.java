package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;

public class StatsView extends BaseView {
    private JImagePanel mainPanel;
    private JPanel topPanel, playersPanel, gamesPanel;
    private JLabel jlPlayers, jlGames;
    private JComboBox<String> jcbPlayers, jcbGames;

    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    // TODO: setters of String[] playersOptions, String[] gamesOptions
    public StatsView() {
        super();
    }

    @Override
    protected void initComponents() {
        mainPanel = new JImagePanel(BACKGROUND_URL);
        topPanel = new JPanel();
        playersPanel = new JPanel();
        gamesPanel = new JPanel();
        jcbPlayers = new JComboBox<>(); // setter
        jcbGames = new JComboBox<>(); // setter
        jlPlayers = new JLabel("Select a player");
        jlGames = new JLabel("Select a game");

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
        mainPanel.setOpacityValue(0.5f);
        add(mainPanel);
        setVisible(true);
    }

    private void setTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        setPlayersPanel();
        setGamesPanel();

        topPanel.add(playersPanel);
        topPanel.add(gamesPanel);
    }

    private void setPlayersPanel() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));

        jlPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);
        jcbPlayers.setAlignmentX(Component.LEFT_ALIGNMENT);
        playersPanel.add(jlPlayers);
        playersPanel.add(jcbPlayers);
    }

    private void setGamesPanel() {
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        jlGames.setAlignmentX(Component.LEFT_ALIGNMENT);
        jcbGames.setAlignmentX(Component.LEFT_ALIGNMENT);
        gamesPanel.add(jlGames);
        gamesPanel.add(jcbGames);
    }

}