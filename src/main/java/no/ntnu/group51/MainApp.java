package no.ntnu.group51;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.view.MainMenuView;

public class MainApp extends Application {

  @Override
  public void start(Stage stage) {

    int width = 1400;
    int height = 800;
    String css = getClass().getResource("/style.css").toExternalForm();

    Scene scene = new Scene(new Pane(), width, height);
    scene.getStylesheets().add(css);

    SceneManager sceneManager = new SceneManager(scene);
    sceneManager.changeScene(new MainMenuView(sceneManager));

    stage.setScene(scene);
    stage.setTitle("MILLION$");
    stage.show();
    stage.setMaximized(true);
  }

  public static void main(String[] args) {
    launch(args);
  }
}