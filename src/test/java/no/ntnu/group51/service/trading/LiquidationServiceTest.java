package no.ntnu.group51.service.trading;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LiquidationServiceTest {
  private LiquidationService liquidationService;
  private LeverageService leverageService;
  private Player player;
  private Stock apple;

  @BeforeEach
  void setup() {
    leverageService = new LeverageService();
    liquidationService = new LiquidationService(leverageService);
    player = new Player("Test", new BigDecimal("10000"));
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
  }

  @Test
  void checkLiquidationsThrowsWhenPlayerNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> liquidationService.checkLiquidations(null, 1)
    );
  }

  @Test
  void checkLiquidationsThrowsWhenWeekNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> liquidationService.checkLiquidations(player, 0)
    );
  }

  @Test
  void checkLiquidationsDoesNothingWhenNoPositions() {
    int sizeBeforeCheck = player.getTransactionArchive().getTransactions().size();

    liquidationService.checkLiquidations(player, 1);

    assertEquals(sizeBeforeCheck, player.getTransactionArchive().getTransactions().size());
  }

  @Test
  void checkLiquidationsRemovesLiquidatedPosition() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));

    LeveragedPosition position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );

    player.getPortfolio().addLeveragedPosition(position);

    apple.addNewSalesPrice("79");

    liquidationService.checkLiquidations(player, 1);

    assertTrue(player.getPortfolio().getLeveragedPositions().isEmpty());
  }

  @Test
  void checkLiquidationsRecordsLiquidationTransaction() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));

    LeveragedPosition position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );

    player.getPortfolio().addLeveragedPosition(position);

    apple.addNewSalesPrice("79");

    liquidationService.checkLiquidations(player, 1);

    assertTrue(player.getTransactionArchive().getTransactions().size() > 0);
  }

  @Test
  void checkLiquidationsKeepsNonLiquidatedPosition() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));

    LeveragedPosition position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );

    player.getPortfolio().addLeveragedPosition(position);

    apple.addNewSalesPrice("120");

    liquidationService.checkLiquidations(player, 1);

    assertEquals(1, player.getPortfolio().getLeveragedPositions().size());
  }
}