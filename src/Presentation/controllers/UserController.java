package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.LoginView;
import Presentation.views.MainFrame;

import java.sql.SQLException;

public class UserController {
    private AppController appController;
    private LoginView loginView;
    private UserManager userManager;

    public UserController(AppController appController, LoginView loginView) {
        this.appController = appController;
        this.loginView = loginView;
        this.userManager = new UserManager();

        loginView.addLoginListener(e -> handleLogin());
    }

    public void handleLogin() {
        String username = loginView.getUsername();
        //String email = loginView.getEmail();
        String email = "paulaviva@gmail.com";
        String password = loginView.getPassword();

        if (userManager.login(username, email, password)) {
            appController.switchToCard("menu");
        } else {
            loginView.showError("Invalid credentials");
        }
    }
}
