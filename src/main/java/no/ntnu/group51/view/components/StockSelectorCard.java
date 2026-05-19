package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.view.View;
import org.kordamp.ikonli.javafx.FontIcon;

public class StockSelectorCard implements View, Observer {
  private final GameModel gameModel;

  private final HBox root = new HBox();
  private Label tickerLabel;
  private Label companyLabel;
  private Label priceLabel;
  private Label changeLabel;


  public StockSelectorCard(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","stock-selector-card");
    root.setAlignment(Pos.CENTER_LEFT);

    createLayout();
    registerEvents();

    gameModel.addObserver(this);
    updateDisplay();
  }

  private void createLayout() {
    tickerLabel = new Label();
    tickerLabel.getStyleClass().add("stock-selector-card-ticker-label");

    companyLabel = new Label();
    companyLabel.getStyleClass().add("stock-selector-card-company-label");

    priceLabel = new Label();
    priceLabel.getStyleClass().add("stock-selector-card-price-label");

    changeLabel = new Label();
    changeLabel.getStyleClass().add("stock-selector-card-change-label");

    Region topSpacer = new Region();
    HBox.setHgrow(topSpacer, Priority.ALWAYS);

    Region botSpacer = new Region();
    HBox.setHgrow(botSpacer, Priority.ALWAYS);

    HBox topRow = new HBox(
        tickerLabel,
        topSpacer,
        priceLabel
    );

    HBox botRow = new HBox(
        companyLabel,
        botSpacer,
        changeLabel
    );

    topRow.getStyleClass().add("stock-selector-card-row");
    botRow.getStyleClass().add("stock-selector-card-row");

    topRow.setAlignment(Pos.CENTER_LEFT);
    botRow.setAlignment(Pos.CENTER_LEFT);

    VBox content = new VBox(topRow, botRow);
    content.getStyleClass().add("stock-selector-card-content");
    content.setAlignment(Pos.CENTER_LEFT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-down-alt");
    arrowIcon.getStyleClass().add("stock-selector-card-arrow");

    root.getChildren().addAll(
        content,
        arrowIcon
    );

  }

  private void registerEvents() {
    //root.setOnMouseClicked();
  }

  private void updateDisplay() {
    Stock stock = gameModel.getSelectedStock();

    tickerLabel.setText(stock.getSymbol());
    companyLabel.setText(stock.getCompany());
    priceLabel.setText("$" + stock.getSalesPrice().toString());
    changeLabel.setText(stock.getLatestPriceChangePercent().toString() + "%");

    if (stock.getLatestPriceChange().signum() == -1) {
      changeLabel.getStyleClass().remove("positive-price-change");
      changeLabel.getStyleClass().add("negative-price-change");
    } else if (stock.getLatestPriceChange().signum() == 1) {
      changeLabel.getStyleClass().remove("negative-price-change");
      changeLabel.getStyleClass().add("positive-price-change");
    } else {
      changeLabel.getStyleClass().remove("negative-price-change");
      changeLabel.getStyleClass().remove("positive-price-change");
      changeLabel.getStyleClass().add("neutral-price-change");
    }
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
