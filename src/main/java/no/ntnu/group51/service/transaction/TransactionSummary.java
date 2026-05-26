package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Represents summarized transaction data for UI presentation.
 *
 * @param transaction the original transaction
 * @param stock the stock involved in the transaction
 * @param quantity the traded quantity
 * @param unitPrice the price per share
 * @param gross the gross transaction value
 * @param commission the transaction commission
 * @param tax the transaction tax
 * @param total the total transaction value after costs
 * @param type the display transaction type
 * @param note the transaction note
 * @param week the trading week of the transaction
 */
public record TransactionSummary(
    Transaction transaction,
    Stock stock,
    BigDecimal quantity,
    BigDecimal unitPrice,
    BigDecimal gross,
    BigDecimal commission,
    BigDecimal tax,
    BigDecimal total,
    String type,
    String note,
    int week
) {
}
