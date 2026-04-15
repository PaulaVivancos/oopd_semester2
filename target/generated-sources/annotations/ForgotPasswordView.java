package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ForgotPasswordView extends JPanel {

    private JImagePanel jpiMain;
    private JPanel jpCentral, jpUsername, jpPassword, jpButtons, jpLogin;
    private JLabel jlTitle;
    private JButton jbValidateCode;
    private JTextField jtfCode;
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

    public ForgotPasswordView() {
        setLayout(new BorderLayout());

        //Panels
        jpiMain = new JImagePanel(BACKGROUND_URL);
        jpCentral = new JPanel();
        jpUsername = new JPanel();
        jpPassword = new JPanel();
        jpButtons = new JPanel();
        jpLogin = new JPanel();

        //Labels
        jlTitle = new JLabel("FORGOTTEN PASSWORD");

        //Buttons
        jbValidateCode = new JButton("Validate code");

        //Text fields
        jtfCode = new JTextField();
        jtfPassword = new JPasswordField();

        setMainPanel();
    }

    private void setMainPanel() {
        setTitle();
        setCenterPanel();

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
        setCodePanel();
        setLogInPanel();

        jpCentral.setLayout(new BoxLayout(jpCentral, BoxLayout.Y_AXIS));
        jpCentral.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        jpCentral.setOpaque(false);

        jpUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        jpCentral.add(jpUsername);
        jpCentral.add(jpLogin);
    }

    private void setCodePanel() {
        jpUsername.setLayout(new BoxLayout(jpUsername, BoxLayout.Y_AXIS));
        jpUsername.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel topLabel = new JLabel("Enter code: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setUsernameTextField();

        jpUsername.setOpaque(false);
        jpUsername.add(topLabel);
        jpUsername.add(jtfCode);
    }

    private void setUsernameTextField() {
        jtfCode.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtfCode.setPreferredSize(DIMENSION_TEXTFIELD);
        jtfCode.setMaximumSize(DIMENSION_TEXTFIELD);
        jtfCode.setBackground(BACKGROUND_BUTTON);
        jtfCode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfCode.setBackground(BACKGROUND_BUTTON_PRESSED);
                jtfCode.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                jtfCode.setBackground(BACKGROUND_BUTTON);
                jtfCode.setForeground(Color.WHITE);
            }
        });
        jtfCode.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
    }

    private void setLogInPanel() {
        jpLogin = new JPanel(new FlowLayout(FlowLayout.CENTER));

        jbValidateCode.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbValidateCode.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbValidateCode.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbValidateCode.setForeground(Color.WHITE);
        jbValidateCode.setBackground(BACKGROUND_BUTTON);
        jpLogin.setOpaque(false);
        jbValidateCode.setOpaque(true);
        jbValidateCode.setContentAreaFilled(true);
        jpLogin.add(jbValidateCode);

        jbValidateCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbValidateCode.setBackground(BACKGROUND_BUTTON_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbValidateCode.setBackground(BACKGROUND_BUTTON);
            }
        });
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addValidateCodeListener(ActionListener listener) {
        jbValidateCode.addActionListener(listener);
    }

    public void showEnterEmailPopUp() {

    }

    public String getCode() {
        return jtfCode.getText();
    }
}
