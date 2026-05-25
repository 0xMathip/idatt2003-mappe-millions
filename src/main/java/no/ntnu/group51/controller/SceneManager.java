package no.ntnu.group51.controller;

import javafx.scene.Scene;
import no.ntnu.group51.view.View;

public class SceneManager {

  private Scene scene;

  public SceneManager(Scene scene) {
    if (scene == null) {
      throw new IllegalArgumentException("Scene cannot be null.");
    }
    this.scene = scene;
  }

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
