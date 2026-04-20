package no.ntnu.group51.filehandling;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import no.ntnu.group51.Stocks.Stock;

/**
 * Defines functionality for reading and writing startup stock data.
 *
 * <p>The implementations are responsible for handling a specific file format,
 * such as CSV, JSON and for converting between file data and {@link Stock} objects.
 */
public interface StartupFileHandler {
  /**
   * Reads the startup stock data from a file.
   *
   * <p>The file must contain lines on the specified format:
   * symbol,company,price.
   * Blank lines and lines starting with '#' are ignored.
   *
   * @param path the path to the file
   * @return a list of stocks parsed from the file provided
   * @throws IOException if an I/O error occurs
   * @throws IllegalArgumentException if the path is null or contains invalid data
   */
  List<Stock> readStocks(Path path) throws IOException;

  /**
   * Writes the startup stock data to a file.
   *
   * <p>The output format is:
   * symbol,company,price.
   *
   * @param path the path to the file
   * @param stocks a list of stocks to write to file
   * @throws IOException if an I/O error occurs
   * @throws IllegalArgumentException if path or stocks is null,
   *        or if the list contains null elements
   */
  void writeStocks(Path path, List<Stock> stocks) throws IOException;
}
