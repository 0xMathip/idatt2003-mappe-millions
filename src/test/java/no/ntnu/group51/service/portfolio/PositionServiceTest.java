package no.ntnu.group51.service.portfolio;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionServiceTest {
  private PositionService positionService;
  private Portfolio portfolio;
  private Stock apple;
  private Stock google;

  @BeforeEach
  void setup() {
    positionService = new PositionService();
    portfolio = new Portfolio();
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
    google = new Stock("GOOG", "Google", new BigDecimal("200"), "icon");
  }

  @Test
  void createPositionSummariesThrowsWhenPortfolioNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> positionService.createPositionSummaries(null)
    );
  }

  @Test
  void createPositionSummariesReturnsEmptyWhenNoPositions() {
    List<PositionSummary> summaries = positionService.createPositionSummaries(portfolio);

    assertTrue(summaries.isEmpty());
  }

  @Test
  void createPositionSummariesIncludesRegularShares() {
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    portfolio.addShare(share);

    List<PositionSummary> summaries = positionService.createPositionSummaries(portfolio);

    assertEquals(1, summaries.size());
    assertEquals(apple, summaries.get(0).stock());
    assertEquals(0, summaries.get(0).sharesOwned().compareTo(new BigDecimal("10")));
  }

  @Test
  void createPositionSummariesIncludesLeveragedPositions() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));
    LeveragedPosition position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );
    portfolio.addLeveragedPosition(position);

    List<PositionSummary> summaries = positionService.createPositionSummaries(portfolio);

    assertEquals(1, summaries.size());
    assertTrue(summaries.get(0).leveraged());
    assertEquals(Leverage.X5, summaries.get(0).leverage());
  }

  @Test
  void createPositionSummariesIncludesMixedPositions() {
    Share regularShare = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));
    portfolio.addShare(regularShare);

    Share leveragedShare = new Share(google, new BigDecimal("50"), new BigDecimal("200"));
    LeveragedPosition leveragedPosition = new LeveragedPosition(
        leveragedShare,
        Leverage.X10,
        new BigDecimal("500"),
        new BigDecimal("5000"),
        new BigDecimal("150")
    );
    portfolio.addLeveragedPosition(leveragedPosition);

    List<PositionSummary> summaries = positionService.createPositionSummaries(portfolio);

    assertEquals(2, summaries.size());
  }

  @Test
  void createPositionSummaryThrowsWhenStockNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> positionService.createPositionSummary(null, List.of())
    );
  }

  @Test
  void createPositionSummaryThrowsWhenSharesNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> positionService.createPositionSummary(apple, null)
    );
  }

  @Test
  void createPositionSummaryThrowsWhenSharesEmpty() {
    assertThrows(
        IllegalArgumentException.class,
        () -> positionService.createPositionSummary(apple, List.of())
    );
  }

  @Test
  void createPositionSummaryCalculatesCorrectProfit() {
    apple.addNewSalesPrice("110");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));

    PositionSummary summary = positionService.createPositionSummary(apple, List.of(share));

    assertEquals(0, summary.profitLoss().compareTo(new BigDecimal("100.00")));
    assertTrue(summary.roiPercent().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void createPositionSummaryCalculatesCorrectLoss() {
    apple.addNewSalesPrice("90");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));

    PositionSummary summary = positionService.createPositionSummary(apple, List.of(share));

    assertEquals(0, summary.profitLoss().compareTo(new BigDecimal("-100.00")));
    assertTrue(summary.roiPercent().compareTo(BigDecimal.ZERO) < 0);
  }

  @Test
  void createPositionSummaryMergesMultipleSharesOfSameStock() {
    Share share1 = new Share(apple, new BigDecimal("5"), new BigDecimal("100"));
    Share share2 = new Share(apple, new BigDecimal("5"), new BigDecimal("110"));

    PositionSummary summary = positionService.createPositionSummary(
        apple,
        List.of(share1, share2)
    );

    assertEquals(0, summary.sharesOwned().compareTo(new BigDecimal("10.00000000")));
    assertEquals(0, summary.averageBuyPrice().compareTo(new BigDecimal("105.00")));
  }

  @Test
  void createPositionSummaryIncludesHighLowPrices() {
    apple.addNewSalesPrice("110");
    apple.addNewSalesPrice("95");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));

    PositionSummary summary = positionService.createPositionSummary(apple, List.of(share));

    assertTrue(summary.lowestPrice().compareTo(BigDecimal.ZERO) > 0);
    assertTrue(summary.highestPrice().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void createLeveragedPositionSummaryThrowsWhenPositionNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> positionService.createPositionSummary(null, null)
    );
  }

  @Test
  void createLeveragedPositionSummaryIncludesLeverageInfo() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));
    LeveragedPosition position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );

    portfolio.addLeveragedPosition(position);
    List<PositionSummary> summaries = positionService.createPositionSummaries(portfolio);

    assertTrue(summaries.get(0).leveraged());
    assertEquals(Leverage.X5, summaries.get(0).leverage());
    assertEquals(0, summaries.get(0).marginRequired().compareTo(new BigDecimal("1000")));
  }
}
