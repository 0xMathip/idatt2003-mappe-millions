package no.ntnu.group51.Transaction;

import no.ntnu.group51.Stocks.Share;

/**
 * A factory for creating transactions.
 */
public class TransactionFactory {

  /**
   * Creates a transaction of the type passed in.
   *
   * @param type a string which is either "buy" or "sell" which creates a
   *             transaction corresponding to a purchase (buy) or a sale (sell).
   * @param share the share being bought or sold
   * @param week the week the transaction is happening
   * @return either the Purchase or Sale object
   */
  public static Transaction createTransaction(String type, Share share, int week) {
    if (share == null) {
      throw new IllegalArgumentException("share is null");
    }
    if (week <= 0) {
      throw new IllegalArgumentException("week is negative or zero");
    }

    if (type.equalsIgnoreCase("buy")) {
      return new Purchase(share, week);

    } else if (type.equalsIgnoreCase("sell")) {
      return new Sale(share, week);
    }
    throw new IllegalArgumentException("invalid type");
  }
}
