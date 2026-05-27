package no.ntnu.group51.view.pages;

import java.math.BigDecimal;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.dashboard.ActionsPanel;
import no.ntnu.group51.view.components.dashboard.CashPanel;
import no.ntnu.group51.view.components.dashboard.DashboardCashStatsSection;
import no.ntnu.group51.view.components.dashboard.DashboardPortfolioPanel;
import no.ntnu.group51.view.components.dashboard.DashboardTopMoversPanel;
import no.ntnu.group51.view.components.dashboard.DashboardTransactionPanel;
import no.ntnu.group51.view.components.dashboard.DiffOverWeeks;
import no.ntnu.group51.view.components.dashboard.TopMovers;
import no.ntnu.group51.view.components.dashboard.TransactionListing;
import no.ntnu.group51.view.components.shared.PortfolioChartCard;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Class for the dashboard itself. Does not include the sidebar.
 */
public class DashboardView implements View {

  private final GridPane root = new GridPane();
  private final DashboardPortfolioPanel dashboardPortfolioPanel = new DashboardPortfolioPanel();
  private final DashboardTransactionPanel dashboardTransactionPanel =
      new DashboardTransactionPanel();
  private final DashboardTopMoversPanel dashboardTopMoversPanel = new DashboardTopMoversPanel();
  private final DashboardCashStatsSection dashboardCashStatsSection =
      new DashboardCashStatsSection();
  private final PortfolioChartCard portfolioChartCard = new PortfolioChartCard(false);
  private final DiffOverWeeks diffOverWeeks = new DiffOverWeeks();
  private final ActionsPanel actionsPanel = new ActionsPanel();

  /**
   * Creates the dashboard view by creating all the panels from the other classes,
   * then putting it into 2 VBoxes. The right side has 2 HBoxes stacked on top of
   * each other in the VBox. GridPane is used to get the percent width easily so it fits nice.
   */
  public DashboardView() {

    Label dashboardTitle = new Label("Dashboard");
    dashboardTitle.getStyleClass().add(StyleClass.PAGE_TITLE);

    VBox leftSide = new VBox();
    leftSide.getChildren().addAll(
        dashboardTitle,
        dashboardPortfolioPanel.getRoot(),
        dashboardTransactionPanel.getRoot()
    );

    leftSide.setSpacing(20);

    portfolioChartCard.addRootStyleClass(StyleClass.DASHBOARD_PORTFOLIO_CHART);

    VBox chartSection = new VBox();
    chartSection.getChildren().addAll(
        diffOverWeeks.getRoot(),
        portfolioChartCard.getRoot()
    );
    chartSection.setSpacing(20);
    chartSection.getStyleClass().addAll(StyleClass.CARD, StyleClass.DASHBOARD_CHART_SECTION);

    HBox rightTop = new HBox();
    rightTop.getChildren().addAll(
        dashboardCashStatsSection.getRoot(),
        chartSection
    );
    rightTop.setSpacing(52);
    rightTop.setPadding(new Insets(25, 0, 0, 0));

    HBox rightBottom = new HBox();
    rightBottom.getChildren().addAll(
        dashboardTopMoversPanel.getRoot(),
        actionsPanel.getRoot()
    );
    rightBottom.setSpacing(50);
    rightBottom.setPadding(new Insets(25, 0, 0, 0));

    VBox rightSide = new VBox();
    rightSide.getChildren().addAll(
        rightTop,
        rightBottom
    );
    rightSide.setSpacing(40);
    rightSide.setAlignment(Pos.BOTTOM_LEFT);

    root.getStyleClass().add(StyleClass.PAGE_LAYOUT);
    root.setPadding(new Insets(20, 0, 0, 46));

    ColumnConstraints left = new ColumnConstraints();
    left.setPercentWidth(35);

    ColumnConstraints right = new ColumnConstraints();
    right.setPercentWidth(65);

    root.getColumnConstraints().addAll(left, right);
    root.add(leftSide, 0, 1);
    root.add(rightSide, 1, 1);

  }

