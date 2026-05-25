package no.ntnu.group51.controller.market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.view.components.market.MarketHoldingInfoCard;
import no.ntnu.group51.view.util.CurrencyFormatter;

/**
 * Controller for updating the market holding information card.
 *
 * <p>Displays normal share holdings and leveraged position information
 * for the currently selected stock.
 */
public class MarketHoldingInfoController implements Observer {

  private static final int QUANTITY_SCALE = 4;

  private final GameModel gameModel;
  private final MarketHoldingInfoCard holdingInfoCard;

  /**
   * Creates a market holding info controller.
   *
   * @param gameModel the game model
   * @param holdingInfoCard the holding info card view
   * @throws IllegalArgumentException if any argument is null
   */
  public MarketHoldingInfoController(
      GameModel gameModel,
      MarketHoldingInfoCard holdingInfoCard
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    if (holdingInfoCard == null) {
      throw new IllegalArgumentException("Holding info card cannot be null.");
    }

    this.gameModel = gameModel;
    this.holdingInfoCard = holdingInfoCard;

    gameModel.addObserver(this);
    update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update() {
    Stock selectedStock = gameModel.getSelectedStock();

    if (selectedStock == null) {
      holdingInfoCard.clear();
      return;
    }

    updateNormalHoldings(selectedStock);
    updateLeveragedHoldings(selectedStock);
  }

  private void updateNormalHoldings(Stock stock) {
    BigDecimal normalShares = gameModel.getPlayer()
        .getPortfolio()
        .getShares(stock.getSymbol())
        .stream()
        .map(Share::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    holdingInfoCard.updateNormalHoldings(
        formatQuantity(normalShares) + " shares"
    );
  }

  private void updateLeveragedHoldings(Stock stock) {
    List<LeveragedPosition> positions = gameModel.getPlayer()
        .getPortfolio()
        .getLeveragedPositions()
        .stream()
        .filter(position -> position.getShare().getStock().equals(stock))
        .toList();

    if (positions.isEmpty()) {
      holdingInfoCard.updateLeveragedHoldings(
          "0 shares",
          "-",
          "Liq: -"
      );
      return;
    }

    BigDecimal totalLeveragedShares = positions.stream()
        .map(position -> position.getShare().getQuantity())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    holdingInfoCard.updateLeveragedHoldings(
        formatQuantity(totalLeveragedShares) + " shares",
        getLeverageText(positions),
        getLiquidationText(positions)
    );
  }

  private String getLeverageText(List<LeveragedPosition> positions) {
    Set<Leverage> leverages = positions.stream()
        .map(LeveragedPosition::getLeverage)
        .collect(Collectors.toSet());

    if (leverages.size() == 1) {
      return leverages.iterator().next().getMultiplier() + "x";
    }

    return "Mixed";
  }

  private String getLiquidationText(List<LeveragedPosition> positions) {
    Set<BigDecimal> liquidationPrices = positions.stream()
        .map(LeveragedPosition::getLiquidationPrice)
        .collect(Collectors.toSet());

    if (liquidationPrices.size() == 1) {
      return "Liq: " + CurrencyFormatter.format(liquidationPrices.iterator().next());
    }

    return "Liq: Multiple";
  }

  private String formatQuantity(BigDecimal quantity) {
    if (quantity == null) {
      throw new IllegalArgumentException("Quantity cannot be null.");
    }
    return quantity
        .setScale(QUANTITY_SCALE, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString();
  }
}