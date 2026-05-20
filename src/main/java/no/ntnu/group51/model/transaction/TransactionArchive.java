package no.ntnu.group51.model.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the transaction archive.
 * Every transaction will be archived in this archive.
 */
public class TransactionArchive {
  private final List<Transaction> transactions;

  /**
   * Creates a new transaction archive.
   */
  public TransactionArchive() {
    this.transactions = new ArrayList<>();
  }

  /**
   * Adds a transaction to the list of transactions.
   *
   * @param transaction The transaction you want to add
   * @return True if it was added successfully
   *         and false if something went wrong
   */
  public boolean add(Transaction transaction) {
    try {
      transactions.add(transaction);
      return true;

    } catch (Exception e) {
      return false;
    }
  }

  public boolean isEmpty() {
    return transactions.isEmpty();
  }

  /**
   * Filters transactions by week, then retrieves the transactions from the given week to list.
   *
   * @param week The week you want to see transactions from
   * @return A list of the transactions in the given week
   */
  public List<Transaction> getTransactions(int week) {
    if (week <= 0) {
      throw new IllegalArgumentException("week is 0 or negative");
    }
    return transactions.stream()
        .filter(t -> t.getWeek() == week)
        .toList();
  }

  /**
   * Filters transactions first by the given week,
   * then by type Purchase,
   * then converts the transactions to type Purchase.
   *
   * @param week The week you want to see transactions from
   * @return A list of the purchases in the given week
   */
  public List<Purchase> getPurchases(int week) {
    if (week <= 0) {
      throw new IllegalArgumentException("week is 0 or negative");
    }
    return transactions.stream()
        .filter(t -> t.getWeek() == week)
        .filter(t -> t instanceof Purchase)
        .map(t -> (Purchase) t)
        .toList();
  }

  /**
   * Filters transactions first by the given week,
   * then by type Sale,
   * then converts the transactions to type Sale.
   *
   * @param week The week you want to see transactions from
   * @return A list of the sales in the given week
   */
  public List<Sale> getSales(int week) {
    if (week <= 0) {
      throw new IllegalArgumentException("week is 0 or negative");
    }
    return transactions.stream()
        .filter(t -> t.getWeek() == week)
        .filter(t -> t instanceof Sale)
        .map(t -> (Sale) t)
        .toList();
  }

  /**
   * Counts the distinct weeks of trading.
   * Gets the week for every transaction,
   * keeps only the distinct ones,
   * then counts the weeks.
   *
   * @return The distinct weeks of trading
   */
  public int countDistinctWeeks() {
    return (int) transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .count();
  }

  /**
   * Searches the transaction archive for transactions matching the query.
   *
   * <p>If the query is blank, all transactions are returned.
   * Matching is performed against stock symbol, company name,
   * transaction type, and week number.
   *
   * @param query the search query
   * @return a list of matching transactions
   */
  public List<Transaction> findTransactions(String query) {
    if (query == null || query.isBlank()) {
      return List.copyOf(transactions);
    }

    String search = query.toLowerCase().trim();

    return transactions.stream()
        .filter(transaction ->
            transaction.getShare().getStock().getSymbol().toLowerCase().contains(search)
                || transaction.getShare().getStock().getCompany().toLowerCase().contains(search)
                || transactionTypeMatches(transaction, search)
                || String.valueOf(transaction.getWeek()).contains(search)
        )
        .toList();
  }

  /**
   * Checks whether the given transaction type matches the search query.
   *
   * <p>Supported transaction types are buy and sell.
   *
   * @param transaction the transaction to check
   * @param search the search query
   * @return true if the transaction type matches the query, false otherwise
   */
  private boolean transactionTypeMatches(Transaction transaction, String search) {
    if (transaction instanceof Purchase) {
      return "buy".startsWith(search);
    }

    if (transaction instanceof Sale) {
      return "sell".startsWith(search);
    }

    return false;
  }
}
