package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * View for the user configuration screen, displaying profile picture and
 * account management options including logout and account deletion.
 */
public class ConfigView extends BaseView {
    private final String PROFILE_PICTURE_URL = "resources/profile_picture.png";

    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    public static final String DELETE_ACCOUNT = "DELETE_ACCOUNT";
    public static final String LOGOUT = "LOGOUT";

    private JImagePanel ipProfile;
    private JPanel mainPanel;
    private JPanel buttonsPanel, centralPanel;
    private JButton jbLogOut, jbDeleteAccount;

    private ActionListener logoutListener;
    private ActionListener deleteListener;

    public void addLogoutListener(ActionListener l)  { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l)  { this.deleteListener = l; }

    public ConfigView() {
        super(false);

        setLogOutButton();
        setDeleteAccountButton();
    }

    /** Populates the top bar dropdown menu with configuration-related options. */
    @Override
    protected void buildMenu(JPopupMenu menu){
    }

    /** Initializes all UI components for the config screen. */
    @Override
    protected void initComponents() {
        //mainPanel = new JImagePanel(BACKGROUND_URL);
        mainPanel = new JPanel();

        centralPanel = new JPanel();
        ipProfile = new JImagePanel(PROFILE_PICTURE_URL);
        buttonsPanel = new JPanel();

        jbLogOut = new JButton("LOG OUT");
        jbDeleteAccount = new JButton("DELETE ACCOUNT");

        setMainPanel();
    }

    /** Wraps the central panel in a GridBagLayout container and adds it to the view. */
    private void setMainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel wrapper = new JPanel(new GridBagLayout());
        setCentralPanel();

        wrapper.add(centralPanel);
        wrapper.setOpaque(false);

        mainPanel.add(wrapper, BorderLayout.CENTER);
        addToCenter(mainPanel);
    }

    /** Lays out the profile image and buttons panel vertically in the center of the view. */
    private void setCentralPanel(){
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setOpaque(false);

        centralPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        setButtonsPanel();
        setProfileImage();
        centralPanel.add(ipProfile);
        centralPanel.add(Box.createVerticalStrut(100));
        centralPanel.add(buttonsPanel);

    }

    /** Arranges the language, logout, and delete account buttons vertically with spacing. */
    private void setButtonsPanel() {
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        buttonsPanel.add(jbLogOut);
        buttonsPanel.add(Box.createVerticalStrut(30));
        buttonsPanel.add(jbDeleteAccount);

        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    /** Configures the appearance, press effect, and logout action of the log out button. */
    private void setLogOutButton() {
        jbLogOut.setPreferredSize(new Dimension(200, 50));
        jbLogOut.setMaximumSize(new Dimension(200, 50));
        jbLogOut.setMinimumSize(new Dimension(200, 50));

        jbLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jbLogOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogOut.setForeground(Color.WHITE);
        jbLogOut.setBackground(BACKGROUND_BUTTON);
        jbLogOut.setOpaque(true);
        jbLogOut.setContentAreaFilled(true);
        jbLogOut.setFocusPainted(false);
        jbLogOut.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jbLogOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbLogOut.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbLogOut.setForeground(Color.BLACK);
                jbLogOut.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbLogOut.setBackground(BACKGROUND_BUTTON);
                jbLogOut.setForeground(Color.WHITE);
                jbLogOut.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });

        jbLogOut.setActionCommand(LOGOUT);
        jbLogOut.addActionListener(e -> { if (logoutListener != null) logoutListener.actionPerformed(e); });
    }

    /** Configures the appearance, press effect, and delete action of the delete account button. */
    private void setDeleteAccountButton() {
        jbDeleteAccount.setPreferredSize(new Dimension(300, 50));
        jbDeleteAccount.setMaximumSize(new Dimension(300, 50));
        jbDeleteAccount.setMinimumSize(new Dimension(300, 50));

        jbDeleteAccount.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jbDeleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbDeleteAccount.setForeground(Color.WHITE);
        jbDeleteAccount.setBackground(BACKGROUND_BUTTON);
        jbDeleteAccount.setOpaque(true);
        jbDeleteAccount.setContentAreaFilled(true);
        jbDeleteAccount.setFocusPainted(false);
        jbDeleteAccount.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jbDeleteAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbDeleteAccount.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbDeleteAccount.setForeground(Color.BLACK);
                jbDeleteAccount.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbDeleteAccount.setBackground(BACKGROUND_BUTTON);
                jbDeleteAccount.setForeground(Color.WHITE);
                jbDeleteAccount.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });

        jbDeleteAccount.setActionCommand(DELETE_ACCOUNT);
        jbDeleteAccount.addActionListener(e -> { if (deleteListener != null) deleteListener.actionPerformed(e); });
    }

    /** Sets the fixed size and alignment of the profile picture image panel. */
    private void setProfileImage() {
        ipProfile.setPreferredSize(new Dimension(200, 200));
        ipProfile.setMaximumSize(new Dimension(200, 200));
        ipProfile.setOpaque(false);

        ipProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipProfile.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

}
