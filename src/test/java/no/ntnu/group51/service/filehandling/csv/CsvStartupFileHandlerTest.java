package no.ntnu.group51.service.filehandling.csv;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvStartupFileHandlerTest {
  private CsvStartupFileHandler csv;

  @BeforeEach
  void setUp() {
    csv = new CsvStartupFileHandler();
  }

  @Test
  void readStocksReadsValidFile() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    Files.writeString(tempFile,
        "AAPL,Apple,100,no-icon\n" +
            "GOOG,Google,125,no-icon");

    List<Stock> stocks = csv.readStocks(tempFile);

    assertEquals(2, stocks.size());
    assertEquals("AAPL", stocks.get(0).getSymbol());
    assertEquals(new BigDecimal("100"), stocks.get(0).getSalesPrice());
  }

  @Test
  void readStocksWithRealFile() throws IOException {
    Path file = Path.of("src/main/resources/sp500.csv");

    List<Stock> stocks = csv.readStocks(file);
    assertFalse(stocks.isEmpty());
    assertEquals("NVDA", stocks.get(0).getSymbol());
  }

  @Test
  void readStocksIgnoresEmptyLinesAndComments() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    Files.writeString(tempFile,
        "AAPL,Apple,100,no-icon\n" +
            "#a cool comment\n" +
            "GOOG,Google,125,no-icon\n" +
            "\n");

    List<Stock> stocks = csv.readStocks(tempFile);

    assertEquals(2, stocks.size());
    assertEquals("AAPL", stocks.get(0).getSymbol());
    assertEquals(new BigDecimal("100"), stocks.get(0).getSalesPrice());
    assertEquals("GOOG", stocks.get(1).getSymbol());
    assertEquals(new BigDecimal("125"), stocks.get(1).getSalesPrice());
  }

  @Test
  void readStocksThrowsWhenInvalidFormat() throws IOException{
      Path tempFile = Files.createTempFile("stocks", ".csv");

      Files.writeString(tempFile,
          "Apple,AAPL\n");

      assertThrows(IllegalArgumentException.class,
          () -> csv.readStocks(tempFile));
  }

  @Test
  void readStocksThrowsWhenPathIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> csv.readStocks(null));
  }

  @Test
  void readStocksThrowsWhenPriceIsInvalid() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    Files.writeString(tempFile,
        "AAPL,Apple,NotANumber,no-icon\n" +
            "GOOG,Google,125,no-icon");

    assertThrows(IllegalArgumentException.class,
        () -> csv.readStocks(tempFile));
  }

  @Test
  void readStocksThrowsWhenSymbolIsEmpty() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    Files.writeString(tempFile,
        "AAPL,Apple,100,no-icon\n" +
            ",Google,125,no-icon");

    assertThrows(IllegalArgumentException.class,
        () -> csv.readStocks(tempFile));
  }

  @Test
  void readStocksThrowsWhenCompanyIsEmpty() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    Files.writeString(tempFile,
        "AAPL,Apple,100,no-icon\n" +
            ",Google,125,no-icon");

    assertThrows(IllegalArgumentException.class,
        () -> csv.readStocks(tempFile));
  }

  @Test
  void writeStockWritesFile() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");

    List<Stock> stocks = List.of(
        new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon"),
        new Stock("GOOG", "Google", new BigDecimal("6.53433"), "no-icon"));

    csv.writeStocks(tempFile, stocks);

    String content = Files.readString(tempFile);
    String lineSep = System.lineSeparator();
    String expected = "AAPL,Apple,4.7392781,no-icon" + lineSep
        + "GOOG,Google,6.53433,no-icon" + lineSep;
    assertEquals(expected, content);
  }

  @Test
  void writeStocksThrowsWhenPathIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> csv.writeStocks(null, List.of()));
  }

  @Test
  void writeStocksThrowsWhenStocksIsNull() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");
    assertThrows(IllegalArgumentException.class,
        () -> csv.writeStocks(tempFile, null));
  }

  @Test
  void writeStocksThrowsWhenStocksListContainsNull() throws IOException {
    Path tempFile = Files.createTempFile("stocks", ".csv");
    List<Stock> stocks = new ArrayList<>();

    stocks.add(new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon"));
    stocks.add(null);

    assertThrows(IllegalArgumentException.class,
        () -> csv.writeStocks(tempFile, stocks));
  }
}