package no.ntnu.group51.view.components.shared;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.sun.javafx.util.Utils.clamp;

public class SearchMenu extends StackPane {
  private final TextField searchField = new TextField();
  private final VBox list = new VBox(8);
  private final FontIcon closeIcon;
  private final boolean showCloseButton;

  public SearchMenu(String promptText, boolean showCloseButton) {
    this.showCloseButton = showCloseButton;

    getStyleClass().addAll(StyleClass.CARD, StyleClass.SEARCH_MENU);
    setAlignment(Pos.CENTER);

    searchField.setPromptText(promptText);
    searchField.getStyleClass().addAll(StyleClass.CARD, StyleClass.SEARCH_MENU_TEXT_FIELD);

    HBox topBar = new HBox(searchField);
    topBar.getStyleClass().add(StyleClass.SEARCH_MENU_TOP_BAR);
    topBar.setAlignment(Pos.CENTER_LEFT);

    if (showCloseButton) {
      closeIcon = new FontIcon("cil-x");
      closeIcon.getStyleClass().add(StyleClass.SEARCH_MENU_CLOSE);
      topBar.getChildren().add(closeIcon);
    } else {
      closeIcon = null;
      getStyleClass().add(StyleClass.SEARCH_MENU_NO_CLOSE);
    }

    ScrollPane scrollPane = new ScrollPane(list);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add(StyleClass.SEARCH_MENU_SCROLL);

    scrollPane.addEventFilter(ScrollEvent.SCROLL,e -> {
      e.consume();

      double deltaY = e.getDeltaY();
      double width = scrollPane.getContent().getBoundsInLocal().getWidth();
      double height = scrollPane.getContent().getBoundsInLocal().getHeight();

      double vvalue = scrollPane.getVvalue();
      double scrollAmount = (deltaY * 1.5) / (height / 2);

      double newVvalue = clamp(vvalue - scrollAmount, 0, 1);

      Timeline timeline = new Timeline();
      KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), newVvalue);
      KeyFrame kf = new KeyFrame(Duration.millis(200), kv);

      timeline.getKeyFrames().add(kf);
      timeline.play();
    });

    Region separator = new Region();
    separator.getStyleClass().add(StyleClass.SEARCH_MENU_SEPARATOR);

    VBox content = new VBox(8, topBar, separator, scrollPane);
    content.getStyleClass().add(StyleClass.SEARCH_MENU_CONTENT);
    content.setAlignment(Pos.CENTER);

    getChildren().add(content);
  }

  public TextField getSearchField() {
    return searchField;
  }

  public void setRows(List<? extends Node> rows) {
    list.getChildren().setAll(rows);
  }

  public void setOnClose(Runnable onClose) {
    if (!showCloseButton || onClose == null) {
      return;
    }

    closeIcon.setOnMouseClicked(e -> onClose.run());
  }

  private double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }
}
