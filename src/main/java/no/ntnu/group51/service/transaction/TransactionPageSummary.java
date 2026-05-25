package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;

/**
 * Represents summarized transaction statistics for the transaction page.
 *
 * @param totalTrades  the total number of transactions
 * @param totalBought  the total number of purchase transactions
 * @param totalSold    the total number of sale transactions
 * @param totalTaxFees the total accumulated taxes and fees
 */
public record TransactionPageSummary(
    int totalTrades,
    int totalBought,
    int totalSold,
    BigDecimal totalTaxFees
) {
}
