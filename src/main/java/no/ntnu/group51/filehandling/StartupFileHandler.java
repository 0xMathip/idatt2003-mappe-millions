package no.ntnu.group51.filehandling;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import no.ntnu.group51.Stocks.Stock;

public interface StartupFileHandler {
  List<Stock> readStocks(Path path) throws IOException;
  void writeStocks(Path path, List<Stock> stocks) throws IOException;
}
