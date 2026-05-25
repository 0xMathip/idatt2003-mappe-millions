package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.view.components.shared.SidebarView;
import no.ntnu.group51.view.pages.DashboardView;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.PortfolioView;
import no.ntnu.group51.view.pages.TransactionView;

public class GameView implements View {

  private final BorderPane root = new BorderPane();

  private final SidebarView sidebar;
  private final DashboardView dashboard;
  private final MarketView market;
  private final TransactionView transaction;
  private final PortfolioView portfolio;

  public GameView() {
    this.sidebar = new SidebarView();
    this.dashboard = new DashboardView();
    this.market = new MarketView();
    this.transaction = new TransactionView();
    this.portfolio = new PortfolioView();

    setLeftView(sidebar);
    setCenterView(dashboard);
  }

  public MarketView getMarketView() {
    return market;
  }

  public TransactionView getTransactionView() {
    return transaction;
  }

  public PortfolioView getPortfolioView() {
    return portfolio;
  }

  public DashboardView getDashboardView() {
    return dashboard;
  }

  public SidebarView getSidebarView() {
    return sidebar;
  }

  public void setCenterView(View view) {
    root.setCenter(view.getRoot());
  }

  public void setLeftView(View view) {
    root.setLeft(view.getRoot());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
