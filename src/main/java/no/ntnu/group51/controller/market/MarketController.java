package no.ntnu.group51.controller.market;

import java.util.List;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.service.trading.TradeService;
import no.ntnu.group51.view.components.market.MarketSearchMenu;
import no.ntnu.group51.view.pages.MarketView;

/**
 * Controller for the market page.
 *
 * <p>Handles stock selection, stock search, and updates the market view
 * when the game model changes.
 */
public class MarketController implements Observer {

  private final GameModel gameModel;
  private final MarketView marketView;
  private final TradePanelController tradePanelController;
  private final MarketHoldingInfoController holdingInfoController;

  /**
   * Creates a market controller.
   *
   * @param gameModel    the game model
   * @param marketView   the market view
   * @param tradeService the trade service used by the trade panel
   * @throws IllegalArgumentException if any argument is null
   */
  public MarketController(
      GameModel gameModel,
      MarketView marketView,
      TradeService tradeService
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    if (marketView == null) {
      throw new IllegalArgumentException("Market view cannot be null.");
    }
    if (tradeService == null) {
      throw new IllegalArgumentException("Trade service cannot be null.");
    }

    this.gameModel = gameModel;
    this.marketView = marketView;

    this.tradePanelController = new TradePanelController(
        gameModel,
        marketView.getTradePanel(),
        tradeService
    );

    this.holdingInfoController =
        new MarketHoldingInfoController(
            gameModel,
            marketView.getHoldingInfoCard()
        );

    gameModel.addObserver(this);
    initialize();
  }

  private void initialize() {
    marketView.setOnStockSelectorClicked(this::openSearchMenu);
    updateView();
  }

  private void openSearchMenu() {
    MarketSearchMenu searchMenu = new MarketSearchMenu();

    searchMenu.setOnSearchChanged(() -> updateSearchMenu(searchMenu));
    searchMenu.setOnStockSelected(stock -> {
      gameModel.setSelectedStock(stock);
      marketView.closeStockSearchMenu();
    });
    searchMenu.setOnClose(marketView::closeStockSearchMenu);

    updateSearchMenu(searchMenu);

    marketView.showStockSearchMenu(searchMenu);
  }

  private void updateSearchMenu(MarketSearchMenu searchMenu) {
    if (searchMenu == null) {
      throw new IllegalArgumentException("Search menu cannot be null.");
    }
    searchMenu.updateStocks(
        gameModel.getExchange().findStocks(searchMenu.getSearchText())
    );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update() {
    updateView();
  }

  private void updateView() {
    Stock selectedStock = getSelectedOrFirstStock();

    if (selectedStock == null) {
      marketView.clear();
      return;
    }

    marketView.updateSelectedStock(selectedStock);
  }

  private Stock getSelectedOrFirstStock() {
    Stock selectedStock = gameModel.getSelectedStock();

    if (selectedStock != null) {
      return selectedStock;
    }

    List<Stock> stocks = gameModel
        .getExchange().findStocks("");

    if (stocks.isEmpty()) {
      return null;
    }

    selectedStock = stocks.getFirst();
    gameModel.setSelectedStock(selectedStock);

    return selectedStock;
  }
}