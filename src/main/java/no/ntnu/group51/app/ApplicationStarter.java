package no.ntnu.group51.app;

import no.ntnu.group51.controller.mainmenu.MainMenuController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MainMenuView;

/**
 * Initializes the application startup flow.
 */
public final class ApplicationStarter {

  /**
   * Prevents instantiation of this utility class.
   */
  private ApplicationStarter() {
  }

  /**
   * Initializes the application and loads the main menu scene.
   *
   * @param model the game model
   * @param sceneManager the scene manager
   * @throws IllegalArgumentException if model or sceneManager is null
   */
  public static void initialize(GameModel model, SceneManager sceneManager) {
    if (model == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }

    if (sceneManager == null) {
      throw new IllegalArgumentException("Scene manager cannot be null.");
    }
    MainMenuView mainMenuView = new MainMenuView();

    new MainMenuController(model, mainMenuView, sceneManager);

    sceneManager.changeScene(mainMenuView);

  }
}
