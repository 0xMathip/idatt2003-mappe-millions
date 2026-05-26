package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;

/**
 * Represents a summarized view of portfolio metrics for presentation.
 *
 * @param portfolioValue the current total value of the portfolio
 * @param netWorth the player's total net worth including cash
 * @param availableCash the player's available cash balance
 * @param totalInvested the total amount invested in positions
 * @param totalReturn the total return compared to starting capital
 * @param totalReturnPercent the total return as a percentage
 */
public record PortfolioSummary(
    BigDecimal portfolioValue,
    BigDecimal netWorth,
    BigDecimal availableCash,
    BigDecimal totalInvested,
    BigDecimal totalReturn,
    BigDecimal totalReturnPercent
) {

}
