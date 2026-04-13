package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView extends BaseView {
    private JImagePanel jpiMain;
    private JPanel jpCentral, jpUsername, jpPassword, jpButtons, jpEmail, jpPasswordConfirmation;
    private JPanel jpLogin;
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

    //IMAGES
    private final String BACKGROUND_URL = "src/Presentation/Images/background.jpg";

    public RegisterView() {
        super();
    }

    @Override
    protected void buildMenu(JPopupMenu menu) {
        addMenuItem(menu, "Ir al login", e -> System.out.println("go login"));
    }

    @Override
    protected void initComponents() {
        //Panels
        jpiMain = new JImagePanel(BACKGROUND_URL);
        jpCentral = new JPanel();
        jpUsername = new JPanel();
        jpPassword = new JPanel();
        jpButtons = new JPanel();
        jpEmail = new JPanel();
        jpPasswordConfirmation = new JPanel();
        jpLogin = new JPanel();

        //Labels
        jlTitle = new JLabel("SIGN UP");

        //Text fields
        jtfUsername = new JTextField();
        jtfPassword = new JPasswordField();
        jtfEmail = new JTextField();
        jtfPasswordConfirmation = new JPasswordField();

        //Buttons
        jbLogIn = new JButton("Already have an account. Log in.");
        jbSignUp = new JButton("SIGN UP");

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

    private void setSignUpButton() {
        jbSignUp.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbSignUp.setMaximumSize(DIMENSION_BUTTON_LOGIN);
        jbSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbSignUp.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbSignUp.setBackground(BACKGROUND_BUTTON);
        jbSignUp.setOpaque(true);
        jbSignUp.setContentAreaFilled(true);
        jbSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbSignUp.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbSignUp.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    private void setButtons() {
        jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.Y_AXIS));
        jpButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        jpButtons.setOpaque(false);

        setSignUpButton();
        setLogInButton();

        jpButtons.add(Box.createVerticalStrut(25));
        jpButtons.add(jbLogIn);
    }

    private void setLogInButton() {
        jbLogIn.setPreferredSize(DIMENSION_BUTTON_SIGNIN);
        jbLogIn.setMaximumSize(DIMENSION_BUTTON_SIGNIN);
        jbLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbLogIn.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbLogIn.setBackground(BACKGROUND_BUTTON);
        jbLogIn.setOpaque(true);
        jbLogIn.setContentAreaFilled(true);
    }

    public JButton getLogInButton() {
        return jbLogIn;
    }
}