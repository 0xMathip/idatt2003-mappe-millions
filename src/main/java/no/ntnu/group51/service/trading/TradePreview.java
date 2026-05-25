package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Represents a prepared trade preview before execution.
 *
 * @param tradeType the type of trade being previewed
 * @param tradeMode the input mode used for the trade
 * @param leverage the selected leverage level
 * @param quantity the calculated share quantity
 * @param marginRequired the required margin for leveraged trades
 * @param total the total transaction value
 * @param transaction the prepared transaction, if applicable
 * @param leveragedPosition the prepared leveraged position, if applicable
 */
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
