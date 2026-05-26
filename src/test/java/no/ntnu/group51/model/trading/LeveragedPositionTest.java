package no.ntnu.group51.model.trading;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeveragedPositionTest {
  private Stock apple;
  private Share appleShare;

  @BeforeEach
  void setup() {
    apple = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    appleShare = new Share(apple, new BigDecimal("100"), new BigDecimal("150"));
  }

  @Test
  void constructorThrowsWhenShareNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeveragedPosition(
            null, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("100")
        )
    );
  }

  @Test
  void constructorThrowsWhenLeverageNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeveragedPosition(
            appleShare, null, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("100")
        )
    );
  }

  @Test
  void constructorThrowsWhenMarginNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeveragedPosition(
            appleShare, Leverage.X5, new BigDecimal("-100"), new BigDecimal("5000"), new BigDecimal("100")
        )
    );
  }

  @Test
  void constructorThrowsWhenExposureNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeveragedPosition(
            appleShare, Leverage.X5, new BigDecimal("1000"), new BigDecimal("-5000"), new BigDecimal("100")
        )
    );
  }

  @Test
  void constructorThrowsWhenLiquidationPriceNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeveragedPosition(
            appleShare, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("-100")
        )
    );
  }

  @Test
  void gettersReturnCorrectValues() {
    LeveragedPosition position = new LeveragedPosition(
        appleShare, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("120")
    );

    assertEquals(appleShare, position.getShare());
    assertEquals(Leverage.X5, position.getLeverage());
    assertEquals(new BigDecimal("1000"), position.getMarginRequired());
    assertEquals(new BigDecimal("5000"), position.getExposure());
    assertEquals(new BigDecimal("120"), position.getLiquidationPrice());
  }

  @Test
  void isLeveragedReturnsTrueWhenNotOff() {
    LeveragedPosition position = new LeveragedPosition(
        appleShare, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("120")
    );

    assertTrue(position.isLeveraged());
  }

  @Test
  void isLeveragedReturnsFalseWhenOff() {
    LeveragedPosition position = new LeveragedPosition(
        appleShare, Leverage.OFF, new BigDecimal("15000"), new BigDecimal("15000"), new BigDecimal("120")
    );

    assertFalse(position.isLeveraged());
  }

  @Test
  void differentLeverageLevels() {
    LeveragedPosition x5 = new LeveragedPosition(
        appleShare, Leverage.X5, new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("100")
    );
    LeveragedPosition x10 = new LeveragedPosition(
        appleShare, Leverage.X10, new BigDecimal("500"), new BigDecimal("5000"), new BigDecimal("50")
    );
    LeveragedPosition x20 = new LeveragedPosition(
        appleShare, Leverage.X20, new BigDecimal("250"), new BigDecimal("5000"), new BigDecimal("25")
    );

    assertEquals(5, x5.getLeverage().getMultiplier());
    assertEquals(10, x10.getLeverage().getMultiplier());
    assertEquals(20, x20.getLeverage().getMultiplier());
  }
}