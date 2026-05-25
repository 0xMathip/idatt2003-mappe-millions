package no.ntnu.group51.model.portfolio;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.group51.model.calculator.SaleCalculator;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PortfolioTest {

    private Portfolio portfolio;
    private Stock appleStockTest;
    private Share appleShareTest1;
    private Share appleShareTest2;
    private Stock googleStockTest;
    private Share googleShareTest;

    @BeforeEach
    void testSetup() {
        portfolio = new Portfolio();
        appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
        appleShareTest1 = new Share(appleStockTest, new BigDecimal("120"), new BigDecimal("4.92322"));
        appleShareTest2 = new Share(appleStockTest, new BigDecimal("155"), new BigDecimal("9.42382"));

        googleStockTest = new Stock("GOOG", "Google", new BigDecimal("6.53433"), "no-icon");
        googleShareTest = new Share(googleStockTest, new BigDecimal("120"), new BigDecimal("7.743323"));
    }

    @Test
    void addShareThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> portfolio.addShare(null));
    }

    @Test
    void testAddShare() {
        assertTrue(portfolio.addShare(appleShareTest1));
    }

    @Test
    void removeShareThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> portfolio.removeShare(null));
    }

    @Test
    void testRemoveShare() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(googleShareTest);
        assertTrue(portfolio.removeShare(appleShareTest1));
    }

    @Test
    void getSharesReturnsList() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        portfolio.addShare(googleShareTest);
        List<Share> result = portfolio.getShares();
        assertEquals(2, result.size());
    }

    @Test
    void getSharesReturnsUnmodifiableList() {
        portfolio.addShare(appleShareTest1);
        List<Share> result = portfolio.getShares();
        assertThrows(UnsupportedOperationException.class,
                () -> result.add(googleShareTest));
    }

    @Test
    void getSharesWithSymbolThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> portfolio.getShares(null));
    }

    @Test
    void getSharesReturnsListFromSymbol() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        portfolio.addShare(googleShareTest);

        List<Share> result = portfolio.getShares("GOOG");
        Share share = portfolio.getShares("GOOG").getFirst();
        assertEquals(new BigDecimal("120"), share.getQuantity());
        assertFalse(result.contains(appleShareTest1));
    }

    @Test
    void getShareIsCaseInsensitive() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        portfolio.addShare(googleShareTest);

        List<Share> result = portfolio.getShares("aapl");
        assertEquals(1, result.size());
    }

    @Test
    void containsThrowsWhenNull(){
        assertThrows(IllegalArgumentException.class, () -> portfolio.contains(null));
    }

    @Test
    void testContainsReturnsTrue() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        portfolio.addShare(googleShareTest);
        assertTrue(portfolio.contains(googleShareTest));
    }

    @Test
    void testContainsReturnsFalse() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        assertFalse(portfolio.contains(googleShareTest));
    }

    @Test
    void getNetWorthReturnsNetWorthWithOneShare() {
        portfolio.addShare(googleShareTest);

        BigDecimal expected =
            new SaleCalculator(googleShareTest).calculateTotal();

        assertEquals(expected, portfolio.getPortfolioNetWorth());
    }

    @Test
    void getNetWorthReturnsNetWorthWithMultipleShares() {
        portfolio.addShare(googleShareTest);
        portfolio.addShare(appleShareTest1);

        BigDecimal expected =
            new SaleCalculator(googleShareTest).calculateTotal()
                .add(new SaleCalculator(appleShareTest1).calculateTotal());

        assertEquals(expected, portfolio.getPortfolioNetWorth());
    }

    @Test
    void getNetWorthReturnsZeroWhenNoShares() {
        assertEquals(BigDecimal.ZERO, portfolio.getPortfolioNetWorth());
    }

    @Test
    void testAddShareMergesExistingStock() {
        Portfolio portfolio = new Portfolio();
        Stock apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");

        Share share1 = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));  // 10 @ $150
        Share share2 = new Share(apple, new BigDecimal("5"), new BigDecimal("160"));   // 5 @ $160

        portfolio.addShare(share1);
        portfolio.addShare(share2);

        // Should have merged into 1 share
        assertEquals(1, portfolio.getShares().size());

        Share merged = portfolio.getShares().get(0);
        assertEquals(new BigDecimal("15"), merged.getQuantity());  // 10 + 5
    }

    @Test
    void testAddShareCalculatesAverageCostBasis() {
        Portfolio portfolio = new Portfolio();
        Stock apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");

        Share share1 = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));  // $1500 invested
        Share share2 = new Share(apple, new BigDecimal("5"), new BigDecimal("160"));   // $800 invested

        portfolio.addShare(share1);
        portfolio.addShare(share2);

        Share merged = portfolio.getShares().get(0);
        // Average: (1500 + 800) / 15 = 153.333...
        assertEquals(new BigDecimal("153.33333333"), merged.getPurchasePrice());
    }

    @Test
    void testAddShareKeepsSeparateStocks() {
        Portfolio portfolio = new Portfolio();
        Stock apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
        Stock google = new Stock("GOOGL", "Google", new BigDecimal("100"), "icon");

        Share appleShare = new Share(apple, new BigDecimal("10"), new BigDecimal("150"));
        Share googleShare = new Share(google, new BigDecimal("5"), new BigDecimal("160"));

        portfolio.addShare(appleShare);
        portfolio.addShare(googleShare);

        assertEquals(2, portfolio.getShares().size());  // Two separate positions
    }
}