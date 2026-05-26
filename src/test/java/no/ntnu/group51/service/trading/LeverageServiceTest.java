package no.ntnu.group51.service.trading;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeverageServiceTest {
  private LeverageService leverageService;
  private Stock apple;
  private Portfolio portfolio;

  @BeforeEach
  void setup() {
    leverageService = new LeverageService();
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
    portfolio = new Portfolio();
  }

  @Test
  void findLiquidatedPositionsReturnsBelowThreshold() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));

    LeveragedPosition position = new LeveragedPosition(
        share, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("50")
    );
    portfolio.addLeveragedPosition(position);

    List<LeveragedPosition> liquidated = leverageService.findLiquidatedPositions(portfolio);
    assertTrue(liquidated.isEmpty());

    apple.addNewSalesPrice("49");
    liquidated = leverageService.findLiquidatedPositions(portfolio);
    assertEquals(1, liquidated.size());
  }

  @Test
  void getMultiplierForX5() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X5);
    assertEquals(new BigDecimal("5"), multiplier);
  }

  @Test
  void getMultiplierForX10() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X10);
    assertEquals(new BigDecimal("10"), multiplier);
  }

  @Test
  void getMultiplierForX20() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X20);
    assertEquals(new BigDecimal("20"), multiplier);
  }

  @Test
  void getMultiplierForOff() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.OFF);
    assertEquals(BigDecimal.ONE, multiplier);
  }

  @Test
  void calculateExposureFromMarginAndMultiplier() {
    BigDecimal margin = new BigDecimal("1000");
    BigDecimal multiplier = new BigDecimal("5");

    BigDecimal exposure = leverageService.calculateExposure(margin, multiplier);

    assertEquals(new BigDecimal("5000"), exposure);
  }

  @Test
  void calculateExposureForX10() {
    BigDecimal margin = new BigDecimal("1000");
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X10);

    BigDecimal exposure = leverageService.calculateExposure(margin, multiplier);

    assertEquals(new BigDecimal("10000"), exposure);
  }

  @Test
  void calculateLiquidationPrice() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X5);

    BigDecimal liquidationPrice = leverageService.calculateLiquidationPrice(
        apple, multiplier, Leverage.X5
    );

    assertEquals(0, liquidationPrice.compareTo(new BigDecimal("80")));
  }

  @Test
  void calculateLiquidationPriceForX10() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.X10);

    BigDecimal liquidationPrice = leverageService.calculateLiquidationPrice(
        apple, multiplier, Leverage.X10
    );

    assertEquals(0, liquidationPrice.compareTo(new BigDecimal("90")));
  }

  @Test
  void calculateLiquidationPriceForLeverageOff() {
    BigDecimal multiplier = leverageService.getMultiplier(Leverage.OFF);

    BigDecimal liquidationPrice = leverageService.calculateLiquidationPrice(
        apple, multiplier, Leverage.OFF
    );

    assertEquals(BigDecimal.ZERO, liquidationPrice);
  }

  @Test
  void isLiquidatedReturnsTrueWhenBelowThreshold() {
    LeveragedPosition position = new LeveragedPosition(
        new Share(apple, new BigDecimal("100"), new BigDecimal("100")),
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );

    assertFalse(leverageService.isLiquidated(position));

    apple.addNewSalesPrice("79");
    assertTrue(leverageService.isLiquidated(position));
  }

  @Test
  void getMultiplierThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> leverageService.getMultiplier(null));
  }

  @Test
  void calculateExposureThrowsWhenMarginNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> leverageService.calculateExposure(null, BigDecimal.ONE)
    );
  }

  @Test
  void calculateExposureThrowsWhenMultiplierNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> leverageService.calculateExposure(BigDecimal.ONE, null)
    );
  }

  @Test
  void findLiquidatedPositionsThrowsWhenNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> leverageService.findLiquidatedPositions(null)
    );
  }

  @Test
  void isLiquidatedThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> leverageService.isLiquidated(null));
  }

  @Test
  void createSummaryReturnsValidSummary() {
    LeverageSummary summary = leverageService.createSummary(
        apple,
        new BigDecimal("1000"),
        Leverage.X5
    );

    assertNotNull(summary);
    assertEquals(Leverage.X5, summary.leverage());
    assertEquals(new BigDecimal("5"), summary.multiplier());
    assertEquals(new BigDecimal("1000"), summary.marginRequired());
    assertEquals(new BigDecimal("5000"), summary.exposure());
  }
}