package no.ntnu.group51.service.transaction;

import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Creates human-readable notes for completed transactions.
 */
public class TransactionNoteService {

  /**
   * Creates a descriptive note for a transaction.
   *
   * @param transaction the transaction to describe
   * @param leveraged   whether the transaction involves leverage
   * @return a descriptive transaction note
   * @throws IllegalArgumentException if transaction is null
   */
  public String createNote(Transaction transaction, boolean leveraged) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    if (transaction instanceof Purchase) {
      if (leveraged) {
        return "Leveraged position opened.";
      }

      return "Position opened.";
    }

    if (transaction instanceof Sale) {
      if (leveraged) {
        return "Leveraged position closed.";
      }

      return "Position closed.";
    }

    return "Transaction completed.";
  }
}