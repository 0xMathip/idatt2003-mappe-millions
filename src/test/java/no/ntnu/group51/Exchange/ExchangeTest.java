package no.ntnu.group51.Exchange;

import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
import no.ntnu.group51.Transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeTest {

    private Exchange exchange;
    private Stock apple;
    private Stock google;

    @BeforeEach
    void testSetup() {
        apple = new Stock("AAPL", "Apple", new BigDecimal("100"));
        google = new Stock("GOOG", "Google", new BigDecimal("200"));
        exchange = new Exchange("Oslo Børs", List.of(apple, google));
    }

    @Test
    void constructorThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> new Exchange(null, List.of(apple)));
        assertThrows(IllegalArgumentException.class, () -> new Exchange("X", null));
    }

    @Test
    void hasStockWorks() {
        assertTrue(exchange.hasStock("AAPL"));
        assertTrue(exchange.hasStock("aapl"));
        assertFalse(exchange.hasStock("TSLA"));
    }

    @Test
    void getStockThrowsWhenNotFound() {
        assertThrows(IllegalArgumentException.class, () -> exchange.getStock("TSLA"));
    }

    @Test
    void getStockReturnsCorrectStock() {
        assertEquals("AAPL", exchange.getStock("aapl").getSymbol());
    }

    @Test
    void findStocksFindsStuff() {
        assertEquals(1, exchange.findStocks("AAP").size());
        assertEquals(1, exchange.findStocks("google").size());
        assertTrue(exchange.findStocks("random").isEmpty());
    }
    @Test
    void buyThrowsWhenNullInput() {
        Player player = new Player("Test", new BigDecimal("1000"));

        assertThrows(IllegalArgumentException.class,
                () -> exchange.buy(null, BigDecimal.ONE, player));

        assertThrows(IllegalArgumentException.class,
                () -> exchange.buy("AAPL", null, player));

        assertThrows(IllegalArgumentException.class,
                () -> exchange.buy("AAPL", BigDecimal.ZERO, player));

        assertThrows(IllegalArgumentException.class,
                () -> exchange.buy("AAPL", BigDecimal.ONE, null));
    }

    @Test
    void buyReturnsTransactionAndAddsShare() {
        Player player = new Player("Test", new BigDecimal("1000"));

        Transaction transaction = exchange.buy("AAPL", new BigDecimal("2"), player);

        assertNotNull(transaction);
        assertEquals(1, player.getPortfolio().getShares().size());
    }

    @Test
    void sellThrowsWhenNull() {
        Player player = new Player("Test", new BigDecimal("1000"));

        assertThrows(IllegalArgumentException.class,
                () -> exchange.sell(null, player));

        Share share = new Share(apple, BigDecimal.ONE, apple.getSalesPrice());

        assertThrows(IllegalArgumentException.class,
                () -> exchange.sell(share, null));
    }

    @Test
    void sellReturnsTransaction() {
        Player player = new Player("Test", new BigDecimal("1000"));

        Transaction buyTx = exchange.buy("AAPL", BigDecimal.ONE, player);
        Share share = player.getPortfolio().getShares().get(0);

        Transaction sellTx = exchange.sell(share, player);

        assertNotNull(sellTx);
    }

    @Test
    void advanceIncrementsWeek() {
        int before = exchange.getWeek();
        exchange.advance();
        assertEquals(before + 1, exchange.getWeek());
    }
}