package no.ntnu.group51.controller.market;

import java.math.RoundingMode;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;
import no.ntnu.group51.service.trading.TradePreview;
import no.ntnu.group51.service.trading.TradeService;
import no.ntnu.group51.view.components.market.TradePanel;
import no.ntnu.group51.view.util.CurrencyFormatter;

public class TradePanelController implements Observer {

  private final GameModel gameModel;
  private final TradePanel tradePanel;
  private final TradeService tradeService;

  private TradeMode selectedTradeMode = TradeMode.AMOUNT;
  private Leverage selectedLeverage = Leverage.OFF;

  public TradePanelController(
      GameModel gameModel,
      TradePanel tradePanel,
      TradeService tradeService
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    if (tradePanel == null) {
      throw new IllegalArgumentException("Trade panel cannot be null.");
    }
    if (tradeService == null) {
      throw new IllegalArgumentException("Trade service cannot be null.");
    }

    this.gameModel = gameModel;
    this.tradePanel = tradePanel;
    this.tradeService = tradeService;

    gameModel.addObserver(this);
    initialize();
  }

  private void initialize() {
    tradePanel.setOnTradeModeChanged(this::handleTradeModeChanged);
    tradePanel.setOnLeverageChanged(this::handleLeverageChanged);
    tradePanel.setOnBuy(() -> handleTrade(TradeType.BUY));
    tradePanel.setOnSell(() -> handleTrade(TradeType.SELL));
    tradePanel.setOnInputChanged(() -> updatePreview(TradeType.BUY));

    updateView();
  }

  private void handleTradeModeChanged(TradeMode tradeMode) {
    selectedTradeMode = tradeMode;
    tradePanel.updateTradeMode(tradeMode);
    updatePreview(TradeType.BUY);
  }

  private void handleLeverageChanged(Leverage leverage) {
    selectedLeverage = leverage;
    tradePanel.updateLeverage(leverage);
    updatePreview(TradeType.BUY);
  }

  private void handleTrade(TradeType tradeType) {
    try {
      TradePreview preview = createPreview(tradeType);
      tradeService.commitTrade(gameModel.getPlayer(), preview);

      tradePanel.clearInput();
      updateView();
      gameModel.notifyObservers();

    } catch (IllegalArgumentException e) {
      tradePanel.setEstimateText(e.getMessage());
    }
  }

  private void updatePreview(TradeType tradeType) {
    try {
      if (tradePanel.getInputText() == null || tradePanel.getInputText().isBlank()) {
        resetEstimate();
        return;
      }

      TradePreview preview = createPreview(tradeType);

      if (selectedTradeMode == TradeMode.SHARES) {
        String estimateText = CurrencyFormatter.format(preview.marginRequired());

        if (preview.leverage() != Leverage.OFF) {
          estimateText += " | Liq: "
              + CurrencyFormatter.format(preview.leveragedPosition().getLiquidationPrice());
        }

        tradePanel.setEstimateText(estimateText);
      } else {
        String estimateText =
            preview.quantity()
                .setScale(4, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();

        if (preview.leverage() != Leverage.OFF) {
          estimateText += " | Liq: "
              + CurrencyFormatter.format(preview.leveragedPosition().getLiquidationPrice());
        }

        tradePanel.setEstimateText(estimateText);
      }

    } catch (IllegalArgumentException e) {
      tradePanel.setEstimateText(e.getMessage());
    }
  }

  private TradePreview createPreview(TradeType tradeType) {
    Stock selectedStock = gameModel.getSelectedStock();

    return tradeService.createPreview(
        gameModel.getPlayer(),
        selectedStock,
        tradePanel.getInputText(),
        selectedTradeMode,
        tradeType,
        selectedLeverage,
        gameModel.getExchange().getWeek()
    );
  }

  private void resetEstimate() {
    if (selectedTradeMode == TradeMode.SHARES) {
      tradePanel.setEstimateText("$0.00");
    } else {
      tradePanel.setEstimateText("0 shares");
    }
  }

  private void updateView() {
    tradePanel.setCash(gameModel.getPlayer().getMoney());
    tradePanel.updateTradeMode(selectedTradeMode);
    tradePanel.updateLeverage(selectedLeverage);

    if (tradePanel.getInputText() == null || tradePanel.getInputText().isBlank()) {
      resetEstimate();
    } else {
      updatePreview(TradeType.BUY);
    }
  }

  @Override
  public void update() {
    updateView();
  }
}
