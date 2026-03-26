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

public class CsvStartupFileHandler implements StartupFileHandler {

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

  @Override
  public void writeStocks(Path path, List<Stock> stocks) throws IOException {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null.");
    }
    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }

    try (BufferedWriter writer = Files.newBufferedWriter(path))  {
      for (Stock s : stocks) {
        if (s == null) {
          throw new IllegalArgumentException("Stocks list cannot contain null.");
        }

        writer.write(s.getSymbol()
            + "," + s.getCompany()
            + "," + s.getHistoricalPrices().get(0));
        writer.newLine();
      }
    }
  }
}
