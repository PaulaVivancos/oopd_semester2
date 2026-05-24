import Business.managers.GameManager;
import Business.managers.StatsManager;
import Business.managers.UserManager;
import Persistence.GameDAO;
import Persistence.SQLDaos.GameSQLDao;
import Persistence.SQLDaos.StatsSQLDao;
import Persistence.SQLDaos.UserSQLDao;
import Persistence.StatsDAO;
import Persistence.UserDAO;
import Presentation.controllers.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 1. Create the DAOs.
        // InterfaceDAO dao = new ImplementedDAO();
        GameDAO gameDAO = new GameSQLDao();
        UserDAO userDAO = new UserSQLDao();
        StatsDAO statsDAO = new StatsSQLDao();

        // 2. Create all the managers + attach daos to managers (dependency inversion).
        // Manager manager = new Manager(dao);
        UserManager userManager = new UserManager(userDAO);
        GameManager gameManager = new GameManager(gameDAO);
        StatsManager statsManager = new StatsManager(statsDAO);

        // 3. Create all the views. (Omit)

        // 4. Create all the controllers + attach managers and views to controllers.
        AppController appController = new AppController();
        AuthController authController = new AuthController(appController, userManager);
        StatsController statsController = new StatsController(appController, statsManager);
        GameController gameController = new GameController(appController, gameManager, userManager, statsController);

        MenuController menuController = new MenuController(appController, authController, gameController, statsController);

        appController.startSystem();
    }
}