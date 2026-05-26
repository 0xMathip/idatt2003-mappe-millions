package no.ntnu.group51.controller.mainmenu;

import javafx.application.Platform;
import no.ntnu.group51.controller.GameViewController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.pages.MainMenuView;

/**
 * Controller for the main menu view.
 */
public class MainMenuController {

  private final GameModel model;
  private final MainMenuView view;
  private final SceneManager sceneManager;

  /**
   * Creates the main menu controller.
   *
   * @param model        the game model
   * @param view         the main menu view
   * @param sceneManager the scene manager
   * @throws IllegalArgumentException if any argument is null
   */
  public MainMenuController(GameModel model, MainMenuView view, SceneManager sceneManager) {
    if (model == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    if (sceneManager == null) {
      throw new IllegalArgumentException("Scene manager cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.sceneManager = sceneManager;
    setupButtons();
  }

  /**
   * Configures the main menu button actions.
   */
  private void setupButtons() {
    view.setOnNewGame(e -> {
      GameView gameView = new GameView();
      new GameViewController(model, gameView, sceneManager);
      sceneManager.changeScene(gameView);
    });

    view.setOnExit(e -> {
      Platform.exit();
    });
  }

}