  /**
   * Updates the dashboard transaction list.
   *
   * @param transactions the transactions to display
   * @throws IllegalArgumentException if transactions is null
   */
  public void createTransactionListings(List<Transaction> transactions) {
    if (transactions == null) {
      throw new IllegalArgumentException("Transactions cannot be null.");
    }

    dashboardTransactionPanel.clearListings();

    if (transactions.isEmpty()) {
      Label label = new Label("No transactions yet");
      label.getStyleClass().add(StyleClass.DASHBOARD_EMPTY_TRANSACTION);
      label.setAlignment(Pos.CENTER);
      dashboardTransactionPanel.addToPanel(label);

    } else {
      for (Transaction t : transactions) {
        dashboardTransactionPanel.addToPanel(TransactionListing.createTransactionListing(t));
      }
    }
  }

  /**
   * Updates the dashboard top movers section.
   *
   * @param exchange the exchange used to fetch movers
   * @throws IllegalArgumentException if exchange is null
   */
  public void addMovers(Exchange exchange) {
    if (exchange == null) {
      throw new IllegalArgumentException("Exchange cannot be null.");
    }
    dashboardTopMoversPanel.clearMovers();
    dashboardTopMoversPanel.addToPanel(TopMovers.createMover("gainer", exchange.getGainers(1)));
    dashboardTopMoversPanel.addToPanel(TopMovers.createMover("loser", exchange.getLosers(1)));
  }

  /**
   * Updates the dashboard cash statistics section.
   *
   * @param model the game model containing player values
   * @throws IllegalArgumentException if model is null
   */
  public void addCashPanel(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    dashboardCashStatsSection.clearPanel();
    dashboardCashStatsSection.setPanel(
        CashPanel.createCashPanel("Net Worth", model.getPlayer().getNetWorth()));
    dashboardCashStatsSection.setPanel(
        CashPanel.createCashPanel("Available cash", model.getPlayer().getMoney()));
  }

  /**
   * Sets the action for the market navigation button.
   *
   * @param action the action handler
   * @throws IllegalArgumentException if action is null
   */
  public void setOnMarketPress(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    dashboardTopMoversPanel.setOnViewMarket(action);
  }

  /**
   * Sets the action for the transaction navigation button.
   *
   * @param action the action handler
   * @throws IllegalArgumentException if action is null
   */
  public void setOnTransactionPress(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    dashboardTransactionPanel.setOnViewAll(action);
  }

  /**
   * Sets the action for the advance week button.
   *
   * @param action the action handler
   * @throws IllegalArgumentException if action is null
   */
  public void setOnAdvanceWeekPress(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    actionsPanel.setOnAdvanceWeek(action);
  }

  /**
   * Updates the portfolio net worth chart.
   *
   * @param netWorthHistory the recorded net worth history
   */
  public void updatePortfolioChart(List<BigDecimal> netWorthHistory) {
    if (netWorthHistory == null || netWorthHistory.isEmpty()) {
      portfolioChartCard.clear();
      return;
    }

    portfolioChartCard.updateValues(netWorthHistory);
  }

  /**
   * Updates the dashboard portfolio summary panel.
   *
   * @param shares the owned shares
   * @param totalInvested the total invested amount
   * @param returnPercent the return percentage
   */
  public void updatePortfolioPanel(
      List<Share> shares,
      BigDecimal totalInvested,
      BigDecimal returnPercent
  ) {
    dashboardPortfolioPanel.updatePortfolio(
        shares,
        totalInvested,
        returnPercent
    );
  }

  /**
   * Updates the performance difference summary.
   *
   * @param thisWeek the current week change
   * @param last4Weeks the four-week change
   * @param allTime the all-time change
   */
  public void updateDiffOverWeeks(
      BigDecimal thisWeek,
      BigDecimal last4Weeks,
      BigDecimal allTime
  ) {
    diffOverWeeks.updateDifferences(thisWeek, last4Weeks, allTime);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
