package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

public class TransactionService {

  private static final int MONEY_SCALE = 2;

  public List<TransactionSummary> createTransactionSummaries(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    return player.getTransactionArchive()
        .findTransactions(null)
        .stream()
        .map(this::createTransactionSummary)
        .toList();
  }

  public TransactionPageSummary createPageSummary(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    List<TransactionSummary> summaries = createTransactionSummaries(player);

    int totalTrades = summaries.size();

    int totalBought = (int) summaries
        .stream()
        .filter(summary -> summary.transaction() instanceof Purchase)
        .count();

    int totalSold = (int) summaries
        .stream()
        .filter(summary -> summary.transaction() instanceof Sale)
        .count();

    BigDecimal totalTaxFees = summaries
        .stream()
        .map(TransactionSummary::tax)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

    return new TransactionPageSummary(
        totalTrades,
        totalBought,
        totalSold,
        totalTaxFees
    );
  }

  private TransactionSummary createTransactionSummary(Transaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    Share share = transaction.getShare();
    Stock stock = share.getStock();

    String type = transaction instanceof Purchase ? "Buy" : "Sell";

    String note = "transaction.note();";

    return new TransactionSummary(
        transaction,
        stock,
        share.getQuantity(),
        share.getPurchasePrice(),
        transaction.getCalculator().calculateGross().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        transaction.getCalculator().calculateTax().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        transaction.getCalculator().calculateTotal().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        type,
        note,
        transaction.getWeek()
    );
  }

}
