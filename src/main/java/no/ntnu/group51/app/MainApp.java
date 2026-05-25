package no.ntnu.group51.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.service.filehandling.csv.CsvStartupFileHandler;

public class MainApp extends Application {

  @Override
  public void start(Stage stage) {
    int width = 1920;
    int height = 1080;

    Scene scene = new Scene(new Pane(), width, height);
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
        getClass().getResource("/styles/menus/search-menu.css").toExternalForm(),
        getClass().getResource("/styles/menus/overlays.css").toExternalForm()
    );

    CsvStartupFileHandler csv = new CsvStartupFileHandler();
    List<Stock> stocks = new ArrayList<>();

    Path file = Path.of("src/main/resources/sp500.csv");
    try {
      stocks = csv.readStocks(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    // testing

    Player player = new Player("Mathias",new BigDecimal("2000"));
    Exchange ex = new Exchange("NASDAQ", stocks);
    GameModel gameModel = new GameModel(player, ex);

    Share share1 = new Share(stocks.getFirst(), new BigDecimal(30), new BigDecimal("1"));
    Purchase p1 = new Purchase(share1, 2);
    Share share2 = new Share(stocks.getLast(), new BigDecimal(14), new BigDecimal("578.42"));
    Sale s = new Sale(share2, 3);
    Share share3 = new Share(stocks.get(2), new BigDecimal(100), new BigDecimal("22.67"));
    Purchase p2 = new Purchase(share3, 6);
    player.getTransactionArchive().add(p1);
    player.getTransactionArchive().add(s);
    player.getTransactionArchive().add(p2);


    gameModel.setSelectedStock(gameModel.getExchange().getStock("AAPL"));
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    gameModel.getExchange().advance();
    // end of testing
    SceneManager sceneManager = new SceneManager(scene);
    ApplicationStarter.initialize(gameModel, sceneManager);

    stage.setScene(scene);
    stage.setTitle("MILLION$");
    stage.show();
    stage.setMaximized(true);
  }

  public static void main(String[] args) {
    launch(args);
  }
}