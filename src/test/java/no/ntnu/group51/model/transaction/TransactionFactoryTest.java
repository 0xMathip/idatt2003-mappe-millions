package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

  @Test
  void testCreateTransaction() {
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("100"));
    Share share = new Share(apple, new BigDecimal("2"), new BigDecimal("90"));
    var gronk = TransactionFactory.createTransaction("buy", share, 2);
    var baby =  TransactionFactory.createTransaction("sEll", share, 2);
    assertInstanceOf(Purchase.class, gronk);
    assertInstanceOf(Sale.class, baby);
  }

}