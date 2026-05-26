package no.ntnu.group51.app;

import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.controller.mainmenu.MainMenuController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MainMenuView;

/**
 * Class for a method that is called in the MainApp class.
 */
public class ApplicationStarter {

  /**
   * Method is called in the MainApp class and collects everything needed to initialize the app
   * so that the MainApp class stays slim.
   *
   * @param model The persistent model for the game.
   * @param sceneManager The scene manager following through the game with access to the scene.
   */
  public static void initialize(GameModel model, SceneManager sceneManager) {

    MainMenuView mainMenuView = new MainMenuView(sceneManager, model);

    MainMenuController mainMenuController = new MainMenuController(
        model, mainMenuView, sceneManager
    );

    sceneManager.changeScene(mainMenuView);

  }
}
