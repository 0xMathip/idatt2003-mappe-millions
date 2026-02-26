package no.ntnu.group51.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the transaction archive.
 * Every transaction will be archived in this archive.
 */
public class TransactionArchive {
  private List<Transaction> transactions;

  /**
   * Creates a new transaction archive.
   */
  public TransactionArchive() {
    List<Transaction> transactions = new ArrayList<>();
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
}
