package no.ntnu.group51.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import no.ntnu.group51.controller.MainMenuController;

public class MainMenuView implements View {

  private final BorderPane root = new BorderPane();
  private final VBox menuButtons = new VBox();
  private final HBox title = new HBox();

  public MainMenuView() {

    Button cont = new Button("CONTINUE");
    Button newG = new Button("NEW GAME");
    Button exit = new Button("EXIT");
    cont.getStyleClass().add("button");
    newG.getStyleClass().add("button");
    exit.getStyleClass().add("button");

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
    root.setTop(title);
    title.getChildren().add(titleImage);
    title.setAlignment(Pos.CENTER);
    title.setTranslateY(120);
    titleImage.setPreserveRatio(true);





    double scale = 2;
    menuButtons.setAlignment(Pos.CENTER);
    menuButtons.setSpacing(20);
    menuButtons.setScaleX(scale);
    menuButtons.setScaleY(scale);
    menuButtons.setTranslateY(100);

    exit.setOnAction(e -> Platform.exit());

  }

  public Parent getRoot() {
    return root;
  }
}
