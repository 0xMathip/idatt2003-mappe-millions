package no.ntnu.group51.service.trading;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradeServiceTest {
  private TradeService tradeService;
  private LeverageService leverageService;
  private Player player;
  private Stock apple;

  @BeforeEach
  void setup() {
    leverageService = new LeverageService();
    tradeService = new TradeService(leverageService);
    player = new Player("Test", new BigDecimal("10000"));
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
  }

  @Test
  void createPreviewThrowsWhenPlayerNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            null, apple, "100", TradeMode.AMOUNT, TradeType.BUY, Leverage.OFF, 1
        )
    );
  }

  @Test
  void createPreviewThrowsWhenStockNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            player, null, "100", TradeMode.AMOUNT, TradeType.BUY, Leverage.OFF, 1
        )
    );
  }

  @Test
  void createPreviewBuyByAmount() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "100",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.OFF,
        1
    );

    assertNotNull(preview);
    assertEquals(TradeType.BUY, preview.tradeType());
    assertEquals(Leverage.OFF, preview.leverage());
    assertTrue(preview.quantity().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void createPreviewBuyByQuantity() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "10",
        TradeMode.SHARES,
        TradeType.BUY,
        Leverage.OFF,
        1
    );

    assertEquals(0, preview.quantity().compareTo(new BigDecimal("10")));
  }

  @Test
  void createPreviewSellByAmount() {
    Share buyShare = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    player.getPortfolio().addShare(buyShare);

    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "500",
        TradeMode.AMOUNT,
        TradeType.SELL,
        Leverage.OFF,
        1
    );

    assertNotNull(preview);
    assertEquals(TradeType.SELL, preview.tradeType());
  }

  @Test
  void createPreviewWithLeverageX5() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.X5,
        1
    );

    assertEquals(Leverage.X5, preview.leverage());
    assertTrue(preview.marginRequired().compareTo(BigDecimal.ZERO) > 0);
    assertNotNull(preview.leveragedPosition());
  }

  @Test
  void createPreviewWithLeverageX10() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.X10,
        1
    );

    assertEquals(Leverage.X10, preview.leverage());
    assertNotNull(preview.leveragedPosition());
  }

  @Test
  void commitTradeThrowsWhenInsufficientFunds() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.OFF,
        1
    );

    player.withdrawMoney(player.getMoney());

    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.commitTrade(player, preview)
    );
  }

  @Test
  void createPreviewThrowsWhenInvalidInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            player,
            apple,
            "invalid",
            TradeMode.AMOUNT,
            TradeType.BUY,
            Leverage.OFF,
            1
        )
    );
  }

  @Test
  void commitTradeBuyRegular() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.OFF,
        1
    );

    BigDecimal moneyBefore = player.getMoney();

    tradeService.commitTrade(player, preview);

    assertTrue(player.getMoney().compareTo(moneyBefore) < 0);
    assertTrue(player.getPortfolio().getShares().size() > 0);
  }

  @Test
  void commitTradeSellRegular() {
    Share buyShare = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    Purchase purchase = new Purchase(buyShare, 1);
    purchase.commit(player);
    player.getPortfolio().addShare(buyShare);

    BigDecimal moneyBefore = player.getMoney();

    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "500",
        TradeMode.AMOUNT,
        TradeType.SELL,
        Leverage.OFF,
        1
    );

    tradeService.commitTrade(player, preview);

    assertTrue(player.getMoney().compareTo(moneyBefore) > 0);
  }

  @Test
  void commitTradeBuyLeveraged() {
    TradePreview preview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.X5,
        1
    );

    tradeService.commitTrade(player, preview);

    assertTrue(player.getPortfolio().getLeveragedPositions().size() > 0);
  }

  @Test
  void commitTradeThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> tradeService.commitTrade(null, null));
  }

  @Test
  void createPreviewThrowsWhenInputIsNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            player,
            apple,
            "-100",
            TradeMode.AMOUNT,
            TradeType.BUY,
            Leverage.OFF,
            1
        )
    );
  }

  @Test
  void createPreviewThrowsWhenInputIsZero() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            player,
            apple,
            "0",
            TradeMode.AMOUNT,
            TradeType.BUY,
            Leverage.OFF,
            1
        )
    );
  }

  @Test
  void createPreviewThrowsWhenWeekIsNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.createPreview(
            player,
            apple,
            "100",
            TradeMode.AMOUNT,
            TradeType.BUY,
            Leverage.OFF,
            0
        )
    );
  }

  @Test
  void commitTradeThrowsWhenPreviewIsNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> tradeService.commitTrade(player, null)
    );
  }

  @Test
  void commitTradeSellLeveraged() {
    TradePreview buyPreview = tradeService.createPreview(
        player,
        apple,
        "1000",
        TradeMode.AMOUNT,
        TradeType.BUY,
        Leverage.X5,
        1
    );

    tradeService.commitTrade(player, buyPreview);

    BigDecimal moneyBefore = player.getMoney();

    TradePreview sellPreview = tradeService.createPreview(
        player,
        apple,
        "500",
        TradeMode.AMOUNT,
        TradeType.SELL,
        Leverage.X5,
        2
    );

    tradeService.commitTrade(player, sellPreview);

    assertTrue(player.getMoney().compareTo(moneyBefore) > 0);
  }
}