package no.ntnu.group51.model.transaction;

import java.math.BigDecimal;
import no.ntnu.group51.model.calculator.TransactionCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;

/**
 * The class for any kind of transaction.
 */
public abstract class Transaction {
  protected Share share;
  protected int week;
  protected TransactionCalculator calculator;
  protected boolean committed = false;

  /**
   * Creates a transaction.
   *
   * @param share The share being purchased/sold
   * @param week The week the transaction is happening
   * @param calculator The type of calculator being made (Sale- or PurchaseCalculator)
   */
  public Transaction(Share share, int week, TransactionCalculator calculator) {
    if (share == null) {
      throw new IllegalArgumentException("share is null");
    }

    if (week <= 0) {
      throw new IllegalArgumentException("week is negative");
    }

    if (calculator == null) {
      throw new IllegalArgumentException("calculator is null");
    }
    this.share = share;
    this.week = week;
    this.calculator = calculator;
  }

  public Share getShare() {
    return share;
  }

  public int getWeek() {
    return week;
  }

  /**
   * Returns the total calculated value of this transaction.
   *
   * <p>This method encapsulates the calculator so other classes do not need
   * to know how transaction values are calculated internally.
   *
   * @return the total calculated value of this transaction
   */
  public BigDecimal getTotal() {
    return calculator.calculateTotal();
  }

  public TransactionCalculator getCalculator() {
    return calculator;
  }

  public boolean isCommitted() {
    return committed;
  }


  /**
   * The purpose is to convert the money of the player from their capital
   * to their portfolio and vise versa. After this it will save the
   * transaction in the transactionArchive of the player.
   *
   * @param player The player making the transaction
   */
  public abstract void commit(Player player);
}
