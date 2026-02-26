package no.ntnu.group51.Transaction;

import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Share;
import no.ntnu.group51.Stocks.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

  Stock stock = new Stock("AAPL", "Apple", new BigDecimal("3.55"));
  Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("4.55"));

  @Test
  void testEnoughMoney() {
    Player shorty = new Player("Shorty", new BigDecimal("3.56"));
    Purchase purchase = new Purchase(share, 1);
    purchase.commit(shorty);
    assertTrue(purchase.isCommitted());
  }

  @Test
  void testNotEnoughMoney() {
    Player shorty = new Player("Shorty", new BigDecimal("3.54"));
    Purchase purchase = new Purchase(share, 1);
    purchase.commit(shorty);
    assertFalse(purchase.isCommitted());
  }

  @Test
  void alreadyCommitted() {
    Player shorty = new Player("Shorty", new BigDecimal("300"));
    Purchase purchase = new Purchase(share, 1);
    purchase.commit(shorty);
    assertTrue(purchase.isCommitted());

    Purchase purchase2 = new Purchase(share, 1);
    assertFalse(purchase2.isCommitted());
  }

}