package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;

/**
 * Represents a summarized view of a portfolio position for UI presentation.
 *
 * @param stock the stock associated with the position
 * @param sharesOwned the number of shares owned
 * @param averageBuyPrice the average purchase price per share
 * @param currentPrice the current stock price
 * @param positionValue the total current value of the position
 * @param totalInvested the total capital invested in the position
 * @param profitLoss the current profit or loss
 * @param roiPercent the return on investment as a percentage
 * @param lowestPrice the lowest recorded stock price
 * @param highestPrice the highest recorded stock price
 * @param leveraged whether the position is leveraged
 * @param leverage the leverage level applied
 * @param marginRequired the margin required for leveraged positions
 * @param liquidationPrice the liquidation price for leveraged positions
 */
public record PositionSummary (
    Stock stock,
    BigDecimal sharesOwned,
    BigDecimal averageBuyPrice,
    BigDecimal currentPrice,
    BigDecimal positionValue,
    BigDecimal totalInvested,
    BigDecimal profitLoss,
    BigDecimal roiPercent,
    BigDecimal lowestPrice,
    BigDecimal highestPrice,
    boolean leveraged,
    Leverage leverage,
    BigDecimal marginRequired,
    BigDecimal liquidationPrice
) {

}
