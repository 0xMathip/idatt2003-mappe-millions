package no.ntnu.group51.Player;

import no.ntnu.group51.Calculator.SaleCalculator;
import no.ntnu.group51.Portfolio.Portfolio;
import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player player;

  @BeforeEach
  void testSetup() {
    player = new Player("Test", new BigDecimal("1000"));
  }

  @Test
  void constructorSetsNameAndMoney() {
    assertEquals("Test", player.getName());
    assertEquals(new BigDecimal("1000"), player.getMoney());
    assertThrows(IllegalArgumentException.class, () -> new Player("Test", null));
    assertEquals("Player", new Player(null, new BigDecimal("1000")).getName());
  }

  @Test
  void addMoneyIncreasesBalance() {
    player.addMoney(new BigDecimal("500"));
    assertEquals(new BigDecimal("1500"), player.getMoney());
    player.addMoney(null);
    assertEquals(new BigDecimal("1500"), player.getMoney());
  }

  @Test
  void withdrawMoneyDecreasesBalance() {
    player.withdrawMoney(new BigDecimal("200"));
    assertEquals(new BigDecimal("800"), player.getMoney());
    assertThrows(IllegalArgumentException.class, () -> player.withdrawMoney(null));
    assertThrows(IllegalArgumentException.class, () -> player.withdrawMoney(new BigDecimal("1001")));
  }

  @Test
  void portfolioAndArchiveAreInitialized() {
    assertNotNull(player.getPortfolio());
    assertNotNull(player.getTransactionArchive());
  }

  @Test
  void getNetWorthReturnsMoneyWhenZeroPortfolioValue() {
    assertEquals(player.getMoney(), player.getNetWorth());
  }

  @Test
  void getNetWorthReturnsMoneyAndPortfolioValue() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"));
    Share appleShareTest = new Share(appleStockTest, new BigDecimal("120"), new BigDecimal("4.92322"));

    player.getPortfolio().addShare(appleShareTest);

    BigDecimal expected = new BigDecimal("1000").add(new SaleCalculator(appleShareTest).calculateTotal());
    assertEquals(expected, player.getNetWorth());
  }

  @Test
  void getNetWorthReturnsZeroWhenPortfolioAndMoneyValueAreZero() {
    player.withdrawMoney(new BigDecimal("1000"));

    assertEquals(BigDecimal.ZERO, player.getNetWorth());
  }
}