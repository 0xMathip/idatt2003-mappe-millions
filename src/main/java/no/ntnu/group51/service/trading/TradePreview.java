package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;
import no.ntnu.group51.model.transaction.Transaction;

public record TradePreview(
    TradeType tradeType,
    TradeMode tradeMode,
    Leverage leverage,
    BigDecimal quantity,
    BigDecimal marginRequired,
    BigDecimal total,
    Transaction transaction,
    LeveragedPosition leveragedPosition
) {

}
