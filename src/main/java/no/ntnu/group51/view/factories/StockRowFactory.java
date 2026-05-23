package no.ntnu.group51.view.factories;

import java.math.BigDecimal;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.util.PriceStyleHelper;
import org.kordamp.ikonli.javafx.FontIcon;

public final class StockRowFactory {

  private StockRowFactory() {
  }

  public static SearchRow createStockRow(Stock stock) {
    SearchRow row = new SearchRow(55, 30, 15, 10);

    Label ticker = new Label(stock.getSymbol());
    ticker.getStyleClass().add("factory-search-row-ticker");

    Label company = new Label(stock.getCompany());
    company.getStyleClass().add("factory-search-row-company");

    Label price = new Label("$" + stock.getSalesPrice().toString());
    price.getStyleClass().add("factory-search-row-price");

    BigDecimal latestChange = stock.getLatestPriceChange();

    Label priceChange = new Label(valueExpression(latestChange, "$"));
    priceChange.getStyleClass().add("factory-search-row-change");

    Label priceChangePercentage = new Label("(" +
        valueExpression(stock.getLatestPriceChangePercent(),"%") + ")");
    priceChangePercentage.getStyleClass().add("factory-search-row-change-percent");

    HBox changeBox = new HBox(6, priceChange, priceChangePercentage);
    changeBox.setAlignment(Pos.CENTER_RIGHT);

    PriceStyleHelper.applyPriceChangeStyle(priceChange, latestChange);
    PriceStyleHelper.applyPriceChangeStyle(priceChangePercentage, latestChange);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add("factory-search-row-arrow");

    row.addToCell(ticker, 0, 0);
    row.addToCell(company, 0, 1);

    row.addToCell(price, 1, 0, 2, 1);
    row.addToCell(changeBox, 1, 1, 2, 1);

    row.addToCell(arrowIcon, 3, 0, 1, 2);

    GridPane.setHalignment(price, HPos.RIGHT);
    GridPane.setHalignment(changeBox, HPos.RIGHT);
    GridPane.setHalignment(arrowIcon, HPos.CENTER);

    return row;
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
