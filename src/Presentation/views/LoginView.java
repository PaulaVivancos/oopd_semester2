package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * View for the login screen.
 * Provides username/email and password fields with login, sign-up, and forgot-password actions.
 */
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
    private final Dimension DIMENSION_BUTTON_SIGNIN = new Dimension(280, 50);

    //COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    // ACTION COMMANDS CONSTANTS
    public static final String LOGIN_USER = "LOGIN_USER";
    public static final String GO_REGISTER = "GO_TO_REGISTER";
    public static final String GO_FORGOT_PASSWORD = "GO_TO_FORGOT_PASSWORD";

    //IMAGES
    private final String BACKGROUND_URL = "resources/background.jpg";

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

        // ATTACH ACTION COMMANDS
        jbLogIn.setActionCommand(LOGIN_USER);
        jbSignUp.setActionCommand(GO_REGISTER);
        jbForgotPassword.setActionCommand(GO_FORGOT_PASSWORD);

        //Text fields
        jtfUsername = new JTextField();
        jtfPassword = new JPasswordField();

        setMainPanel();
    }

    /**
     * Assembles the background panel with title, input fields, and bottom buttons.
     */
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

    /**
     * Styles and centers the title label.
     */
    private void setTitle() {
        jlTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        jlTitle.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
        jlTitle.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * Lays out the username, password, and login button panels vertically.
     */
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

    /**
     * Builds the username field panel with its label.
     */
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

    /**
     * Builds the password field panel with its label.
     */
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

    /**
     * Configures the appearance and focus color effects of the username text field.
     */
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

    /**
     * Configures the appearance and focus color effects of the password field.
     */
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

    /**
     * Builds and styles the log-in button panel.
     */
    private void setLogInPanel() {
        jpLogin = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jpLogin.setOpaque(false);

        jbLogIn.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogIn.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbLogIn.setForeground(Color.WHITE);
        jbLogIn.setBackground(BACKGROUND_BUTTON);
        jbLogIn.setOpaque(true);
        jbLogIn.setContentAreaFilled(true);
        jbLogIn.setFocusPainted(false);
        jbLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jpLogin.add(jbLogIn);

        jbLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbLogIn.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbLogIn.setForeground(Color.BLACK);
                jbLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbLogIn.setBackground(BACKGROUND_BUTTON);
                jbLogIn.setForeground(Color.WHITE);
                jbLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });
    }

    /**
     * Arranges the sign-up and forgot-password buttons at the bottom with spacing.
     */
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

    /**
     * Configures the size and alignment of the sign-up button.
     * */
    private void setSignInButton() {
        jbSignUp.setPreferredSize(DIMENSION_BUTTON_SIGNIN);
        jbSignUp.setMaximumSize(DIMENSION_BUTTON_SIGNIN);
        jbSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbSignUp.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbSignUp.setForeground(Color.WHITE);
        jbSignUp.setBackground(BACKGROUND_BUTTON);
        jbSignUp.setOpaque(true);
        jbSignUp.setContentAreaFilled(true);
        jbSignUp.setFocusPainted(false);
        jbSignUp.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jbSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbSignUp.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbSignUp.setForeground(Color.BLACK);
                jbSignUp.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbSignUp.setBackground(BACKGROUND_BUTTON);
                jbSignUp.setForeground(Color.WHITE);
                jbSignUp.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });
    }

    /**
     * Configures the size and alignment of the forgot-password button.
     */
    private void setForgotPasswordButton() {
        jbForgotPassword.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbForgotPassword.setMaximumSize(DIMENSION_BUTTON_LOGIN);
        jbForgotPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbForgotPassword.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbForgotPassword.setForeground(Color.WHITE);
        jbForgotPassword.setBackground(BACKGROUND_BUTTON);
        jbForgotPassword.setOpaque(true);
        jbForgotPassword.setContentAreaFilled(true);
        jbForgotPassword.setFocusPainted(false);
        jbForgotPassword.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jbForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbForgotPassword.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbForgotPassword.setForeground(Color.BLACK);
                jbForgotPassword.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbForgotPassword.setBackground(BACKGROUND_BUTTON);
                jbForgotPassword.setForeground(Color.WHITE);
                jbForgotPassword.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addLoginListener(ActionListener listener) {
        jbLogIn.addActionListener(listener);
    }

    public void addSignUpListener(ActionListener listener) {
        jbSignUp.addActionListener(listener);
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

    public void clearFields() {
        if (jtfUsername != null) {
            jtfUsername.setText("");
        }
        if (jtfPassword != null) {
            jtfPassword.setText("");
        }
    }
}