package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.GameView;
import Presentation.views.LoginView;
import Presentation.views.RegisterView;

import javax.swing.*;

public class UserController {
    private final AppController appController;
    private final LoginView loginView;
    private final RegisterView registerView;
    private final GameView gameView;
    private final UserManager userManager;

    public UserController(AppController appController, LoginView loginView, RegisterView registerView, GameView gameView) {
        this.appController = appController;
        this.loginView = loginView;
        this.registerView = registerView;
        this.gameView = gameView;
        this.userManager = new UserManager();

        loginView.addLoginListener(e -> handleLogin());
        registerView.addRegisterListener(e -> handleRegister());

        gameView.addDeleteListener(e -> handleDelete());
        gameView.addLogoutListener(e -> handleLogout());
    }

    private void handleLogin() {
        String username_email = loginView.getUsernameEmail();
        String password = loginView.getPassword();

        if (userManager.login(username_email, password)) {
            appController.switchCard("menu");
        } else {
            loginView.showError("Invalid credentials");
        }
    }

    private void handleLogout(){
        userManager.logout();
        appController.switchCard("login");
    }

    private void handleDelete(){
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
    private void handleRegister() {
        String username = registerView.getUsername();
        String email = registerView.getEmail();
        String password = registerView.getPassword();
        String password_confirmation = registerView.getPasswordConfirmation();

        if (userManager.register(username, email, password, password_confirmation)) {
            appController.switchCard("menu");
        } else {
            registerView.showError("Ops! There has been an error!");
        }
    }
}
