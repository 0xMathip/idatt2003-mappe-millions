package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import no.ntnu.group51.model.calculator.LeverageCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.model.transaction.TransactionFactory;

public class TradeService {

  private static final int MONEY_SCALE = 2;
  private static final int QUANTITY_SCALE = 8;
  private static final BigDecimal MIN_THRESHOLD = new BigDecimal("0.0001");

  private final LeverageService leverageService;

  public TradeService(LeverageService leverageService) {
    if (leverageService == null) {
      throw new IllegalArgumentException("Leverage service cannot be null.");
    }

    this.leverageService = leverageService;
  }

  public TradePreview createPreview(
      Player player,
      Stock stock,
      String input,
      TradeMode tradeMode,
      TradeType tradeType,
      Leverage leverage,
      int week
  ) {
    validateInputs(player, stock, input, tradeMode, tradeType, leverage, week);

    BigDecimal enteredValue = parseInput(input);
    BigDecimal quantity =
        tradeType == TradeType.SELL
            && leverage != Leverage.OFF
            && tradeMode == TradeMode.AMOUNT
            ? calculateLeveragedSellQuantity(player, stock, enteredValue)
            : calculateQuantity(stock, enteredValue, tradeMode, tradeType, leverage);

    BigDecimal marginRequired = calculateMarginRequired(
        stock,
        quantity,
        enteredValue,
        tradeMode,
        tradeType,
        leverage
    );

    if (leverage == Leverage.OFF) {
      Transaction transaction = createNormalTransaction(
          player,
          stock,
          quantity,
          tradeType,
          week);

      return new TradePreview(
          tradeType,
          tradeMode,
          leverage,
          quantity,
          marginRequired,
          transaction.getTotal(),
          transaction,
          null
      );
    }

    LeveragedPosition leveragedPosition = tradeType == TradeType.BUY
        ? createLeveragedPosition(stock, quantity, marginRequired, leverage)
        : createSellLeveragedPosition(player, stock, quantity);

    Transaction transaction = TransactionFactory.createTransaction(
        tradeType.name(),
        leveragedPosition.getShare(),
        week
    );

    LeverageCalculator levCalc = new LeverageCalculator(leveragedPosition);

    return new TradePreview(
        tradeType,
        tradeMode,
        leverage,
        quantity,
        marginRequired,
        levCalc.calculateTotal(),
        transaction,
        leveragedPosition
    );
  }

  public void commitTrade(Player player, TradePreview preview) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (preview == null) {
      throw new IllegalArgumentException("Trade preview cannot be null.");
    }

    if (preview.leverage() == Leverage.OFF) {
      if (preview.tradeType() == TradeType.BUY) {
        validatePlayerCanAfford(player, preview.total());
      }
      preview.transaction().commit(player);
      player.getTransactionArchive().add(preview.transaction());
      return;
    }

    if (preview.tradeType() == TradeType.BUY) {
      validatePlayerCanAfford(player, preview.marginRequired());

      player.withdrawMoney(preview.marginRequired());
      player.getPortfolio().addLeveragedPosition(preview.leveragedPosition());
      player.getTransactionArchive().add(preview.transaction());
      return;
    }

