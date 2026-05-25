package no.ntnu.group51.view.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * The view for the main menu.
 */
public class MainMenuView implements View {

  private final BorderPane root = new BorderPane();
  private final Button cont = new Button("CONTINUE");
  private final Button newG = new Button("NEW GAME");
  private final Button exit = new Button("EXIT");

  /**
   * Creates the main menu view.
   */
  public MainMenuView() {

    cont.getStyleClass().add(StyleClass.MAIN_MENU_BUTTON);
    newG.getStyleClass().add(StyleClass.MAIN_MENU_BUTTON);
    exit.getStyleClass().add(StyleClass.MAIN_MENU_BUTTON);

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

  /**
   * Sets the action for starting a new game.
   *
   * @param action the action handler
   */
  public void setOnNewGame(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    newG.setOnAction(action);
  }

  /**
   * Sets the action for exiting the application.
   *
   * @param action the action handler
   */
  public void setOnExit(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    exit.setOnAction(action);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
