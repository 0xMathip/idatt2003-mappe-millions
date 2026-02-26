package no.ntnu.group51.Transaction;

import no.ntnu.group51.Calculator.TransactionCalculator;
import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Share;

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