    closeLeveragedPosition(player, preview);
    player.getTransactionArchive().add(preview.transaction());
  }

  private Transaction createNormalTransaction(
      Player player,
      Stock stock,
      BigDecimal quantity,
      TradeType tradeType,
      int week
  ) {
    Share share = tradeType == TradeType.BUY
        ? new Share(stock, quantity, stock.getSalesPrice())
        : createSellShare(player, stock, quantity);

    return TransactionFactory.createTransaction(
        tradeType.name(),
        share,
        week
    );
  }

  private LeveragedPosition createLeveragedPosition(
      Stock stock,
      BigDecimal quantity,
      BigDecimal marginRequired,
      Leverage leverage
  ) {
    Share share = new Share(stock, quantity, stock.getSalesPrice());

    LeverageSummary summary =
        leverageService.createSummary(stock, marginRequired, leverage);

    return new LeveragedPosition(
        share,
        leverage,
        summary.marginRequired(),
        summary.exposure(),
        summary.liquidationPrice()
    );
  }

  private LeveragedPosition createSellLeveragedPosition(Player player, Stock stock,
                                                        BigDecimal quantity) {
    LeveragedPosition ownedPosition = player.getPortfolio()
        .getLeveragedPositions()
        .stream()
        .filter(position -> position.getShare().getStock().equals(stock))
        .filter(position -> position.getShare().getQuantity().compareTo(quantity) >= 0)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Not enough leveraged shares owned."));

    BigDecimal ownedQuantity = ownedPosition.getShare().getQuantity();
    BigDecimal ratio = quantity.divide(ownedQuantity, QUANTITY_SCALE, RoundingMode.HALF_UP);

    Share sellShare = new Share(
        stock,
        quantity,
        ownedPosition.getShare().getPurchasePrice()
    );

    return new LeveragedPosition(
        sellShare,
        ownedPosition.getLeverage(),
        ownedPosition.getMarginRequired().multiply(ratio),
        ownedPosition.getExposure().multiply(ratio),
        ownedPosition.getLiquidationPrice()
    );
  }

  private void closeLeveragedPosition(Player player, TradePreview preview) {
    LeveragedPosition sellPosition = preview.leveragedPosition();

    LeveragedPosition ownedPosition = player.getPortfolio()
        .getLeveragedPositions()
        .stream()
        .filter(
            position -> position.getShare().getStock().equals(sellPosition.getShare().getStock()))
        .filter(position -> position.getShare().getQuantity()
            .compareTo(sellPosition.getShare().getQuantity()) >= 0)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Leveraged position not found."));

    player.addMoney(preview.total());
    player.getPortfolio().removeLeveragedPosition(ownedPosition);

    BigDecimal remainingQuantity = ownedPosition.getShare().getQuantity()
        .subtract(sellPosition.getShare().getQuantity())
        .setScale(QUANTITY_SCALE, RoundingMode.HALF_UP);

    if (remainingQuantity.abs().compareTo(MIN_THRESHOLD) <= 0) {
      remainingQuantity = BigDecimal.ZERO;
    }

    if (remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
      BigDecimal ratio = remainingQuantity.divide(
          ownedPosition.getShare().getQuantity(),
          QUANTITY_SCALE,
          RoundingMode.HALF_UP
      );

      Share remainingShare = new Share(
          ownedPosition.getShare().getStock(),
          remainingQuantity,
          ownedPosition.getShare().getPurchasePrice()
      );

      LeveragedPosition remainingPosition = new LeveragedPosition(
          remainingShare,
          ownedPosition.getLeverage(),
          ownedPosition.getMarginRequired().multiply(ratio),
          ownedPosition.getExposure().multiply(ratio),
          ownedPosition.getLiquidationPrice()
      );

      player.getPortfolio().addLeveragedPosition(remainingPosition);
    }
  }

  private Share createSellShare(Player player, Stock stock, BigDecimal quantity) {
    return player.getPortfolio()
        .getShares()
        .stream()
        .filter(share -> share.getStock().equals(stock))
        .filter(share -> share.getQuantity().compareTo(quantity) >= 0)
        .findFirst()
        .map(share -> new Share(stock, quantity, share.getPurchasePrice()))
        .orElseThrow(() -> new IllegalArgumentException("Not enough shares owned."));
  }

  private BigDecimal calculateQuantity(
      Stock stock,
      BigDecimal enteredValue,
      TradeMode tradeMode,
      TradeType tradeType,
      Leverage leverage
  ) {
    if (tradeMode == TradeMode.SHARES) {
      return enteredValue.setScale(QUANTITY_SCALE, RoundingMode.HALF_UP);
    }

    if (tradeType == TradeType.SELL) {
      return enteredValue.divide(
          stock.getSalesPrice(),
          QUANTITY_SCALE,
          RoundingMode.HALF_UP
      );
    }

    LeverageSummary summary =
        leverageService.createSummary(stock, enteredValue, leverage);

    return summary.exposure()
        .divide(stock.getSalesPrice(), QUANTITY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateLeveragedSellQuantity(
      Player player,
      Stock stock,
      BigDecimal enteredValue
  ) {
    LeveragedPosition ownedPosition = player.getPortfolio()
        .getLeveragedPositions()
        .stream()
        .filter(position -> position.getShare().getStock().equals(stock))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No leveraged position owned."));

    LeverageCalculator calculator = new LeverageCalculator(ownedPosition);
    BigDecimal positionValue = calculator.calculateTotal();

    if (enteredValue.compareTo(positionValue) >= 0) {
      return ownedPosition.getShare().getQuantity();
    }

    BigDecimal ratio = enteredValue.divide(
        positionValue,
        QUANTITY_SCALE,
        RoundingMode.HALF_UP
    );

    return ownedPosition.getShare()
        .getQuantity()
        .multiply(ratio)
        .setScale(QUANTITY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateMarginRequired(
      Stock stock,
      BigDecimal quantity,
      BigDecimal enteredValue,
      TradeMode tradeMode,
      TradeType tradeType,
      Leverage leverage
  ) {
    if (tradeMode == TradeMode.AMOUNT) {
      if (leverage == Leverage.OFF || tradeType == TradeType.BUY) {
        return enteredValue.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
      }

      BigDecimal multiplier = leverageService.getMultiplier(leverage);

      return enteredValue.divide(
          multiplier,
          MONEY_SCALE,
          RoundingMode.HALF_UP
      );
    }

    BigDecimal exposure = quantity.multiply(stock.getSalesPrice());

    return exposure.divide(
        leverageService.getMultiplier(leverage),
        MONEY_SCALE,
        RoundingMode.HALF_UP
    );
  }

  private void validatePlayerCanAfford(Player player, BigDecimal amount) {
    if (player.getMoney().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds.");
    }
  }

  private void validateInputs(
      Player player,
      Stock stock,
      String input,
      TradeMode tradeMode,
      TradeType tradeType,
      Leverage leverage,
      int week
  ) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException("Input cannot be null or blank.");
    }
    if (tradeMode == null) {
      throw new IllegalArgumentException("Trade mode cannot be null.");
    }
    if (tradeType == null) {
      throw new IllegalArgumentException("Trade type cannot be null.");
    }
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }
    if (week <= 0) {
      throw new IllegalArgumentException("Week must be positive.");
    }
  }

  private BigDecimal parseInput(String input) {
    try {
      BigDecimal value = new BigDecimal(input.trim());

      if (value.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Input must be greater than zero.");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number input.");
    }
  }
}
