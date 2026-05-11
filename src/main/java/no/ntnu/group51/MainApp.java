package no.ntnu.group51;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import no.ntnu.group51.view.MainMenuView;

public class MainApp extends Application {

  @Override
  public void start(Stage stage) {
    Font f = Font.loadFont(
      getClass().getResource("/fonts/Almarai-Bold.ttf").toExternalForm(),
      20
    );

    System.out.println("Loaded font: " + f);

    MainMenuView view = new MainMenuView();

    int width = 1400;
    int height = 800;
    String css = getClass().getResource("/style.css").toExternalForm();

    Scene scene = new Scene(view.getRoot(), width, height);
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.setTitle("Main Menu");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}