package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

  Stock stock = new Stock("AAPL", "Apple", new BigDecimal("3.55"), "no-icon");
  Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("4.55"));

  @Test
  void testEnoughMoney() {
    Player shorty = new Player("Shorty", new BigDecimal("3556"));
    Purchase purchase = new Purchase(share, 1);
    purchase.commit(shorty);
    assertTrue(purchase.isCommitted());
  }

  @Test
  void testNotEnoughMoney() {
    Player shorty = new Player("Shorty", new BigDecimal("3.54"));
    Purchase purchase = new Purchase(share, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> purchase.commit(shorty),
        "Should throw when insufficient funds"
    );

    assertFalse(purchase.isCommitted());
  }

  @Test
  void testAlreadyCommittedThrows() {
    Player rich = new Player("Rich", new BigDecimal("5000"));
    Purchase purchase = new Purchase(share, 1);

    purchase.commit(rich);
    assertTrue(purchase.isCommitted());
    assertThrows(
        IllegalStateException.class,
        () -> purchase.commit(rich)
    );
  }

  @Test
  void testExceptions() {
    assertThrows(IllegalArgumentException.class, () -> new Purchase(null, 3));
    assertThrows(IllegalArgumentException.class, () -> new Purchase(share, 0));
    assertThrows(IllegalArgumentException.class, () -> new Purchase(null, -4));
  }

  @Test
  void testCommitSucceedsWithSufficientFunds() {
    Player player = new Player("Test", new BigDecimal("5000"));
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));

    Purchase purchase = new Purchase(share, 1);
    BigDecimal expectedCost = purchase.getTotal();
    BigDecimal expectedRemaining = new BigDecimal("5000").subtract(expectedCost);

    purchase.commit(player);

    assertTrue(purchase.isCommitted());
    assertEquals(expectedRemaining, player.getMoney());
  }

  @Test
  void testCommitThrowsWithInsufficientFunds() {
    Player player = new Player("Test", new BigDecimal("1000"));
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));  // $1500

    Purchase purchase = new Purchase(share, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> purchase.commit(player),
        "Should throw when insufficient funds"
    );

    assertFalse(purchase.isCommitted());
    assertEquals(new BigDecimal("1000"), player.getMoney());  // Unchanged
  }

  @Test
  void testCommitThrowsWhenAlreadyCommitted() {
    Player player = new Player("Test", new BigDecimal("5000"));
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));

    Purchase purchase = new Purchase(share, 1);
    purchase.commit(player);

    assertThrows(
        IllegalStateException.class,
        () -> purchase.commit(player),
        "Should throw when already committed"
    );
  }

  @Test
  void testCommitThrowsWhenPlayerNull() {
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));

    Purchase purchase = new Purchase(share, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> purchase.commit(null)
    );
  }

  @Test
  void testCommitAddsShareToPortfolio() {
    Player player = new Player("Test", new BigDecimal("5000"));
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));

    Purchase purchase = new Purchase(share, 1);
    purchase.commit(player);

    assertEquals(1, player.getPortfolio().getShares().size());
    assertEquals(apple, player.getPortfolio().getShares().get(0).getStock());
  }

}