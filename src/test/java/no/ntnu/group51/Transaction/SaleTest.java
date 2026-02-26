package no.ntnu.group51.Transaction;

import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
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
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"));
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
  void sellDoesNothingIfPlayerDoesNotOwnShare() {
    BigDecimal beforeMoney = player.getMoney();
    int beforeShares = player.getPortfolio().getShares().size();

    Transaction transaction = new Sale(share, 1);
    transaction.commit(player);

    assertEquals(beforeMoney, player.getMoney());
    assertEquals(beforeShares, player.getPortfolio().getShares().size());
  }
}