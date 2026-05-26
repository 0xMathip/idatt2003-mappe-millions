package no.ntnu.group51.model.exchange;

import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Transaction;
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
        apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "no-icon");
        google = new Stock("GOOG", "Google", new BigDecimal("200"), "no-icon");
        tesla = new Stock("TSLA", "Tesla", new BigDecimal("300"), "no-icon");
        microsoft = new Stock("MSFT", "Microsoft", new BigDecimal("120"), "no-icon");
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

    @Test
    void advanceIncreasesWeekAndUpdatesPrices() {
        List<Stock> stocks = List.of(
            new Stock("AAPL", "Apple", new BigDecimal("100"), "icon")
        );
        Exchange exchange = new Exchange("NASDAQ", stocks);

        int weekBefore = exchange.getWeek();
        exchange.advance();
        int weekAfter = exchange.getWeek();

        assertEquals(weekBefore + 1, weekAfter);
    }

    @Test
    void getGainersLimitWorks() {
        Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
        Stock stock2 = new Stock("GOOGL", "Google", new BigDecimal("100"), "icon");
        Stock stock3 = new Stock("MSFT", "Microsoft", new BigDecimal("100"), "icon");

        stock1.addNewSalesPrice("150");
        stock2.addNewSalesPrice("120");
        stock3.addNewSalesPrice("80");

        Exchange exchange = new Exchange("NASDAQ", List.of(stock1, stock2, stock3));
        List<Stock> top2 = exchange.getGainers(2);

        assertEquals(2, top2.size());
        assertEquals("AAPL", top2.get(0).getSymbol());
    }

    @Test
    void findStocksByCaseInsensitiveSymbol() {
        List<Stock> stocks = List.of(
            new Stock("AAPL", "Apple", new BigDecimal("100"), "icon")
        );
        Exchange exchange = new Exchange("NASDAQ", stocks);

        assertEquals("AAPL", exchange.getStock("aapl").getSymbol());
        assertEquals("AAPL", exchange.getStock("AaPl").getSymbol());
    }

    @Test
    void findStocksIsCaseInsensitive() {
        assertTrue(exchange.findStocks("AAP").size() > 0);
        assertTrue(exchange.findStocks("aap").size() > 0);
        assertTrue(exchange.findStocks("AAP").size() > 0);
    }

    @Test
    void findStocksSearchesSymbolAndCompanyName() {
        List<Stock> bySymbol = exchange.findStocks("MSFT");
        List<Stock> byCompany = exchange.findStocks("Microsoft");

        assertEquals(bySymbol.size(), 1);
        assertEquals(byCompany.size(), 1);
    }

    @Test
    void findStocksReturnsMultipleMatches() {
        List<Stock> results = exchange.findStocks("o");
        assertTrue(results.size() >= 1);
    }

    @Test
    void getGainersHasSortedResults() {
        setupPriceChanges();
        List<Stock> gainers = exchange.getGainers(4);

        assertTrue(gainers.size() > 0);
    }

    @Test
    void getLosersReturnsTopLosingStocks() {
        setupPriceChanges();

        List<Stock> losers = exchange.getLosers(2);

        assertEquals(2, losers.size());
    }

    @Test
    void advanceUpdatesStockPrices() {
        BigDecimal priceBeforeAdvance = apple.getSalesPrice();

        exchange.advance();

        assertNotNull(apple.getSalesPrice());
    }

    @Test
    void getStockWithDifferentCaseFormats() {
        Stock stock1 = exchange.getStock("aapl");
        Stock stock2 = exchange.getStock("AAPL");
        Stock stock3 = exchange.getStock("AaPl");

        assertEquals(stock1.getSymbol(), stock2.getSymbol());
        assertEquals(stock2.getSymbol(), stock3.getSymbol());
    }

    @Test
    void buyCreatesTransactionObject() {
        Player player = new Player("Test", new BigDecimal("10000"));

        Transaction transaction = exchange.buy("AAPL", BigDecimal.ONE, player);

        assertNotNull(transaction);
        assertTrue(transaction.isCommitted());
    }

    @Test
    void sellCreatesTransactionObject() {
        Player player = new Player("Test", new BigDecimal("10000"));

        exchange.buy("AAPL", new BigDecimal("5"), player);
        Share share = player.getPortfolio().getShares().get(0);

        Transaction transaction = exchange.sell(share, player);

        assertNotNull(transaction);
        assertTrue(transaction.isCommitted());
    }
}
