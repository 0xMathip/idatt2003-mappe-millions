package no.ntnu.group51.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.service.filehandling.csv.CsvStartupFileHandler;

/**
 * Starts the Millions application.
 */
public class MainApp extends Application {
  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  private static final String DEFAULT_PLAYER_NAME = "Player";
  private static final BigDecimal DEFAULT_STARTING_CAPITAL = new BigDecimal("2000");
  private static final String DEFAULT_EXCHANGE_NAME = "NASDAQ";
  private static final Path DEFAULT_STOCK_FILE = Path.of("src/main/resources/sp500.csv");

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(new Pane(), WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().addAll(
        getClass().getResource("/styles/theme.css").toExternalForm(),
        getClass().getResource("/styles/base.css").toExternalForm(),
        getClass().getResource("/styles/layout.css").toExternalForm(),
        getClass().getResource("/styles/components/badges.css").toExternalForm(),
        getClass().getResource("/styles/components/buttons.css").toExternalForm(),
        getClass().getResource("/styles/components/cards.css").toExternalForm(),
        getClass().getResource("/styles/components/icons.css").toExternalForm(),
        getClass().getResource("/styles/panels/trade-panel.css").toExternalForm(),
        getClass().getResource("/styles/panels/market.css").toExternalForm(),
        getClass().getResource("/styles/panels/portfolio.css").toExternalForm(),
        getClass().getResource("/styles/panels/transactions.css").toExternalForm(),
        getClass().getResource("/styles/panels/dashboard.css").toExternalForm(),
        getClass().getResource("/styles/menus/sidebar.css").toExternalForm(),
        getClass().getResource("/styles/menus/search-menu.css").toExternalForm()
    );

    GameModel gameModel = createDefaultGameModel();
    SceneManager sceneManager = new SceneManager(scene);
    ApplicationStarter.initialize(gameModel, sceneManager);

    stage.setScene(scene);
    stage.setTitle("MILLION$");
    stage.show();
    stage.setMaximized(true);
  }

  private GameModel createDefaultGameModel() {
    List<Stock> stocks = loadDefaultStocks();
    Player player = new Player(DEFAULT_PLAYER_NAME, DEFAULT_STARTING_CAPITAL);
    Exchange exchange = new Exchange(DEFAULT_EXCHANGE_NAME, stocks);
    return new GameModel(player, exchange);
  }

  private List<Stock> loadDefaultStocks() {
    try {
      return new CsvStartupFileHandler().readStocks(DEFAULT_STOCK_FILE);
    } catch (IOException e) {
      throw new IllegalStateException("Could not load default stock data.", e);
    }
  }

  /**
   * Launches the JavaFX application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}