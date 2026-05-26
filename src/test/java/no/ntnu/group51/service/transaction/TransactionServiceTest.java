package no.ntnu.group51.service.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
  private TransactionService transactionService;
  private Player player;
  private Stock apple;

  @BeforeEach
  void setup() {
    transactionService = new TransactionService();
    player = new Player("Test", new BigDecimal("10000"));
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
  }

  @Test
  void createTransactionSummariesThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> transactionService.createTransactionSummaries(null));
  }

  @Test
  void createTransactionSummariesReturnsEmptyForNewPlayer() {
    List<TransactionSummary> summaries = transactionService.createTransactionSummaries(player);

    assertTrue(summaries.isEmpty());
  }

  @Test
  void createTransactionSummariesAfterPurchase() {
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    Purchase purchase = new Purchase(share, 1);
    purchase.commit(player);

    player.getTransactionArchive().add(purchase);

    List<TransactionSummary> summaries = transactionService.createTransactionSummaries(player);

    assertEquals(1, summaries.size());
  }

  @Test
  void createPageSummaryThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> transactionService.createPageSummary(null));
  }

  @Test
  void createPageSummaryWithTransactions() {
    Share buyShare = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    Purchase purchase = new Purchase(buyShare, 1);
    purchase.commit(player);

    player.getTransactionArchive().add(purchase);

    player.getPortfolio().addShare(buyShare);
    Share sellShare = new Share(apple, new BigDecimal("5"), new BigDecimal("100"));
    Sale sale = new Sale(sellShare, 1);
    sale.commit(player);

    player.getTransactionArchive().add(sale);

    TransactionPageSummary summary = transactionService.createPageSummary(player);

    assertEquals(2, summary.totalTrades());
    assertEquals(1, summary.totalBought());
    assertEquals(1, summary.totalSold());
  }
}