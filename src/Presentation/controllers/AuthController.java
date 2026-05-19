package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.ConfigView.DELETE_ACCOUNT;
import static Presentation.views.ConfigView.LOGOUT;
import static Presentation.views.ForgotPasswordView.GO_BACK_LOGIN;
import static Presentation.views.ForgotPasswordView.VALIDATE_CODE;
import static Presentation.views.LoginView.*;
import static Presentation.views.RegisterView.GO_LOGIN;
import static Presentation.views.RegisterView.REGISTER_USER;

/**
 * Handles all authentication-related actions including login, registration,
 * logout, account deletion, and password recovery. Acts as the ActionListener
 * for all auth views.
 */
public class AuthController implements ActionListener {
    private final AppController appController;


    private final LoginView loginView;
    private final RegisterView registerView;
    private final ConfigView configView;
    private final ForgotPasswordView forgotPasswordView;


    private final UserManager userManager;

    protected static final String LOGIN = "login";
    protected static final String REGISTER = "register";
    protected static final String MENU = "menu";
    protected static final String CONFIG = "config";
    protected static final String FORGOT_PASSWORD = "forgotPassword";

    /**
     * Initializes all auth-related views, registers them with the main frame,
     * and attaches this controller as their event listener.
     * @param appController the central app controller used for navigation and dialogs
     * @param userManager the business layer manager handling user operations
     */
    public AuthController(AppController appController, UserManager userManager/*, LoginView loginView, RegisterView registerView,
                          GameView gameView, ConfigView configView, StatsView statsView, MenuView menuView*/) {
        this.appController = appController;

        this.loginView = new LoginView();
        appController.addCardToMainFrame(loginView, LOGIN);
        this.registerView = new RegisterView();
        appController.addCardToMainFrame(registerView, REGISTER);
        this.configView = new ConfigView();
        appController.addCardToMainFrame(configView, CONFIG);
        this.forgotPasswordView = new ForgotPasswordView();
        appController.addCardToMainFrame(forgotPasswordView, FORGOT_PASSWORD);

        this.userManager = userManager;

        loginView.addLoginListener(this);
        loginView.addSignUpListener(this);
        loginView.addForgotPasswordListener(this);

        registerView.addListeners(this);

        configView.addLogoutListener(this);
        configView.addDeleteListener(this);
        configView.addBackListener(e ->  appController.goBack());

        forgotPasswordView.addBackLoginListener(this);
        forgotPasswordView.addValidateCodeListener(this);
    }

    /**
     * Validates the login form credentials and navigates to the menu on success,
     * or displays an error message on failure.
     */
    public void handleLogin() {
        String username_email = loginView.getUsernameEmail();
        String password = loginView.getPassword();

        if (userManager.login(username_email, password)) {
            appController.switchCard("menu");
        } else {
            loginView.showError("Invalid credentials");
        }
    }

    /**
     * Logs out the current user and redirects to the login view.
     */
    public void handleLogout(){
        System.out.println("handleLogout called");

        userManager.logout();
        appController.switchCard(LOGIN);
    }

    /**
     * Prompts the user for confirmation before deleting their account.
     * Navigates to login on success or shows an error dialog on failure.
     */
    public void handleDelete(){
        int choice = appController.showConfirmationPopUp("Delete Account", "Are you sure you want to delete the account?");

        if(choice == JOptionPane.YES_OPTION){
            if(userManager.deleteUser()){
                appController.switchCard(LOGIN);
            }else{
                appController.showErrorPopUp("Error","Could not delete account. Please try again.");
            }
        }
    }

    /**
     * Collects registration form data and attempts to create a new user account.
     * Navigates to the menu on success or displays a validation error on failure.
     */
    public void handleSignUp() {
        String username = registerView.getUsername();
        String email = registerView.getEmail();
        String password = registerView.getPassword();
        String password_confirmation = registerView.getPasswordConfirmation();

        String error = userManager.signUp(username, email, password, password_confirmation);
        if (error == null) {
            appController.switchCard("menu");
        } else {
            registerView.showError(error);
        }
    }

    /**
     * Prompts the user to enter their email and sends a password reset code to it.
     */
    public void handleSendCode() {
        forgotPasswordView.showEnterEmailPopUp();
        userManager.handleSendCode(forgotPasswordView.getEmail());
    }

    /**
     * Verifies the reset code entered by the user. On success, opens the change
     * password dialog; on failure, displays an error in the forgot password view.
     */
    public void handleValidateCode() {
        String sendedCode = userManager.getSendCode();
        if (forgotPasswordView.getCode() != null && sendedCode != null && forgotPasswordView.getCode().equals(sendedCode)) {
            forgotPasswordView.showChangePassword(() -> {
                handleChangePassword();
            });
        } else {
            forgotPasswordView.showError("This code is incorrect. Please try again.");
        }
    }

    /**
     * Submits the new password from the forgot password view and redirects to login.
     */
    public void handleChangePassword() {
        userManager.changePassword(forgotPasswordView.getNewPassword(), forgotPasswordView.getPasswordConfirmation());
        appController.switchCard(LOGIN);
    }

    /**
     * Routes action events from all auth views to their corresponding handler methods.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(LOGIN_USER)) {
            handleLogin();
        } else if (e.getActionCommand().equals(GO_REGISTER)) {
            appController.switchCard(REGISTER);
        } else if (e.getActionCommand().equals(DELETE_ACCOUNT)) {
            handleDelete();
        } else if (e.getActionCommand().equals(LOGOUT)) {
            handleLogout();
        } else if (e.getActionCommand().equals(GO_FORGOT_PASSWORD)) {
            appController.switchCard(FORGOT_PASSWORD);
            handleSendCode();
        } else if (e.getActionCommand().equals(REGISTER_USER)) {
            handleSignUp();
        } else if (e.getActionCommand().equals(GO_LOGIN)) {
            appController.switchCard(LOGIN);
        } else if (e.getActionCommand().equals(GO_BACK_LOGIN)) {
            appController.switchCard(LOGIN);
        } else if (e.getActionCommand().equals(VALIDATE_CODE)) {
            handleValidateCode();
        }

    }
}