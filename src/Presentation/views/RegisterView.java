package Presentation.views;

import Presentation.JImagePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * View for the registration screen with fields for username, email, password, and password confirmation.
 */
public class RegisterView extends JPanel {
    private JImagePanel jpiMain;
    private JPanel jpCentral, jpUsername, jpPassword, jpButtons, jpEmail, jpPasswordConfirmation;
    private JLabel jlTitle;
    private JTextField jtfUsername, jtfEmail;
    private JPasswordField jtfPassword, jtfPasswordConfirmation;
    private JButton jbLogIn, jbSignUp;

    //DIMENSION CONSTANTS
    private final Dimension DIMENSION_TEXTFIELD = new Dimension(300, 50);
    private final Dimension DIMENSION_BUTTON_LOGIN = new Dimension(250, 40);
    private final Dimension DIMENSION_BUTTON_SIGNIN = new Dimension(250, 50);

    //COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    public static final String REGISTER_USER = "REGISTER_USER";
    public static final String GO_LOGIN = "GO_TO_LOGIN";
    private static final String SIGN_UP = "Sign up";
    private static final String EXISTING_ACCOUNT = "Already have an account. Log in.";
    //IMAGES
    private final String BACKGROUND_URL = "resources/background.jpg";

    /**
     * Constructs the register view and initializes all UI components.
     */
    public RegisterView() {
        setLayout(new BorderLayout());

        //Panels
        jpiMain = new JImagePanel(BACKGROUND_URL);
        jpCentral = new JPanel();
        jpUsername = new JPanel();
        jpPassword = new JPanel();
        jpButtons = new JPanel();
        jpEmail = new JPanel();
        jpPasswordConfirmation = new JPanel();

        //Labels
        jlTitle = new JLabel(SIGN_UP);

        //Text fields
        jtfUsername = new JTextField();
        jtfPassword = new JPasswordField();
        jtfEmail = new JTextField();
        jtfPasswordConfirmation = new JPasswordField();


        //Buttons
        jbLogIn = new JButton(EXISTING_ACCOUNT);
        jbSignUp = new JButton(SIGN_UP);

        jbLogIn.setActionCommand(GO_LOGIN);
        jbSignUp.setActionCommand(REGISTER_USER);

        setMainPanel();
    }

