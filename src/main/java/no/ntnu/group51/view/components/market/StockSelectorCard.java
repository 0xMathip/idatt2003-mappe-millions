package no.ntnu.group51.view.components.market;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;
import org.kordamp.ikonli.javafx.FontIcon;

public class StockSelectorCard implements View {

  private final HBox root = new HBox();

  private final Label tickerLabel = new Label();
  private final Label companyLabel = new Label();
  private final Label priceLabel = new Label();
  private final Label changeLabel = new Label();

  public StockSelectorCard() {
    root.getStyleClass().addAll("card", "stock-selector-card");
    root.setAlignment(Pos.CENTER_LEFT);

    createLayout();
  }

  private void createLayout() {
    tickerLabel.getStyleClass().add("stock-selector-card-ticker-label");
    companyLabel.getStyleClass().add("stock-selector-card-company-label");
    priceLabel.getStyleClass().add("stock-selector-card-price-label");
    changeLabel.getStyleClass().add("stock-selector-card-change-label");

    Region topSpacer = new Region();
    HBox.setHgrow(topSpacer, Priority.ALWAYS);

    Region botSpacer = new Region();
    HBox.setHgrow(botSpacer, Priority.ALWAYS);

    HBox topRow = new HBox(tickerLabel, topSpacer, priceLabel);
    HBox botRow = new HBox(companyLabel, botSpacer, changeLabel);

    topRow.getStyleClass().add("stock-selector-card-row");
    botRow.getStyleClass().add("stock-selector-card-row");

    topRow.setAlignment(Pos.CENTER_LEFT);
    botRow.setAlignment(Pos.CENTER_LEFT);

    VBox content = new VBox(topRow, botRow);
    content.getStyleClass().add("stock-selector-card-content");
    content.setAlignment(Pos.CENTER_LEFT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-down-alt");
    arrowIcon.getStyleClass().add("stock-selector-card-arrow");

    root.getChildren().addAll(content, arrowIcon);
  }

  public void updateStock(Stock stock) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }

    tickerLabel.setText(stock.getSymbol());
    companyLabel.setText(stock.getCompany());
    priceLabel.setText(CurrencyFormatter.format(stock.getSalesPrice()));
    changeLabel.setText(stock.getLatestPriceChangePercent().toPlainString() + "%");

    PriceStyleHelper.applyPriceChangeStyle(
        changeLabel,
        stock.getLatestPriceChange()
    );
  }

  public void clear() {
    tickerLabel.setText("-");
    companyLabel.setText("No stock selected");
    priceLabel.setText("$0.00");
    changeLabel.setText("0%");
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}