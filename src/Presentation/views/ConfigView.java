package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfigView extends BaseView {
    private final String PROFILE_PICTURE_URL = "src/Presentation/Images/profile_picture.png";
    private static final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    private JImagePanel ipProfile, mainPanel;
    private JPanel buttonsPanel, centralPanel;
    private JButton jbLanguage, jbLogOut, jbDeleteAccount;

    private ActionListener logoutListener;
    private ActionListener deleteListener;

    public void addLogoutListener(ActionListener l)  { this.logoutListener = l; }
    public void addDeleteListener(ActionListener l)  { this.deleteListener = l; }


    public ConfigView() {
        super();
    }

    @Override
    protected void buildMenu(JPopupMenu menu){
        addMenuItem(menu, "i don't know yet", e -> System.out.println("don't know yet"));
    }

    @Override
    protected void initComponents() {
        mainPanel = new JImagePanel(BACKGROUND_URL);
        mainPanel.setOpacityValue(0.5f);

        centralPanel = new JPanel();
        ipProfile = new JImagePanel(PROFILE_PICTURE_URL);
        buttonsPanel = new JPanel();

        jbLanguage = new JButton("LANGUAGE");
        jbLogOut = new JButton("LOG OUT");
        jbDeleteAccount = new JButton("DELETE ACCOUNT");

        setMainPanel();
    }

    private void setMainPanel() {
        mainPanel.setLayout(new BorderLayout());

        JPanel wrapper = new JPanel(new GridBagLayout());
        setCentralPanel();

        wrapper.add(centralPanel);
        wrapper.setOpaque(false);

        mainPanel.add(wrapper, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

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

    private void setButtonsPanel() {
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        setLanguageButton();
        setLogOutButton();
        setDeleteAccountButton();

        buttonsPanel.add(jbLanguage);
        buttonsPanel.add(Box.createVerticalStrut(30));
        buttonsPanel.add(jbLogOut);
        buttonsPanel.add(Box.createVerticalStrut(30));
        buttonsPanel.add(jbDeleteAccount);

        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    private void setLanguageButton() {
        jbLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jbLanguage.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLanguage.setBackground(BACKGROUND_BUTTON);
        jbLanguage.setOpaque(true);
        jbLanguage.setContentAreaFilled(true);

        jbLanguage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbLanguage.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbLanguage.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    private void setLogOutButton() {
        jbLogOut.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jbLogOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogOut.setBackground(BACKGROUND_BUTTON);
        jbLogOut.setOpaque(true);
        jbLogOut.setContentAreaFilled(true);

        jbLogOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbLogOut.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbLogOut.setBackground(BACKGROUND_BUTTON);
            }
        });
        jbLogOut.addActionListener(e -> { if (logoutListener != null) logoutListener.actionPerformed(e); });
    }

    private void setDeleteAccountButton() {
        jbDeleteAccount.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jbDeleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbDeleteAccount.setBackground(BACKGROUND_BUTTON);
        jbDeleteAccount.setOpaque(true);
        jbDeleteAccount.setContentAreaFilled(true);

        jbDeleteAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbDeleteAccount.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbDeleteAccount.setBackground(BACKGROUND_BUTTON);
            }
        });
        jbDeleteAccount.addActionListener(e -> { if (deleteListener != null) deleteListener.actionPerformed(e); });
    }

    private void setProfileImage() {
        ipProfile.setPreferredSize(new Dimension(200, 200));
        ipProfile.setMaximumSize(new Dimension(200, 200));
        ipProfile.setOpaque(false);

        ipProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipProfile.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

}
