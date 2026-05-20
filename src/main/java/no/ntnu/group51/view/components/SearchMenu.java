package no.ntnu.group51.view.components;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SearchMenu extends StackPane {
  private final TextField searchField = new TextField();
  private final VBox list = new VBox(8);
  private final FontIcon closeIcon = new FontIcon("cil-x");

  public SearchMenu(String promptText) {
    getStyleClass().addAll("card", "search-menu");
    setAlignment(Pos.CENTER);

    searchField.setPromptText(promptText);
    searchField.getStyleClass().addAll("card", "search-menu-text-field");

    closeIcon.getStyleClass().add("search-menu-close");

    HBox topBar = new HBox(searchField, closeIcon);
    topBar.getStyleClass().add("search-menu-top-bar");
    topBar.setAlignment(Pos.CENTER_LEFT);

    ScrollPane scrollPane = new ScrollPane(list);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add("search-menu-scroll");

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
    closeIcon.setOnMouseClicked(e -> onClose.run());
  }
}
