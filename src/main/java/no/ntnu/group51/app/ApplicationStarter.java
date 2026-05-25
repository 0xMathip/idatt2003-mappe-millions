package no.ntnu.group51.app;

import no.ntnu.group51.controller.mainmenu.MainMenuController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MainMenuView;

public class ApplicationStarter {

  public static void initialize(GameModel model, SceneManager sceneManager) {

    MainMenuView mainMenuView = new MainMenuView(sceneManager, model);

    MainMenuController mainMenuController = new MainMenuController(
        model, mainMenuView, sceneManager
    );

    sceneManager.changeScene(mainMenuView);

  }
}
