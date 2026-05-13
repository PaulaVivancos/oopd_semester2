package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.*;

import javax.swing.*;

public class UserController {
    private final AppController appController;
    private final LoginView loginView;
    private final RegisterView registerView;
    private final GameView gameView;
    private final ForgotPasswordView forgotPasswordView;
    private final ConfigView configView;
    private final StatsView statsView;
    private final MenuView menuView;
    private final UserManager userManager;

    public UserController(AppController appController, LoginView loginView, RegisterView registerView,
                          GameView gameView, ConfigView configView, StatsView statsView, MenuView menuView,
                          ForgotPasswordView forgotPasswordView) {
        this.appController = appController;
        this.loginView  = loginView;
        this.registerView = registerView;
        this.gameView   = gameView;
        this.forgotPasswordView = forgotPasswordView;
        this.configView = configView;
        this.statsView  = statsView;
        this.menuView   = menuView;
        //TODO pass parameter that is missing??
        //this.userManager = new UserManager();
        this.userManager = null;

        loginView.addLoginListener(e -> handleLogin());
        registerView.addRegisterListener(e -> handleSignUp());

        gameView.addLogoutListener(e -> handleLogout());
        gameView.addDeleteListener(e -> handleDelete());

        configView.addLogoutListener(e -> handleLogout());
        configView.addDeleteListener(e -> handleDelete());

        //forgotPasswordView.addSendCodeListener(e -> handleSendingEmail());  TODO and here
        forgotPasswordView.addValidateCodeListener(e -> handleCodeVerification());
        forgotPasswordView.addBackLoginListener(e -> {
                forgotPasswordView.clearTextFields();
                //userManager.resetSendCode();  TODO and here
                appController.switchCard("login");
        });


        statsView.addLogoutListener(e -> handleLogout());
        menuView.getLogOutButton().addActionListener(e -> handleLogout());
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
        userManager.logout();
        appController.switchCard("login");
    }

    public void handleDelete(){
        int choice = JOptionPane.showConfirmDialog(
                gameView,
                "Are you sure you want to delete the account?",
                "Delete Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if(choice == JOptionPane.YES_OPTION){
            if(userManager.deleteUser()){
                appController.switchCard("login");
            }else{
                JOptionPane.showMessageDialog(gameView,
                        "Could not delete account. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
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

    public void handleSendingEmail() {
        forgotPasswordView.clearTextFields();
        String email = forgotPasswordView.getEmail();
        userManager.handleSendCode(email);

        if(userManager.getSendCode() == null) {
            forgotPasswordView.showError("This email is invalid or could not be reached.");
        }
        else {
            forgotPasswordView.clearTextFields();
            appController.switchCard("forgotPassword");
        }

    }

    public void handleCodeVerification() {
        if(forgotPasswordView.getCode().equals(userManager.getSendCode())) {
            //forgotPasswordView.showChangePassword();  TODO missing arg here as well
        }
        else {
            forgotPasswordView.showError("This code is invalid.");
        }
    }
}
