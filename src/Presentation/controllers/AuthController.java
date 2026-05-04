package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Presentation.views.ConfigView.DELETE_ACCOUNT;
import static Presentation.views.ConfigView.LOGOUT;
import static Presentation.views.LoginView.*;
import static Presentation.views.RegisterView.GO_LOGIN;
import static Presentation.views.RegisterView.REGISTER_USER;

public class AuthController implements ActionListener {
    private final AppController appController;


    private final LoginView loginView;
    private final RegisterView registerView;
    private final ConfigView configView;


    private final UserManager userManager;

    protected static final String LOGIN = "login";
    protected static final String REGISTER = "register";
    protected static final String MENU = "menu";
    protected static final String CONFIG = "config";

    public AuthController(AppController appController, UserManager userManager/*, LoginView loginView, RegisterView registerView,
                          GameView gameView, ConfigView configView, StatsView statsView, MenuView menuView*/) {
        this.appController = appController;

        this.loginView = new LoginView();
        appController.addCardToMainFrame(loginView, LOGIN);
        this.registerView = new RegisterView();
        appController.addCardToMainFrame(registerView, REGISTER);
        this.configView = new ConfigView();
        appController.addCardToMainFrame(configView, CONFIG);

        this.userManager = userManager;

        loginView.addLoginListener(this);
        loginView.addSignUpListener(this);
        loginView.addForgotPasswordListener(this);

        registerView.addListeners(this);

        configView.addLogoutListener(this);
        configView.addDeleteListener(this);
        configView.addBackListener(e ->  appController.goBack());
    }

    public void handleLogin() {
        String username_email = loginView.getUsernameEmail();
        String password = loginView.getPassword();

        if (userManager.login(username_email, password)) {
            appController.switchCard("menu");
        } else {
            loginView.showError("Invalid credentials");
        }
    }

    public void handleLogout(){
        System.out.println("handleLogout called");

        userManager.logout();
        appController.switchCard(LOGIN);
    }

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
        } else if (e.getActionCommand().equals(FORGOT_PASSWORD)) {
            // handle that
        } else if (e.getActionCommand().equals(REGISTER_USER)) {
            handleSignUp();
        } else if (e.getActionCommand().equals(GO_LOGIN)) {
            appController.switchCard(LOGIN);
        }

    }
}
