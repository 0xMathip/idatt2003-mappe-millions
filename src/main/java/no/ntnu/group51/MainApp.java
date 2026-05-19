package no.ntnu.group51;

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
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.service.filehandling.csv.CsvStartupFileHandler;
import no.ntnu.group51.view.GameView;

public class MainApp extends Application {

  @Override
  public void start(Stage stage) {
    int width = 1400;
    int height = 800;

    Scene scene = new Scene(new Pane(), width, height);
    scene.getStylesheets().addAll(
        getClass().getResource("/styles/theme.css").toExternalForm(),
        getClass().getResource("/styles/base.css").toExternalForm(),
        getClass().getResource("/styles/layout.css").toExternalForm(),
        getClass().getResource("/styles/navigation.css").toExternalForm(),
        getClass().getResource("/styles/components.css").toExternalForm(),
        getClass().getResource("/styles/market.css").toExternalForm(),
        getClass().getResource("/styles/dashboard.css").toExternalForm(),
        getClass().getResource("/styles/portfolio.css").toExternalForm(),
        getClass().getResource("/styles/transactions.css").toExternalForm(),
        getClass().getResource("/styles/overlays.css").toExternalForm());

    CsvStartupFileHandler csv = new CsvStartupFileHandler();
    List<Stock> stocks = new ArrayList<>();

    Path file = Path.of("src/main/resources/dummy.csv");
    try {
       stocks = csv.readStocks(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }



    GameModel gameModel = new GameModel(new Player("Mathias",new BigDecimal("2000")), new Exchange("NASDAQ", stocks));
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

    Stock apple = gameModel.getExchange().findStocks("AAPL").getFirst();
    Stock tesla = gameModel.getExchange().findStocks("TSLA").getFirst();
    Stock nvidia = gameModel.getExchange().findStocks("NVDA").getFirst();
    Stock microsoft = gameModel.getExchange().findStocks("MSFT").getFirst();
    Stock amazon = gameModel.getExchange().findStocks("AMZN").getFirst();

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(apple,
            new BigDecimal("10"),
            new BigDecimal("198.50")), 10)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Sale(new Share(apple,
            new BigDecimal("4"),
            new BigDecimal("205.00")), 11)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(tesla,
            new BigDecimal("7"),
            new BigDecimal("310.75")), 12)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(nvidia,
            new BigDecimal("15"),
            new BigDecimal("790.20")), 12)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Sale(new Share(tesla,
            new BigDecimal("2"),
            new BigDecimal("344.10")), 13)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(microsoft,
            new BigDecimal("20"),
            new BigDecimal("412.30")), 13)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Sale(new Share(nvidia,
            new BigDecimal("5"),
            new BigDecimal("845.20")), 14)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(amazon,
            new BigDecimal("12"),
            new BigDecimal("182.40")), 14)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Sale(new Share(apple,
            new BigDecimal("3"),
            new BigDecimal("220.15")), 15)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(tesla,
            new BigDecimal("6"),
            new BigDecimal("329.90")), 15)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Sale(new Share(microsoft,
            new BigDecimal("8"),
            new BigDecimal("430.00")), 16)
    );

    gameModel.getPlayer().getTransactionArchive().add(
        new Purchase(new Share(nvidia,
            new BigDecimal("9"),
            new BigDecimal("860.45")), 16)
    );

    SceneManager sceneManager = new SceneManager(scene);
    sceneManager.changeScene(new GameView(gameModel));

    stage.setScene(scene);
    stage.setTitle("MILLION$");
    stage.show();
    stage.setMaximized(true);
  }

  public static void main(String[] args) {
    launch(args);
  }
}