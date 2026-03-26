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
    private Stock tesla;
    private Stock microsoft;

    @BeforeEach
    void testSetup() {
        apple = new Stock("AAPL", "Apple", new BigDecimal("100"));
        google = new Stock("GOOG", "Google", new BigDecimal("200"));
        tesla = new Stock("TSLA", "Tesla", new BigDecimal("300"));
        microsoft = new Stock("MSFT", "Microsoft", new BigDecimal("120"));
        exchange = new Exchange("Oslo Børs", List.of(apple, google, tesla, microsoft));
    }

    @Test
    void constructorThrowsWhenNameOrStocksIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Exchange(null, List.of(apple)));
        assertThrows(IllegalArgumentException.class, () -> new Exchange("X", null));
    }

    @Test
    void hasStockWorks() {
        assertTrue(exchange.hasStock("AAPL"));
        assertTrue(exchange.hasStock("aapl"));
        assertFalse(exchange.hasStock("EQNR"));
    }

    @Test
    void hasStockThrowsWhenSymbolIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> exchange.hasStock(null));
    }

    @Test
    void getStockThrowsWhenStockNotFound() {
        assertThrows(IllegalArgumentException.class, () -> exchange.getStock("EQNR"));
    }

    @Test
    void getStockThrowsWhenSymbolIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> exchange.getStock(null));
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
    void findStocksThrowsWhenSearchTermIsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> exchange.findStocks(null));
    }

    @Test
    void buyThrowsWhenNullInputOrZeroQuantity() {
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
    void sellThrowsWhenShareOrPlayerIsNull() {
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

        exchange.buy("AAPL", BigDecimal.ONE, player);
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

    @Test
    void getGainersReturnsTopPerformingStocks() {
        setupPriceChanges();

        List<Stock> gainers = exchange.getGainers(2);

        assertEquals(2, gainers.size());
        assertEquals("AAPL", gainers.get(0).getSymbol());
        assertEquals("TSLA", gainers.get(1).getSymbol());
    }

    @Test
    void getGainersThrowsWhenLimitIsNegative() {
        assertThrows(IllegalArgumentException.class,
            () -> exchange.getGainers(-5));
    }

    @Test
    void getGainersReturnsAllStocksWhenLimitExceedsSize() {
        setupPriceChanges();

        List<Stock> gainers = exchange.getGainers(15);

        assertEquals(4, gainers.size());
        assertEquals("AAPL", gainers.get(0).getSymbol());
        assertEquals("TSLA", gainers.get(1).getSymbol());
        assertEquals("GOOG", gainers.get(2).getSymbol());
        assertEquals("MSFT", gainers.get(3).getSymbol());
    }

    @Test
    void getLosersReturnsWorstPerformingStocks() {
        setupPriceChanges();

        List<Stock> losers = exchange.getLosers(2);

        assertEquals(2, losers.size());
        assertEquals("MSFT", losers.get(0).getSymbol());
        assertEquals("GOOG", losers.get(1).getSymbol());
    }

    @Test
    void getLosersThrowsWhenLimitIsNegative() {
        assertThrows(IllegalArgumentException.class,
            () -> exchange.getLosers(-5));
    }

    @Test
    void getLosersReturnsAllStocksWhenLimitExceedsSize() {
        setupPriceChanges();

        List<Stock> losers = exchange.getLosers(12);

        assertEquals(4, losers.size());
        assertEquals("MSFT", losers.get(0).getSymbol());
        assertEquals("GOOG", losers.get(1).getSymbol());
        assertEquals("TSLA", losers.get(2).getSymbol());
        assertEquals("AAPL", losers.get(3).getSymbol());
    }

    private void setupPriceChanges() {
        apple.addNewSalesPrice("120");
        google.addNewSalesPrice("180");
        tesla.addNewSalesPrice("310");
        microsoft.addNewSalesPrice("60");
    }
}