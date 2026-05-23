package no.ntnu.group51.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;

/**
 * The view for the main menu.
 */
public class MainMenuView implements View {

  private final BorderPane root = new BorderPane();
  private Button cont = new Button("CONTINUE");
  private Button newG = new Button("NEW GAME");
  private Button exit = new Button("EXIT");

  /**
   * Creates the main menu view.
   *
   * @param sceneManager The scene manager for the stage.
   */
  public MainMenuView(SceneManager sceneManager, GameModel model) {

    cont.getStyleClass().add("main-menu-button");
    newG.getStyleClass().add("main-menu-button");
    exit.getStyleClass().add("main-menu-button");

    VBox menuButtons = new VBox();
    menuButtons.getChildren().addAll(cont, newG, exit);
    root.setCenter(menuButtons);

    Image image = new Image("images/Background.png");
    BackgroundSize bSize = new BackgroundSize(
        BackgroundSize.AUTO,
        BackgroundSize.AUTO,
        false,
        false,
        true,
        false);
    Background background = new Background(new BackgroundImage(image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        bSize));
    root.setBackground(background);

    ImageView titleImage = new ImageView(new Image("images/Million.png"));
    HBox title = new HBox();
    root.setTop(title);
    title.getChildren().add(titleImage);
    title.setAlignment(Pos.CENTER);
    title.setTranslateY(120);
    titleImage.setPreserveRatio(true);

    menuButtons.setAlignment(Pos.CENTER);
    menuButtons.setSpacing(30);
    menuButtons.setTranslateY(100);
  }

  public void setOnNewGame(EventHandler<ActionEvent> action) {
    newG.setOnAction(action);
  }

  public void setOnExit(EventHandler<ActionEvent> action) {
    exit.setOnAction(action);
  }

  public Parent getRoot() {
    return root;
  }
}
