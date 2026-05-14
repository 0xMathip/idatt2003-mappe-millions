package no.ntnu.group51.view.factories;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stocks.Stock;
import org.kordamp.ikonli.javafx.FontIcon;

public class StockRowFactory {

  public Parent createStockRow(Stock stock) {
    HBox row = new HBox();
    row.getStyleClass().add("factory-stock-row");

    Label ticker = new Label(stock.getSymbol());
    ticker.getStyleClass().add("factory-stock-row-ticker");

    Label company = new Label(stock.getCompany());
    company.getStyleClass().add("factory-stock-row-company");

    Label price = new Label(stock.getSalesPrice().toString());
    price.getStyleClass().add("factory-stock-row-price");

    Label priceChange = new Label("$" + stock.getLatestPriceChange().toString());
    priceChange.getStyleClass().add("factory-stock-row-change");

    Label priceChangePercentage = new Label(stock.getLatestPriceChangePercent().toString() + "%");
    priceChangePercentage.getStyleClass().add("factory-stock-row-change-percent");

    applyStyleChange(priceChange, stock);
    applyStyleChange(priceChangePercentage, stock);

    Region topSpacer = new Region();
    HBox.setHgrow(topSpacer, Priority.ALWAYS);

    Region botSpacer = new Region();
    HBox.setHgrow(botSpacer, Priority.ALWAYS);

    HBox topRow = new HBox(
        ticker,
        topSpacer,
        price
    );

    HBox botRow = new HBox(
        company,
        botSpacer,
        priceChange,
        priceChangePercentage
    );

    topRow.getStyleClass().add("factory-stock-row-top");
    botRow.getStyleClass().add("factory-stock-row-bot");

    VBox content = new VBox(topRow, botRow);
    content.getStyleClass().add("factory-stock-content");

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add("factory-stock-row-arrow");

    row.getChildren().addAll(content, arrowIcon);

    return row;
  }

  private void applyStyleChange(Label label, Stock stock) {
    label.getStyleClass().removeAll(
        "positive-price-change",
        "negative-price-change",
        "neutral-price-change"
    );

    int sign = stock.getLatestPriceChange().signum();

    if (sign < 0) {
      label.getStyleClass().add("negative-price-change");
    } else if (sign > 0) {
      label.getStyleClass().add("positive-price-change");
    } else {
      label.getStyleClass().add("neutral-price-change");
    }
  }
}
