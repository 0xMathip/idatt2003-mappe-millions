package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Transaction;

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
