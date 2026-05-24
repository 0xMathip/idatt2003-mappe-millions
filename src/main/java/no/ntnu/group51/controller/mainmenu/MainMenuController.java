package no.ntnu.group51.controller.mainmenu;

import javafx.application.Platform;
import no.ntnu.group51.controller.GameViewController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.pages.MainMenuView;

public class MainMenuController {

  private final GameModel model;
  private final MainMenuView view;
  private final SceneManager sceneManager;

  public MainMenuController(GameModel model, MainMenuView view, SceneManager sceneManager) {
    this.model = model;
    this.view = view;
    this.sceneManager = sceneManager;
    setupButtons();
  }

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
