package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;

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
