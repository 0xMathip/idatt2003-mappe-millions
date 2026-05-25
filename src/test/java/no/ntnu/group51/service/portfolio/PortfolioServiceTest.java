package no.ntnu.group51.service.portfolio;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortfolioServiceTest {
  private PortfolioService portfolioService;
  private Player player;
  private Stock apple;

  @BeforeEach
  void setup() {
    PositionService positionService = new PositionService();
    portfolioService = new PortfolioService(positionService);

    player = new Player("Test", new BigDecimal("10000"));
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
  }

  @Test
  void createPortfolioSummaryThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> portfolioService.createPortfolioSummary(null));
  }

  @Test
  void createPortfolioSummaryWithEmptyPortfolio() {
    PortfolioSummary summary = portfolioService.createPortfolioSummary(player);

    assertEquals(0, summary.portfolioValue().compareTo(BigDecimal.ZERO));
    assertEquals(0, summary.netWorth().compareTo(new BigDecimal("10000")));
    assertEquals(0, summary.availableCash().compareTo(new BigDecimal("10000")));
    assertEquals(0, summary.totalInvested().compareTo(BigDecimal.ZERO));
  }

  @Test
  void createPortfolioSummaryWithShares() {
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    player.getPortfolio().addShare(share);
    player.withdrawMoney(new BigDecimal("1000"));

    PortfolioSummary summary = portfolioService.createPortfolioSummary(player);

    assertNotEquals(0, summary.portfolioValue().compareTo(BigDecimal.ZERO));
    assertEquals(0, summary.availableCash().compareTo(new BigDecimal("9000")));
  }

  @Test
  void calculatesTotalReturnCorrectly() {
    player.addMoney(new BigDecimal("5000"));

    PortfolioSummary summary = portfolioService.createPortfolioSummary(player);

    assertEquals(0, summary.totalReturn().compareTo(new BigDecimal("5000")));
    assertEquals(0, summary.totalReturnPercent().compareTo(new BigDecimal("50")));
  }

  @Test
  void calculateTotalReturnPercentWithNegativeReturn() {
    player.withdrawMoney(new BigDecimal("5000"));

    PortfolioSummary summary = portfolioService.createPortfolioSummary(player);

    assertEquals(0, summary.totalReturn().compareTo(new BigDecimal("-5000")));
    assertEquals(0, summary.totalReturnPercent().compareTo(new BigDecimal("-50")));
  }
}