package no.ntnu.group51.view.components.market;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.StockRowFactory;

/**
 * Search menu for displaying and selecting market stocks.
 */
public class MarketSearchMenu implements View {

  private final SearchMenu root;
  private Consumer<Stock> onStockSelected = stock -> {};

  /**
   * Creates a market search menu.
   */
  public MarketSearchMenu() {
    this.root = new SearchMenu("⌕ Search stocks", true);
  }

  /**
   * Updates the displayed stock rows.
   *
   * @param stocks the stocks to display
   * @throws IllegalArgumentException if stocks is null
   */
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

  /**
   * Sets the action to run when a stock is selected.
   *
   * @param handler the stock selection handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnStockSelected(Consumer<Stock> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onStockSelected = handler;
  }

  /**
   * Returns the current search text.
   *
   * @return the search text
   */
  public String getSearchText() {
    return root.getSearchField().getText();
  }

  /**
   * Sets the action to run when the search text changes.
   *
   * @param handler the search change handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnSearchChanged(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    root.getSearchField().textProperty().addListener(
        (obs, oldValue, newValue) -> handler.run()
    );
  }

  /**
   * Sets the action to run when the menu is closed.
   *
   * @param onClose the close handler
   * @throws IllegalArgumentException if onClose is null
   */
  public void setOnClose(Runnable onClose) {
    if (onClose == null) {
      throw new IllegalArgumentException("Close handler cannot be null.");
    }

    root.setOnClose(onClose);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}