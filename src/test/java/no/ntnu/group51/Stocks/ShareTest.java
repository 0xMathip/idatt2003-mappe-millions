package no.ntnu.group51.Stocks;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.group51.Portfolio.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ShareTest {
    private Portfolio portfolio;
    private Share share;
    private Stock appleStockTest;
    private Share appleShareTest1;
    private Share appleShareTest2;
    private Stock googleStockTest;
    private Share googleShareTest;

    @BeforeEach
    void testSetup(){
        appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"));
        appleShareTest1 = new Share(appleStockTest, new BigDecimal("120"), new BigDecimal("4.92322"));
        appleShareTest2 = new Share(appleStockTest, new BigDecimal("155"), new BigDecimal("9.42382"));

        googleStockTest = new Stock("GOOG", "Google", new BigDecimal("6.53433"));
        googleShareTest = new Share(googleStockTest, new BigDecimal("120"), new BigDecimal("7.743323"));
    }


    @Test
    void testShareThrowsWhenStockIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(null, new BigDecimal("120"), new BigDecimal("4.93222")));
    }

    @Test
    void testShareThrowsWhenQuantityIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(appleStockTest, null, new BigDecimal("4.93222")));
    }

    @Test
    void testShareThrowsWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(appleStockTest, new BigDecimal("-5"), new BigDecimal("4.93222")));
    }

    @Test
    void testShareThrowsWhenPurchasePriceIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(appleStockTest, new BigDecimal("120"), null));
    }

    @Test
    void testShareThrowsWhenPurchasePriceIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(appleStockTest, new BigDecimal("120"), new BigDecimal("-6.3242")));
    }

    @Test
    void testGetStockReturnsStock() {
        assertEquals(appleStockTest, appleShareTest1.getStock());
    }

    @Test
    void testGetQuantityReturnsQuantity() {
        assertEquals(new BigDecimal("120"), googleShareTest.getQuantity());
    }

    @Test
    void testGetPurchasePriceReturnsPurchasePrice() {
        assertEquals(new BigDecimal("7.743323"), googleShareTest.getPurchasePrice());
    }
}