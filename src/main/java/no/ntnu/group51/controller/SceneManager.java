package no.ntnu.group51.controller;

import javafx.scene.Scene;
import no.ntnu.group51.view.View;

public class SceneManager {

  private Scene scene;

  public SceneManager(Scene scene) {
    this.scene = scene;
  }

  public void changeScene(View view) {
    scene.setRoot(view.getRoot());
  }

  public Scene getScene() {
    return scene;
  }
}
