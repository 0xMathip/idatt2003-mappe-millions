package no.ntnu.group51.Portfolio;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
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
        appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"));
        appleShareTest1 = new Share(appleStockTest, new BigDecimal("120"), new BigDecimal("4.92322"));
        appleShareTest2 = new Share(appleStockTest, new BigDecimal("155"), new BigDecimal("9.42382"));

        googleStockTest = new Stock("GOOG", "Google", new BigDecimal("6.53433"));
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
        assertEquals(3, result.size());
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

        List<Share> result = portfolio.getShares("AAPL");
        assertEquals(2, result.size());
        assertTrue(result.contains(appleShareTest1));
        assertTrue(result.contains(appleShareTest2));
    }

    @Test
    void getShareIsCaseInsensitive() {
        portfolio.addShare(appleShareTest1);
        portfolio.addShare(appleShareTest2);
        portfolio.addShare(googleShareTest);

        List<Share> result = portfolio.getShares("aapl");
        assertEquals(2, result.size());
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
}