package no.ntnu.group51.controller.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.view.pages.DashboardView;

/**
 * Controller for everything related to the dashboard view.
 */
public class DashboardController {

  private final GameModel model;
  private final DashboardView view;

  /**
   * Creates a dashboard controller and also refreshes everything in the dashboard.
   *
   * @param model The persistent model for the game.
   * @param view  The dashboard view.
   */
  public DashboardController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    refresh();
  }

  /**
   * Groups all refresh methods.
   */
  public void refresh() {
    refreshCashPanel();
    refreshPortfolioPanel();
    refreshMovers();
    updateTransactionListings();
    refreshDiffOverWeeks();
    view.updatePortfolioChart(model.getNetWorthHistory());
  }

  /**
   * Refreshes the cash panel.
   */
  public void refreshCashPanel() {
    view.addCashPanel(model);
  }

  /**
   * Refreshes the movers panel.
   */
  public void refreshMovers() {
    view.addMovers(model.getExchange());
  }

  /**
   * Refreshes the transactions listings panel.
   */
  public void updateTransactionListings() {
    view.createTransactionListings(
        model.getPlayer().getTransactionArchive().getLast3Transactions());
  }

  /**
   * Refreshes the portfolio panel by calculating the current portfolio values
   * and updating the dashboard view.
   */
  public void refreshPortfolioPanel() {
    Portfolio portfolio = model.getPlayer().getPortfolio();

    BigDecimal totalInvested = portfolio.getShares()
        .stream()
        .map(share -> share.getPurchasePrice().multiply(share.getQuantity()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalReturn = model.getPlayer()
        .getNetWorth()
        .subtract(model.getPlayer().getStartingMoney());

    BigDecimal returnPercent = model.getPlayer().getStartingMoney().compareTo(BigDecimal.ZERO) == 0
        ? BigDecimal.ZERO
        : totalReturn
        .divide(model.getPlayer().getStartingMoney(), 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));

    view.updatePortfolioPanel(
        portfolio.getShares(),
        totalInvested,
        returnPercent
    );
  }

  /**
   * Refreshes the difference over weeks section.
   */
  public void refreshDiffOverWeeks() {
    view.updateDiffOverWeeks(
        calculateChangePercent(1),
        calculateChangePercent(4),
        calculateAllTimeChangePercent()
    );
  }

  /**
   * Calculates the percentage change from a previous week to the current week.
   *
   * @param weeksBack The amount of weeks to compare against.
   * @return The percentage change between the current value and the previous value.
   */
  private BigDecimal calculateChangePercent(int weeksBack) {
    List<BigDecimal> history = model.getNetWorthHistory();

    if (history.size() <= weeksBack) {
      return BigDecimal.ONE;
    }

    BigDecimal current = history.getLast();
    BigDecimal previous = history.get(history.size() - 1 - weeksBack);

    if (previous.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ONE;
    }

    return current
        .divide(previous, 4, RoundingMode.HALF_UP);
  }

  /**
   * Calculates the all-time percentage change from the starting net worth.
   *
   * @return The percentage change from the starting net worth until now.
   */
  private BigDecimal calculateAllTimeChangePercent() {
    List<BigDecimal> history = model.getNetWorthHistory();

    if (history.size() < 2) {
      return BigDecimal.ONE;
    }

    BigDecimal current = history.getLast();
    BigDecimal start = history.getFirst();

    if (start.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ONE;
    }

    return current
        .divide(start, 4, RoundingMode.HALF_UP)
        .stripTrailingZeros();
  }

  /**
   * Runs a runnable on pressing the view market button in the movers panel.
   *
   * @param runnable The runnable that should run on press
   */
  public void setOnMarketPress(Runnable runnable) {
    view.setOnMarketPress(e -> runnable.run());
  }

  /**
   * Runs a runnable on pressing the view all transactions in the transaction panel.
   *
   * @param runnable The runnable that should run on press
   */
  public void setOnViewAllPress(Runnable runnable) {
    view.setOnTransactionPress(e -> runnable.run());
  }
}
