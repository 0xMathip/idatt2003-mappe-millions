package no.ntnu.group51;

import no.ntnu.group51.controller.MainMenuController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.controller.SidebarController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.MainMenuView;

public class Start {

  public static void initialize(GameModel model, SceneManager sceneManager) {

    MainMenuView mainMenuView = new MainMenuView(sceneManager, model);

    MainMenuController mainMenuController = new MainMenuController(
        model, mainMenuView, sceneManager
    );

    sceneManager.changeScene(mainMenuView);

  }
}
