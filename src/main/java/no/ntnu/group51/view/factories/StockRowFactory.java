package no.ntnu.group51.view.factories;

import java.math.BigDecimal;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stocks.Stock;
import org.kordamp.ikonli.javafx.FontIcon;

public final class StockRowFactory {

  private StockRowFactory() {
  }

  public static Parent createStockRow(Stock stock) {
    HBox row = new HBox();
    row.getStyleClass().add("factory-stock-row");

    Label ticker = new Label(stock.getSymbol());
    ticker.getStyleClass().add("factory-stock-row-ticker");

    Label company = new Label(stock.getCompany());
    company.getStyleClass().add("factory-stock-row-company");

    Label price = new Label(stock.getSalesPrice().toString());
    price.getStyleClass().add("factory-stock-row-price");

    BigDecimal latestChange = stock.getLatestPriceChange();

    Label priceChange = new Label(valueExpression(latestChange, "$"));
    priceChange.getStyleClass().add("factory-stock-row-change");

    Label priceChangePercentage = new Label(
        valueExpression(stock.getLatestPriceChangePercent(),"%"));
    priceChangePercentage.getStyleClass().add("factory-stock-row-change-percent");

    applyStyleChange(priceChange, latestChange);
    applyStyleChange(priceChangePercentage, latestChange);

    Region topSpacer = new Region();
    HBox.setHgrow(topSpacer, Priority.ALWAYS);

    Region botSpacer = new Region();
    HBox.setHgrow(botSpacer, Priority.ALWAYS);

    HBox topRow = new HBox(
        8,
        ticker,
        topSpacer,
        price
    );

    HBox botRow = new HBox(
        8,
        company,
        botSpacer,
        priceChange,
        priceChangePercentage
    );

    VBox content = new VBox(topRow, botRow);
    content.getStyleClass().add("factory-stock-content");

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add("factory-stock-row-arrow");

    row.getChildren().addAll(content, arrowIcon);

    return row;
  }

  private static void applyStyleChange(Label label, BigDecimal latestChange) {
    label.getStyleClass().removeAll(
        "positive-price-change",
        "negative-price-change",
        "neutral-price-change"
    );

    int sign = latestChange.signum();

    if (sign < 0) {
      label.getStyleClass().add("negative-price-change");
    } else if (sign > 0) {
      label.getStyleClass().add("positive-price-change");
    } else {
      label.getStyleClass().add("neutral-price-change");
    }
  }

  private static String valueExpression(BigDecimal value, String symbol) {
    BigDecimal absValue = value.abs().stripTrailingZeros();

    String prefix = "$".equals(symbol) ? symbol : "";
    String suffix = "%".equals(symbol) ? symbol : "";

    if (value.signum() > 0) {
      return "+" + prefix + absValue + suffix;
    }

    if (value.signum() < 0 ) {
      return "-" + prefix + absValue + suffix;
    }

    return prefix + absValue.toString() + suffix;
  }
}
