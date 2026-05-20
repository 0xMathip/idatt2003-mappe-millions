package no.ntnu.group51.view.components;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.factories.StockRowFactory;
import org.kordamp.ikonli.javafx.FontIcon;

public class StockSearchMenu implements View, Observer {
  private final StackPane root = new StackPane();
  private TextField searchField;
  private final VBox stockList = new VBox(8);
  private Runnable onClose;
  private FontIcon closeIcon;

  private final GameModel gameModel;

  public StockSearchMenu(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","stock-search-menu");
    root.setAlignment(Pos.CENTER);
    createLayout();
    registerEvents();

    gameModel.addObserver(this);
    updateDisplay();
  }

  private void createLayout() {
    searchField = new TextField();
    searchField.setPromptText("Search stocks");
    searchField.getStyleClass().addAll("card","stock-search-menu-text-field");

    closeIcon = new FontIcon("cil-x");
    closeIcon.getStyleClass().add("stock-search-menu-close");

    HBox topBar = new HBox(
        searchField,
        closeIcon
    );
    topBar.getStyleClass().add("stock-search-menu-top-bar");

    HBox.setHgrow(searchField, Priority.ALWAYS);

    ScrollPane scrollPane = new ScrollPane(stockList);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add("stock-search-menu-scroll");

    VBox content = new VBox(
        8,
        topBar,
        scrollPane);
    content.getStyleClass().add("stock-search-menu-content");
    content.setAlignment(Pos.CENTER);

    root.getChildren().add(content);
  }

  private void registerEvents() {
    searchField.textProperty().addListener((obs, oldValue, newValue) -> updateDisplay());

    closeIcon.setOnMouseClicked(e -> {
      if (onClose != null) {
        onClose.run();
      }
    });
  }

  private void updateDisplay() {
    List<Parent> newRows = gameModel
        .getExchange()
        .findStocks(searchField.getText())
        .stream()
        .map(StockRowFactory::createStockRow)
        .toList();

    stockList.getChildren().setAll(newRows);
  }

  public void setOnClose(Runnable onClose) {
    this.onClose = onClose;
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void update() {
    updateDisplay();
  }
}
