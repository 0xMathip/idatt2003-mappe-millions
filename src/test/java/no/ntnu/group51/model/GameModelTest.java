package no.ntnu.group51.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameModelTest {
  private GameModel gameModel;
  private Player player;
  private Exchange exchange;
  private List<Stock> stocks;

  @BeforeEach
  void setup() {
    player = new Player("Test", new BigDecimal("5000"));
    stocks = List.of(
        new Stock("AAPL", "Apple", new BigDecimal("150"), "icon"),
        new Stock("GOOGL", "Google", new BigDecimal("2800"), "icon")
    );
    exchange = new Exchange("NASDAQ", stocks);
    gameModel = new GameModel(player, exchange);
  }

  @Test
  void constructorThrowsWhenPlayerNull() {
    assertThrows(IllegalArgumentException.class, () -> new GameModel(null, exchange));
  }

  @Test
  void constructorThrowsWhenExchangeNull() {
    assertThrows(IllegalArgumentException.class, () -> new GameModel(player, null));
  }

  @Test
  void getPlayerReturnsCorrectPlayer() {
    assertEquals(player, gameModel.getPlayer());
  }

  @Test
  void getExchangeReturnsCorrectExchange() {
    assertEquals(exchange, gameModel.getExchange());
  }

  @Test
  void selectedStockIsInitiallyNull() {
    assertNull(gameModel.getSelectedStock());
  }

  @Test
  void setSelectedStockUpdatesStock() {
    Stock apple = stocks.get(0);
    gameModel.setSelectedStock(apple);
    assertEquals(apple, gameModel.getSelectedStock());
  }

  @Test
  void setSelectedStockNotifiesObservers() {
    Observer observer = new TestObserver();
    gameModel.addObserver(observer);

    gameModel.setSelectedStock(stocks.get(0));

    assertTrue(((TestObserver) observer).wasNotified());
  }

  @Test
  void recordNetWorthAddsToHistory() {
    List<BigDecimal> before = gameModel.getNetWorthHistory();
    int sizeBefore = before.size();

    gameModel.recordNetWorth();

    List<BigDecimal> after = gameModel.getNetWorthHistory();
    assertEquals(sizeBefore + 1, after.size());
  }

  @Test
  void netWorthHistoryStartsWithInitialNetWorth() {
    List<BigDecimal> history = gameModel.getNetWorthHistory();

    assertEquals(1, history.size());
    assertEquals(player.getNetWorth(), history.get(0));
  }

  @Test
  void addObserverThrowsWhenNull() {
    assertThrows(IllegalArgumentException.class, () -> gameModel.addObserver(null));
  }

  @Test
  void multipleObserversAllNotified() {
    Observer observer1 = new TestObserver();
    Observer observer2 = new TestObserver();

    gameModel.addObserver(observer1);
    gameModel.addObserver(observer2);
    gameModel.setSelectedStock(stocks.get(0));

    assertTrue(((TestObserver) observer1).wasNotified());
    assertTrue(((TestObserver) observer2).wasNotified());
  }

  static class TestObserver implements Observer {
    private boolean notified = false;

    @Override
    public void update() {
      notified = true;
    }

    public boolean wasNotified() {
      return notified;
    }
  }
}