package no.ntnu.group51.service.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Liquidation;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.model.transaction.TransactionArchive;

/**
 * Creates transaction summaries and transaction page statistics for UI presentation.
 */
public class TransactionService {

  private final TransactionNoteService noteService = new TransactionNoteService();
  private static final int MONEY_SCALE = 2;

  /**
   * Creates summaries for all transactions in a player's archive.
   *
   * @param player the player whose transactions should be summarized
   * @return a list of transaction summaries
   * @throws IllegalArgumentException if player is null
   */
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

  /**
   * Creates aggregated transaction statistics for a player.
   *
   * @param player the player whose transaction statistics should be calculated
   * @return a transaction page summary
   * @throws IllegalArgumentException if player is null
   */
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
        .map(summary -> summary.commission().add(summary.tax()))
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

    String note = transaction instanceof Liquidation
        ? "Liquidated."
        : noteService.createNote(transaction, isLeveraged(transaction));

    return new TransactionSummary(
        transaction,
        stock,
        share.getQuantity(),
        share.getPurchasePrice(),
        transaction.getCalculator().calculateGross().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        transaction.getCalculator().calculateCommission().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        transaction.getCalculator().calculateTax().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        transaction.getCalculator().calculateTotal().setScale(MONEY_SCALE, RoundingMode.HALF_UP),
        type,
        note,
        transaction.getWeek()
    );
  }

  private boolean isLeveraged(Transaction transaction) {
    return transaction.getShare().getQuantity().scale() > 0;
  }

}
