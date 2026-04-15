package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JPanel {
    private JImagePanel jpiMain;
    private JPanel jpCentral, jpUsername, jpPassword, jpButtons, jpLogin;
    private JLabel jlTitle;
    private JButton jbLogIn, jbSignUp, jbForgotPassword;
    private JTextField jtfUsername;
    private JPasswordField jtfPassword;

    //DIMENSION CONSTANTS
    private final Dimension DIMENSION_TEXTFIELD = new Dimension(300, 50);
    private final Dimension DIMENSION_BUTTON_LOGIN = new Dimension(150, 40);
    private final Dimension DIMENSION_BUTTON_SIGNIN = new Dimension(250, 50);

    //COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    //IMAGES
    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    public LoginView() {
        setLayout(new BorderLayout());

        //Panels
        jpiMain = new JImagePanel(BACKGROUND_URL);
        jpCentral = new JPanel();
        jpUsername = new JPanel();
        jpPassword = new JPanel();
        jpButtons = new JPanel();
        jpLogin = new JPanel();

        //Labels
        jlTitle = new JLabel("LOG IN");

        //Buttons
        jbLogIn = new JButton("Log In");
        jbSignUp = new JButton("Don't have an account yet? \nSign up");
        jbForgotPassword = new JButton("Forgot Password?");

        //Text fields
        jtfUsername = new JTextField();
        jtfPassword = new JPasswordField();

        setMainPanel();
    }

    private void setMainPanel() {
        setTitle();
        setCenterPanel();
        setButtons();

        jpiMain.setLayout(new BorderLayout());
        jpiMain.setOpacityValue(0.5f);
        jpiMain.add(jlTitle, BorderLayout.NORTH);
        jpiMain.add(jpCentral, BorderLayout.CENTER);
        jpiMain.add(jpButtons, BorderLayout.SOUTH);

        add(jpiMain, BorderLayout.CENTER);
    }

    private void setTitle() {
        jlTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        jlTitle.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
        jlTitle.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setCenterPanel() {
        setUsernamePanel();
        setPasswordPanel();
        setLogInPanel();

        jpCentral.setLayout(new BoxLayout(jpCentral, BoxLayout.Y_AXIS));
        jpCentral.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        jpCentral.setOpaque(false);

        jpUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        jpCentral.add(jpUsername);
        jpCentral.add(jpPassword);
        jpCentral.add(jpLogin);
    }

    private void setUsernamePanel() {
        jpUsername.setLayout(new BoxLayout(jpUsername, BoxLayout.Y_AXIS));
        jpUsername.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel topLabel = new JLabel("Username / Email: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setUsernameTextField();

        jpUsername.setOpaque(false);
        jpUsername.add(topLabel);
        jpUsername.add(jtfUsername);
    }

    private void setPasswordPanel() {
        jpPassword.setLayout(new BoxLayout(jpPassword, BoxLayout.Y_AXIS));
        jpPassword.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        jpPassword.setOpaque(false);

        JLabel topLabel = new JLabel("Password: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setPasswordTextField();

        jpPassword.add(topLabel);
        jpPassword.add(jtfPassword);
    }

    private void setUsernameTextField() {
        jtfUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtfUsername.setPreferredSize(DIMENSION_TEXTFIELD);
        jtfUsername.setMaximumSize(DIMENSION_TEXTFIELD);
        jtfUsername.setBackground(BACKGROUND_BUTTON);
        jtfUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfUsername.setBackground(BACKGROUND_BUTTON_PRESSED);
                jtfUsername.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                jtfUsername.setBackground(BACKGROUND_BUTTON);
                jtfUsername.setForeground(Color.WHITE);
            }
        });
        jtfUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        }

    private void setPasswordTextField() {
        jtfPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtfPassword.setPreferredSize(DIMENSION_TEXTFIELD);
        jtfPassword.setMaximumSize(DIMENSION_TEXTFIELD);
        jtfPassword.setBackground(BACKGROUND_BUTTON);
        jtfPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfPassword.setBackground(BACKGROUND_BUTTON_PRESSED);
                jtfPassword.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                jtfPassword.setBackground(BACKGROUND_BUTTON);
                jtfPassword.setForeground(Color.WHITE);
            }
        });
        jtfPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
    }

    private void setLogInPanel() {
        jpLogin = new JPanel(new FlowLayout(FlowLayout.CENTER));

        jbLogIn.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogIn.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbLogIn.setForeground(Color.WHITE);
        jbLogIn.setBackground(BACKGROUND_BUTTON);
        jpLogin.setOpaque(false);
        jbLogIn.setOpaque(true);
        jbLogIn.setContentAreaFilled(true);
        jpLogin.add(jbLogIn);
        jbLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbLogIn.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbLogIn.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    private void setButtons() {
        jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.Y_AXIS));
        jpButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        jpButtons.setOpaque(false);

        setSignInButton();
        setForgotPasswordButton();

        jpButtons.add(jbSignUp);
        jpButtons.add(Box.createVerticalStrut(10));
        jpButtons.add(jbForgotPassword);
    }

    private void setSignInButton() {
        jbSignUp.setPreferredSize(DIMENSION_BUTTON_SIGNIN);
        jbSignUp.setMaximumSize(DIMENSION_BUTTON_SIGNIN);
        jbSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbSignUp.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbSignUp.setBackground(BACKGROUND_BUTTON);
        jbSignUp.setOpaque(true);
        jbSignUp.setContentAreaFilled(true);
    }

    private void setForgotPasswordButton() {
        jbForgotPassword.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbForgotPassword.setMaximumSize(DIMENSION_BUTTON_LOGIN);
        jbForgotPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbForgotPassword.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbForgotPassword.setBackground(BACKGROUND_BUTTON);
        jbForgotPassword.setOpaque(true);
        jbForgotPassword.setContentAreaFilled(true);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addLoginListener(ActionListener listener) {
        jbLogIn.addActionListener(listener);
    }

    public void addForgotPasswordListener(ActionListener listener) {
        jbForgotPassword.addActionListener(listener);
    }

    public String getUsernameEmail() {
        return jtfUsername.getText();
    }

    public String getPassword() {
        return new String(jtfPassword.getPassword());
    }

    public JButton getSingUpButton() {
        return jbSignUp;
    }
}