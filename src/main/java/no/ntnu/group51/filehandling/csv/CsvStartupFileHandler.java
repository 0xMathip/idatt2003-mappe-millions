package no.ntnu.group51.filehandling.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.group51.Stocks.Stock;
import no.ntnu.group51.filehandling.StartupFileHandler;

/**
 * CSV-based implementation of {@link StartupFileHandler}.
 *
 * <p>Reads and writes startup data in the format:
 * symbol,company,price.
 */
public class CsvStartupFileHandler implements StartupFileHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Stock> readStocks(Path path) throws IOException {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null.");
    }

    List<Stock> loadedStocks = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      String line;

      while ((line = reader.readLine()) != null) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }
        Stock parsedStock = parseStockLine(line);
        loadedStocks.add(parsedStock);
      }
    }
    return loadedStocks;
  }

  /**
   * Helper method for parsing stocks on a single line in the file.
   *
   * <p>Validates that the data is formatted correctly
   * and creates a Stock object from the data parsed.
   *
   * @param line the line to be parsed
   * @return the parsed stock object
   */
  private Stock parseStockLine(String line) {
    if (line == null) {
      throw new IllegalArgumentException("Line cannot be null.");
    }

    String[] components = line.split(",");

    if (components.length != 3) {
      throw new IllegalArgumentException("Invalid line: " + line);
    }

    String symbol = components[0].trim();
    String company = components[1].trim();

    if (symbol.isEmpty() || company.isEmpty()) {
      throw new IllegalArgumentException("Invalid line: " + line);
    }

    try {
      BigDecimal price = new BigDecimal(components[2].trim());
      return new Stock(symbol, company, price);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid price in line: " + line, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeStocks(Path path, List<Stock> stocks) throws IOException {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null.");
    }
    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }

    try (BufferedWriter writer = Files.newBufferedWriter(path))  {
      for (Stock stock : stocks) {
        if (stock == null) {
          throw new IllegalArgumentException("Stocks list cannot contain null.");
        }

        writer.write(stock.getSymbol()
            + "," + stock.getCompany()
            + "," + stock.getHistoricalPrices().get(0));
        writer.newLine();
      }
    }
  }
}
