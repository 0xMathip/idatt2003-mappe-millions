package no.ntnu.group51.controller;

import javafx.scene.Scene;
import no.ntnu.group51.view.View;

/**
 * Class for the scene manager. Its purpose is to take in the scene of the program to
 * hold that throughout.
 */
public class SceneManager {

  private final Scene scene;

  /**
   * Creates a scene manager using the scene of the program.
   *
   * @param scene The scene of the stage.
   */
  public SceneManager(Scene scene) {
    if (scene == null) {
      throw new IllegalArgumentException("Scene cannot be null.");
    }
    this.scene = scene;
  }

  /**
   * Changes the scene to a view.
   *
   * @param view The view you want to set the scene to
   */
  public void changeScene(View view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    scene.setRoot(view.getRoot());
  }

  public Scene getScene() {
    return scene;
  }
}
