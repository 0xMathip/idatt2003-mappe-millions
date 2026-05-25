package no.ntnu.group51.view.components.market;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.StockRowFactory;

public class MarketSearchMenu implements View {

  private final SearchMenu root;
  private Consumer<Stock> onStockSelected = stock -> {};

  public MarketSearchMenu() {
    this.root = new SearchMenu("⌕ Search stocks", true);
  }

  public void updateStocks(List<Stock> stocks) {
    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }

    List<SearchRow> rows = stocks.stream()
        .map(this::createRow)
        .toList();

    root.setRows(rows);
  }

  private SearchRow createRow(Stock stock) {
    SearchRow row = StockRowFactory.createStockRow(stock);
    row.setOnMouseClicked(event -> onStockSelected.accept(stock));
    return row;
  }

  public void setOnStockSelected(Consumer<Stock> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onStockSelected = handler;
  }

  public String getSearchText() {
    return root.getSearchField().getText();
  }

  public void setOnSearchChanged(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    root.getSearchField().textProperty().addListener(
        (obs, oldValue, newValue) -> handler.run()
    );
  }

  public void setOnClose(Runnable onClose) {
    if (onClose == null) {
      throw new IllegalArgumentException("Close handler cannot be null.");
    }

    root.setOnClose(onClose);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}