    /**
     * Registers a listener for the sign up and log in buttons.
     * @param actionListener the ActionListener to invoke on click
     */
    public void addListeners(ActionListener actionListener) {
        jbLogIn.addActionListener(actionListener);
        jbSignUp.addActionListener(actionListener);
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
     * Lays out all input field panels and the sign-up button vertically.
     */
    private void setCenterPanel() {
        setUsernamePanel();
        setPasswordPanel();
        setEmailPanel();
        setPasswordConfirmationPanel();
        setSignUpButton();

        jpCentral.setLayout(new BoxLayout(jpCentral, BoxLayout.Y_AXIS));
        jpCentral.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        jpCentral.setOpaque(false);

        jpUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpPasswordConfirmation.setAlignmentX(Component.CENTER_ALIGNMENT);

        jpCentral.add(jpUsername);
        jpCentral.add(jpEmail);
        jpCentral.add(jpPassword);
        jpCentral.add(jpPasswordConfirmation);
        jpCentral.add(jbSignUp);
    }

    /**
     * Builds the username field panel with its label.
     */
    private void setUsernamePanel() {
        jpUsername.setLayout(new BoxLayout(jpUsername, BoxLayout.Y_AXIS));
        jpUsername.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel topLabel = new JLabel("Username: ");
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
     * Builds the password confirmation field panel with its label.
     */
    private void setPasswordConfirmationPanel() {
        jpPasswordConfirmation.setLayout(new BoxLayout(jpPasswordConfirmation, BoxLayout.Y_AXIS));
        jpPasswordConfirmation.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        jpPasswordConfirmation.setOpaque(false);

        JLabel topLabel = new JLabel("Password confirmation: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setPasswordConfirmationTextField();

        jpPasswordConfirmation.add(topLabel);
        jpPasswordConfirmation.add(jtfPasswordConfirmation);
    }

    /**
     * Builds the email field panel with its label.
     */
    private void setEmailPanel() {
        jpEmail.setLayout(new BoxLayout(jpEmail, BoxLayout.Y_AXIS));
        jpEmail.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel topLabel = new JLabel("Email: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setEmailTextField();

        jpEmail.setOpaque(false);
        jpEmail.add(topLabel);
        jpEmail.add(jtfEmail);
    }

    /**
     * Configures appearance and focus color effects for the username text field.
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
     * Configures appearance and focus color effects for the password field.
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
     * Configures appearance and focus color effects for the password confirmation field.
     */
    private void setPasswordConfirmationTextField() {
        jtfPasswordConfirmation.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtfPasswordConfirmation.setPreferredSize(DIMENSION_TEXTFIELD);
        jtfPasswordConfirmation.setMaximumSize(DIMENSION_TEXTFIELD);
        jtfPasswordConfirmation.setBackground(BACKGROUND_BUTTON);
        jtfPasswordConfirmation.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfPasswordConfirmation.setBackground(BACKGROUND_BUTTON_PRESSED);
                jtfPasswordConfirmation.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                jtfPasswordConfirmation.setBackground(BACKGROUND_BUTTON);
                jtfPasswordConfirmation.setForeground(Color.WHITE);
            }
        });
        jtfPasswordConfirmation.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
    }

    /**
     * Configures appearance and focus color effects for the email text field.
     */
    private void setEmailTextField() {
        jtfEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtfEmail.setPreferredSize(DIMENSION_TEXTFIELD);
        jtfEmail.setMaximumSize(DIMENSION_TEXTFIELD);
        jtfEmail.setBackground(BACKGROUND_BUTTON);
        jtfEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfEmail.setBackground(BACKGROUND_BUTTON_PRESSED);
                jtfEmail.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                jtfEmail.setBackground(BACKGROUND_BUTTON);
                jtfEmail.setForeground(Color.WHITE);
            }
        });
        jtfEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
    }

    /**
     * Configures the size, alignment, and press effect of the sign-up button.
     */
    private void setSignUpButton() {
        jbSignUp.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbSignUp.setMaximumSize(DIMENSION_BUTTON_LOGIN);
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
     * Arranges the log-in redirect button at the bottom with spacing.
     */
    private void setButtons() {
        jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.Y_AXIS));
        jpButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        jpButtons.setOpaque(false);

        setSignUpButton();
        setLogInButton();

        jpButtons.add(Box.createVerticalStrut(25));
        jpButtons.add(jbLogIn);
    }

    /**
     * Configures the size and alignment of the log-in redirect button.
     */
    private void setLogInButton() {
        jbLogIn.setPreferredSize(DIMENSION_BUTTON_SIGNIN);
        jbLogIn.setMaximumSize(DIMENSION_BUTTON_SIGNIN);
        jbLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogIn.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbLogIn.setForeground(Color.WHITE);
        jbLogIn.setBackground(BACKGROUND_BUTTON);
        jbLogIn.setOpaque(true);
        jbLogIn.setContentAreaFilled(true);
        jbLogIn.setFocusPainted(false);
        jbLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));


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
     * @return the text entered in the username field
     */
    public String getUsername() {
        return jtfUsername.getText();
    }

    /**
     * @return the text entered in the password field
     */
    public String getPassword() {
        return new String(jtfPassword.getPassword());
    }

    /**
     * @return the text entered in the email field
     */
    public String getEmail() {
        return jtfEmail.getText();
    }

    /**
     * @return the text entered in the password confirmation field
     */
    public String getPasswordConfirmation() {
        return new String(jtfPasswordConfirmation.getPassword());
    }

    /**
     * Displays an error dialog with the given message.
     * @param message the error message to display
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}