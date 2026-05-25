package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

  private Player player;
  private Stock apple;
  private Share share;

  @BeforeEach
  void setup() {
    player = new Player("Test", new BigDecimal("1000"));
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "no-icon");
    share = new Share(apple, new BigDecimal("2"), new BigDecimal("90"));
  }

  @Test
  void sellRemovesShareFromPortfolio() {
    player.getPortfolio().addShare(share);

    Transaction transaction = new Sale(share, 1);
    transaction.commit(player);

    assertFalse(player.getPortfolio().contains(share));
  }

  @Test
  void sellAddsMoney() {
    player.getPortfolio().addShare(share);
    BigDecimal before = player.getMoney();

    Transaction transaction = new Sale(share, 1);
    transaction.commit(player);

    assertTrue(player.getMoney().compareTo(before) > 0);
  }

  @Test
  void sellThrowsWhenPlayerDoesNotOwnShare() {
    Player shorty = new Player("Shorty", new BigDecimal("100"));
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Share share = new Share(apple, new BigDecimal("2"), new BigDecimal("100"));

    Sale sale = new Sale(share, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> sale.commit(shorty),
        "Should throw when player doesn't own shares"
    );
    assertFalse(sale.isCommitted());
  }

  @Test
  void testExceptions() {
    assertThrows(IllegalArgumentException.class, () -> new Sale(null, 3));
    assertThrows(IllegalArgumentException.class, () -> new Sale(share, 0));
    assertThrows(IllegalArgumentException.class, () -> new Sale(null, -4));
  }

  @Test
  void testSaleWithoutOwnershipThrows() {
    Player player = new Player("Player", new BigDecimal("1000"));
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
    Share share = new Share(stock, new BigDecimal("10"), new BigDecimal("50"));

    Sale sale = new Sale(share, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> sale.commit(player),
        "Should throw when player doesn't own shares"
    );
  }

  @Test
  void testSaleSucceedsWithOwnership() {
    Player player = new Player("Player", new BigDecimal("1000"));
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
    Share ownedShare = new Share(stock, new BigDecimal("10"), new BigDecimal("50"));

    player.getPortfolio().addShare(ownedShare);

    Sale sale = new Sale(ownedShare, 1);
    sale.commit(player);

    assertTrue(sale.isCommitted());
    assertTrue(player.getMoney().compareTo(new BigDecimal("1000")) > 0);
  }
}