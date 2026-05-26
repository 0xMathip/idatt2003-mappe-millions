package no.ntnu.group51.controller.mainmenu;

import javafx.application.Platform;
import no.ntnu.group51.controller.GameViewController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.pages.MainMenuView;

/**
 * Class for the main menu controller.
 */
public class MainMenuController {

  private final GameModel model;
  private final MainMenuView view;
  private final SceneManager sceneManager;

  /**
   * Creates the controller for the main menu and sets up the buttons.
   *
   * @param model The persistent model for the game.
   * @param view The view for the main menu.
   * @param sceneManager The scene manager for the program with access to the scene.
   */
  public MainMenuController(GameModel model, MainMenuView view, SceneManager sceneManager) {
    this.model = model;
    this.view = view;
    this.sceneManager = sceneManager;
    setupButtons();
  }

  /**
   * Sets up the buttons on the main menu.
   */
  public void setupButtons() {

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
