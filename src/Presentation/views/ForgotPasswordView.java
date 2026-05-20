package Presentation.views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** View for the password recovery flow, allowing users to enter a reset code and set a new password. */
public class ForgotPasswordView extends JPanel {

    private JImagePanel jpiMain;
    private JPanel jpCentral, jpCode, jpButtons, jpValidateCode;
    private JLabel jlTitle;
    private JButton jbValidateCode, jbBackLogIn;
    private JTextField jtfCode, jtfEmail;
    private JPasswordField jtfNewPassword, jtfPasswordConfirmation;
    private String email, newPassword, confirmationPassword, code;

    //DIMENSION CONSTANTS
    private final Dimension DIMENSION_TEXTFIELD = new Dimension(300, 50);
    private final Dimension DIMENSION_BUTTON_LOGIN = new Dimension(150, 40);
    private final Dimension DIMENSION_BUTTON_SIGNIN = new Dimension(250, 50);

    //COLOR CONSTANTS
    private final Color BACKGROUND_BUTTON = new Color(103, 51, 25);
    private final Color BACKGROUND_BUTTON_PRESSED = new Color(214, 196, 171);

    //ACTION COMMANDS
    public static final String GO_BACK_LOGIN = "GO_BACK_LOGIN";
    public static final String VALIDATE_CODE = "VALIDATE_CODE";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";

    //IMAGES
    private final String BACKGROUND_URL = "resources/background.jpg";


    public ForgotPasswordView() {
        setLayout(new BorderLayout());

        //Panels
        jpiMain = new JImagePanel(BACKGROUND_URL);
        jpCentral = new JPanel();
        jpCode = new JPanel();
        jpButtons = new JPanel();
        jpValidateCode = new JPanel();

        //Labels
        jlTitle = new JLabel("FORGOTTEN PASSWORD");

        //Buttons
        jbValidateCode = new JButton("Validate code");
        jbBackLogIn = new JButton("GO BACK TO LOG IN");

        //Text fields
        jtfCode = new JTextField();
        jtfEmail = new JTextField();
        jtfNewPassword = new JPasswordField();
        jtfPasswordConfirmation = new JPasswordField();

        //Set action commands
        jbBackLogIn.setActionCommand(GO_BACK_LOGIN);
        jbValidateCode.setActionCommand(VALIDATE_CODE);

        setMainPanel();
    }

    /**
     * Assembles the background panel with title, central fields, and bottom buttons.
     */
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

    /**
     * Styles and centers the title label. *
     */
    private void setTitle() {
        jlTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        jlTitle.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
        jlTitle.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * Lays out the code input field and validate button vertically in the center area.
     */
    private void setCenterPanel() {
        setCodePanel();
        setValidateCodePanel();
        setButtons();

        jpCentral.setLayout(new BoxLayout(jpCentral, BoxLayout.Y_AXIS));
        jpCentral.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        jpCentral.setOpaque(false);

        jpCode.setAlignmentX(Component.CENTER_ALIGNMENT);

        jpCentral.add(jpCode);
        jpCentral.add(jpValidateCode);
        jpCentral.add(jpButtons);
    }

    /**
     * Builds the code entry field panel with its label.
     */
    private void setCodePanel() {
        jpCode.setLayout(new BoxLayout(jpCode, BoxLayout.Y_AXIS));
        jpCode.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel topLabel = new JLabel("Enter code: ");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        setCodeTextField();

        jpCode.setOpaque(false);
        jpCode.add(topLabel);
        jpCode.add(jtfCode);
    }

    /**
     * Configures the appearance and focus color effects of the code text field.
     */
    private void setCodeTextField() {
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

    /**
     * Builds and styles the validate code button panel.
     */
    private void setValidateCodePanel() {
        jpValidateCode = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jpValidateCode.setOpaque(false);

        jbValidateCode.setPreferredSize(DIMENSION_BUTTON_LOGIN);
        jbValidateCode.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbValidateCode.setAlignmentY(Component.CENTER_ALIGNMENT);
        jbValidateCode.setForeground(Color.WHITE);
        jbValidateCode.setBackground(BACKGROUND_BUTTON);
        jbValidateCode.setOpaque(true);
        jbValidateCode.setContentAreaFilled(true);
        jbValidateCode.setFocusPainted(false);
        jbValidateCode.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jpValidateCode.add(jbValidateCode);

        jbValidateCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbValidateCode.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbValidateCode.setForeground(Color.BLACK);
                jbValidateCode.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbValidateCode.setBackground(BACKGROUND_BUTTON);
                jbValidateCode.setForeground(Color.WHITE);
                jbValidateCode.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });

    }

    /**
     * Arranges the bottom navigation buttons with spacing.
     */
    private void setButtons() {
        jpButtons.setLayout(new BoxLayout(jpButtons, BoxLayout.Y_AXIS));
        jpButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        jpButtons.setOpaque(false);

        setGoBackLoginButton();

        jpButtons.add(jbBackLogIn);
        jpButtons.add(Box.createVerticalStrut(10));
    }

