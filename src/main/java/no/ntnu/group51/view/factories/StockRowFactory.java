package no.ntnu.group51.view.factories;

import java.math.BigDecimal;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Factory for creating stock rows used in search menus.
 */
public final class StockRowFactory {

  /**
   * Prevents instantiation of this utility class.
   */
  private StockRowFactory() {
  }

  /**
   * Creates a stock row for a search menu.
   *
   * @param stock the stock to display
   * @return a search row representing the stock
   * @throws IllegalArgumentException if stock is null
   */
  public static SearchRow createStockRow(Stock stock) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }

    SearchRow row = new SearchRow(55, 30, 15, 10);

    Label ticker = new Label(stock.getSymbol());
    ticker.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_TICKER);

    Label company = new Label(stock.getCompany());
    company.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_COMPANY);

    Label price = new Label(CurrencyFormatter.format(stock.getSalesPrice()));
    price.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_PRICE);

    BigDecimal latestChange = stock.getLatestPriceChange();

    Label priceChange = new Label(CurrencyFormatter.format(latestChange));
    priceChange.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_CHANGE);

    Label priceChangePercentage = new Label(
        "(" + PercentFormatter.format(stock.getLatestPriceChangePercent()) + ")"
    );

    priceChangePercentage.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_CHANGE_PERCENT);

    HBox changeBox = new HBox(6, priceChange, priceChangePercentage);
    changeBox.setAlignment(Pos.CENTER_RIGHT);

    PriceStyleHelper.applyPriceChangeStyle(priceChange, latestChange);
    PriceStyleHelper.applyPriceChangeStyle(priceChangePercentage, latestChange);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_ARROW);

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
}
