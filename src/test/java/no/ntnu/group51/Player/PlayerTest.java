package no.ntnu.group51.Player;

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
  }

  @Test
  void addMoneyIncreasesBalance() {
    player.addMoney(new BigDecimal("500"));
    assertEquals(new BigDecimal("1500"), player.getMoney());
  }

  @Test
  void withdrawMoneyDecreasesBalance() {
    player.withdrawMoney(new BigDecimal("200"));
    assertEquals(new BigDecimal("800"), player.getMoney());
  }

  @Test
  void portfolioAndArchiveAreInitialized() {
    assertNotNull(player.getPortfolio());
    assertNotNull(player.getTransactionArchive());
  }
}