package Presentation.controllers;

import Business.managers.UserManager;
import Presentation.views.LoginView;
import Presentation.views.RegisterView;

public class UserController {
    private final AppController appController;
    private final LoginView loginView;
    private final RegisterView registerView;
    private final UserManager userManager;

    public UserController(AppController appController, LoginView loginView, RegisterView registerView) {
        this.appController = appController;
        this.loginView = loginView;
        this.registerView = registerView;
        this.userManager = new UserManager();

        loginView.addLoginListener(e -> handleLogin());
        registerView.addRegisterListener(e -> handleRegister());
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
