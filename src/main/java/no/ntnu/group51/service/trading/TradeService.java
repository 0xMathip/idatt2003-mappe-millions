package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;

public class TradeService {

  private static final int MONEY_SCALE = 2;
  private static final int QUANTITY_SCALE = 8;

  private final LeverageService leverageService;

  public TradeService(LeverageService leverageService) {
    if (leverageService == null) {
      throw new IllegalArgumentException("Leverage service cannot be null.");
    }

    this.leverageService = leverageService;
  }

  public TradePreview createPreview(
      Stock stock,
      String input,
      TradeMode tradeMode,
      TradeType tradeType,
      Leverage leverage
  ) {
    validateInputs(stock, input, tradeMode, tradeType, leverage);

    BigDecimal enteredValue = parseInput(input);
    BigDecimal price = resolvePrice(stock, tradeType);

    BigDecimal quantity;
    BigDecimal marginRequired;
    }
  }
}
