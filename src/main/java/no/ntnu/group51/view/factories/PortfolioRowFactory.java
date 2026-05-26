package no.ntnu.group51.view.factories;

import java.math.RoundingMode;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Factory for creating portfolio position rows used in search menus.
 */
public class PortfolioRowFactory {

  /**
   * Prevents instantiation of this utility class.
   */
  private PortfolioRowFactory() {
  }

  /**
   * Creates a portfolio row for a position summary.
   *
   * @param position the position summary to display
   * @return a search row representing the position
   * @throws IllegalArgumentException if position is null
   */
  public static SearchRow createPortfolioRow(PositionSummary position) {
    if (position == null) {
      throw new IllegalArgumentException("Position summary cannot be null.");
    }

    SearchRow row = new SearchRow(34, 18, 36, 12);
    row.getStyleClass().addAll(StyleClass.CARD, StyleClass.FACTORY_SEARCH_ROW);

    Label ticker = new Label(position.stock().getSymbol());
    ticker.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_TICKER);

    HBox tickerBox = new HBox(8, ticker);
    tickerBox.setAlignment(Pos.CENTER_LEFT);

    if (position.leveraged()) {
      Label leverageBadge = new Label(position.leverage().getMultiplier() + "x");
      leverageBadge.getStyleClass().add(StyleClass.FACTORY_PORTFOLIO_LEVERAGE_BADGE);
      tickerBox.getChildren().add(leverageBadge);
    }

    Label company = new Label(position.stock().getCompany());
    company.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_COMPANY);
    company.setAlignment(Pos.CENTER_LEFT);

    VBox stockBox = new VBox(2, tickerBox, company);
    stockBox.setAlignment(Pos.CENTER_LEFT);

    Label quantityLabel = new Label("shares");
    quantityLabel.getStyleClass().add(StyleClass.FACTORY_PORTFOLIO_QUANTITY_LABEL);

    Label quantityValue = new Label(
        position.sharesOwned()
            .setScale(4, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
    );
    quantityValue.getStyleClass().add(StyleClass.FACTORY_PORTFOLIO_QUANTITY_VALUE);

    VBox quantityBox = new VBox(2, quantityValue, quantityLabel);
    quantityBox.setAlignment(Pos.CENTER);

    Label total = new Label(CurrencyFormatter.format(position.positionValue()));
    total.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_PRICE);

    Label changeValue = new Label(CurrencyFormatter.format(position.profitLoss()));
    Label changePercent = new Label(
        "(" + PercentFormatter.format(position.roiPercent()) + ")"
    );

    changeValue.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_CHANGE);
    changePercent.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_CHANGE_PERCENT);

    PriceStyleHelper.applyPriceChangeStyle(changeValue, position.profitLoss());
    PriceStyleHelper.applyPriceChangeStyle(changePercent, position.profitLoss());

    HBox changeBox = new HBox(6, changeValue, changePercent);
    changeBox.setAlignment(Pos.CENTER_RIGHT);

    VBox priceBox = new VBox(2, total, changeBox);
    priceBox.setAlignment(Pos.CENTER_RIGHT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_ARROW);

    row.addToCell(stockBox, 0, 0, 1, 2);
    row.addToCell(quantityBox, 1, 0, 1, 2);
    row.addToCell(priceBox, 2, 0, 1, 2);
    row.addToCell(arrowIcon, 3, 0, 1, 2);

    GridPane.setHalignment(arrowIcon, HPos.CENTER);
    GridPane.setValignment(arrowIcon, VPos.CENTER);

    return row;
  }
}
