package no.ntnu.group51.view.components;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.sun.javafx.util.Utils.clamp;

public class SearchMenu extends StackPane {
  private final TextField searchField = new TextField();
  private final VBox list = new VBox(8);
  private final FontIcon closeIcon;
  private final boolean showCloseButton;

  public SearchMenu(String promptText, boolean showCloseButton) {
    this.showCloseButton = showCloseButton;

    getStyleClass().addAll("card", "search-menu");
    setAlignment(Pos.CENTER);

    searchField.setPromptText(promptText);
    searchField.getStyleClass().addAll("card", "search-menu-text-field");

    HBox topBar = new HBox(searchField);
    topBar.getStyleClass().add("search-menu-top-bar");
    topBar.setAlignment(Pos.CENTER_LEFT);

    if (showCloseButton) {
      closeIcon = new FontIcon("cil-x");
      closeIcon.getStyleClass().add("search-menu-close");
      topBar.getChildren().add(closeIcon);
    } else {
      closeIcon = null;
      getStyleClass().add("search-menu-no-close");
    }

    ScrollPane scrollPane = new ScrollPane(list);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add("search-menu-scroll");

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
    separator.getStyleClass().add("search-menu-separator");

    VBox content = new VBox(8, topBar, separator, scrollPane);
    content.getStyleClass().add("search-menu-content");
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