    /**
     * Configures the size and alignment of the go-back-to-login button.
     */
    private void setGoBackLoginButton() {
        jbBackLogIn.setPreferredSize(DIMENSION_BUTTON_SIGNIN);
        jbBackLogIn.setMaximumSize(DIMENSION_BUTTON_SIGNIN);
        jbBackLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbBackLogIn.setAlignmentY(Component.CENTER_ALIGNMENT);

        jbBackLogIn.setForeground(Color.WHITE);
        jbBackLogIn.setBackground(BACKGROUND_BUTTON);
        jbBackLogIn.setOpaque(true);
        jbBackLogIn.setContentAreaFilled(true);
        jbBackLogIn.setFocusPainted(false);
        jbBackLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));

        jbBackLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jbBackLogIn.setBackground(BACKGROUND_BUTTON_PRESSED);
                jbBackLogIn.setForeground(Color.BLACK);
                jbBackLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON, 2));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                jbBackLogIn.setBackground(BACKGROUND_BUTTON);
                jbBackLogIn.setForeground(Color.WHITE);
                jbBackLogIn.setBorder(BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2));
            }
        });
    }

    /**
     * Shows an error dialog with the given message.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Opens a modal dialog prompting the user to enter their email,
     * storing it on confirmation for later retrieval.
     */
    public void showEnterEmailPopUp() {
        JButton jbSendCode = createDialogButton("SEND CODE");

        JDialog dialog = new JDialog();
        dialog.setModal(true); //Blocked until is disposed
        dialog.setUndecorated(true);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND_BUTTON);
        content.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel title = new JLabel("ENTER YOUR EMAIL");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel instructions = new JLabel("*Please make sure this email actually exists and is linked to an existing account*");
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setFont(new Font("Times New Roman", Font.BOLD, 13));
        instructions.setForeground(Color.WHITE);

        jtfEmail.setPreferredSize(new Dimension(300, 40));
        jtfEmail.setMaximumSize(new Dimension(300, 40));
        jtfEmail.setAlignmentX(Component.CENTER_ALIGNMENT);


        jbSendCode.addActionListener(e -> {
            email = jtfEmail.getText();
            dialog.dispose();
        });

        content.add(title);
        content.add(Box.createVerticalStrut(20));
        content.add(jtfEmail);
        content.add(Box.createVerticalStrut(20));
        content.add(jbSendCode);
        content.add(Box.createVerticalStrut(20));
        content.add(instructions);

        dialog.add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Creates a styled dialog button with the given label.
     */
    private JButton createDialogButton(String title) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 55));
        button.setBackground(Color.WHITE);
        button.setForeground(BACKGROUND_BUTTON);
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));

        return button;
    }


    public String getCode() {
        return jtfCode.getText();
    }

    public String getEmail() {
        return email;
    }

    public void addBackLoginListener(ActionListener listener) {
        jbBackLogIn.addActionListener(listener);
    }

    public void addValidateCodeListener(ActionListener listener) {
        jbValidateCode.addActionListener(listener);
    }

    /**
     * Opens a modal dialog for entering and confirming a new password.
     * Runs the provided callback once the passwords match and the user confirms.
     * @param onChangePassword called after a successful password confirmation
     */
    public void showChangePassword(Runnable onChangePassword) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);

        JButton jbChangePassword = createDialogButton("CHANGE PASSWORD");

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND_BUTTON);
        content.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BACKGROUND_BUTTON_PRESSED, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel title = new JLabel("ENTER YOUR NEW PASSWORD");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel jlNewPassword = new JLabel("Password: ");
        jlNewPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlNewPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        jlNewPassword.setForeground(Color.WHITE);

        JLabel jlPasswordConfirmation = new JLabel("Password confirmation: ");
        jlPasswordConfirmation.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlPasswordConfirmation.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        jlPasswordConfirmation.setForeground(Color.WHITE);

        jtfNewPassword.setPreferredSize(new Dimension(300, 40));
        jtfNewPassword.setMaximumSize(new Dimension(300, 40));
        jtfNewPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        jtfPasswordConfirmation.setPreferredSize(new Dimension(300, 40));
        jtfPasswordConfirmation.setMaximumSize(new Dimension(300, 40));
        jtfPasswordConfirmation.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(20));
        content.add(jlNewPassword);
        content.add(Box.createVerticalStrut(5));
        content.add(jtfNewPassword);
        content.add(Box.createVerticalStrut(25));
        content.add(jlPasswordConfirmation);
        content.add(Box.createVerticalStrut(5));
        content.add(jtfPasswordConfirmation);
        content.add(Box.createVerticalStrut(20));
        content.add(jbChangePassword);

        jbChangePassword.addActionListener(e -> {
            newPassword = jtfNewPassword.getText();
            confirmationPassword = jtfPasswordConfirmation.getText();

            if (!newPassword.equals(confirmationPassword)) {
                JOptionPane.showMessageDialog(dialog, "The passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dialog.dispose();
                onChangePassword.run();
            }
        });

        dialog.add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public String getNewPassword() {
        return jtfNewPassword.getText();
    }

    public String getPasswordConfirmation() {
        return jtfPasswordConfirmation.getText();
    }

    public void clearTextFields() {
        jtfCode.setText("");
        jtfEmail.setText("");
        jtfNewPassword.setText("");
        jtfPasswordConfirmation.setText("");
    }


}
