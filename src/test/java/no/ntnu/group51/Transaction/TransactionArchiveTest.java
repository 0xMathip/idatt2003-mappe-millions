package no.ntnu.group51.Transaction;

import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionArchiveTest {

  private TransactionArchive archive;
  private Stock apple;
  private Share share;

  @BeforeEach
  void testSetup() {
    archive = new TransactionArchive();
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"));
    share = new Share(apple, BigDecimal.ONE, apple.getSalesPrice());
  }

  @Test
  void archiveStartsEmpty() {
    assertTrue(archive.isEmpty());
  }

  @Test
  void addAddsTransaction() {
    Transaction transaction = new Purchase(share, 1);

    assertTrue(archive.add(transaction));
    assertFalse(archive.isEmpty());
  }

  @Test
  void getTransactionsFiltersByWeek() {
    archive.add(new Purchase(share, 1));
    archive.add(new Sale(share, 2));

    List<Transaction> week1 = archive.getTransactions(1);
    List<Transaction> week2 = archive.getTransactions(2);

    assertEquals(1, week1.size());
    assertEquals(1, week2.size());
  }

  @Test
  void getPurchasesReturnsOnlyPurchases() {
    archive.add(new Purchase(share, 1));
    archive.add(new Sale(share, 1));

    List<Purchase> purchases = archive.getPurchases(1);

    assertEquals(1, purchases.size());
  }

  @Test
  void getSalesReturnsOnlySales() {
    archive.add(new Purchase(share, 1));
    archive.add(new Sale(share, 1));

    List<Sale> sales = archive.getSales(1);

    assertEquals(1, sales.size());
  }

  @Test
  void countDistinctWeeksCountsCorrectly() {
    archive.add(new Purchase(share, 1));
    archive.add(new Sale(share, 1));
    archive.add(new Purchase(share, 2));

    assertEquals(2, archive.countDistinctWeeks());
  }
}