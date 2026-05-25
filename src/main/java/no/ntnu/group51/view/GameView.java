package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.view.components.shared.SidebarView;
import no.ntnu.group51.view.pages.DashboardView;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.PortfolioView;
import no.ntnu.group51.view.pages.TransactionView;

/**
 * Main application view containing sidebar navigation
 * and all primary page views.
 */
public class GameView implements View {

  private final BorderPane root = new BorderPane();

  private final SidebarView sidebar;
  private final DashboardView dashboard;
  private final MarketView market;
  private final TransactionView transaction;
  private final PortfolioView portfolio;

  /**
   * Creates the main game view.
   */
  public GameView() {
    this.sidebar = new SidebarView();
    this.dashboard = new DashboardView();
    this.market = new MarketView();
    this.transaction = new TransactionView();
    this.portfolio = new PortfolioView();

    setLeftView(sidebar);
    setCenterView(dashboard);
  }

  /**
   * Returns the market view.
   *
   * @return the market view
   */
  public MarketView getMarketView() {
    return market;
  }

  /**
   * Returns the transaction view.
   *
   * @return the transaction view
   */
  public TransactionView getTransactionView() {
    return transaction;
  }

  /**
   * Returns the portfolio view.
   *
   * @return the portfolio view
   */
  public PortfolioView getPortfolioView() {
    return portfolio;
  }

  /**
   * Returns the dashboard view.
   *
   * @return the dashboard view
   */
  public DashboardView getDashboardView() {
    return dashboard;
  }

  /**
   * Returns the sidebar view.
   *
   * @return the sidebar view
   */
  public SidebarView getSidebarView() {
    return sidebar;
  }

  /**
   * Sets the center content view.
   *
   * @param view the view to display
   * @throws IllegalArgumentException if view is null
   */
  public void setCenterView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
    root.setCenter(view.getRoot());
  }

  /**
   * Sets the left sidebar view.
   *
   * @param view the view to display
   * @throws IllegalArgumentException if view is null
   */
  public void setLeftView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    }
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
