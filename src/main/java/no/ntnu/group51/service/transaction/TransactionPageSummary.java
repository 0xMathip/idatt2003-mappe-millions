package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Transaction;

public record TransactionPageSummary(
    int totalTrades,
    int totalBought,
    int totalSold,
    BigDecimal totalTaxFees
) {
}
