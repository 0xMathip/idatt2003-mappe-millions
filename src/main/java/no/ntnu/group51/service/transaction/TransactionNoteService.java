package no.ntnu.group51.service.transaction;

import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

public class TransactionNoteService {

  public String createNote(Transaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    if (transaction instanceof Purchase) {
      return "Position opened.";
    }

    if (transaction instanceof Sale) {
      return "Position closed.";
    }
    return "Transaction completed";
  }
}
