package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

public class DashboardView implements View {

  private final GridPane root =  new GridPane();

  public DashboardView() {
    DashboardPortfolioPanel dashboardPortfolioPanel = new DashboardPortfolioPanel();
    DashboardTransactionPanel dashboardTransactionPanel = new DashboardTransactionPanel();

    Label dashboardTitle = new Label("Dashboard");
    dashboardTitle.getStyleClass().add("dashboard-title");
    dashboardTitle.setPadding(new Insets(30, 0, 30, 0));

    VBox leftSide = new VBox();
    leftSide.getChildren().addAll(
        dashboardTitle,
        dashboardPortfolioPanel.getRoot(),
        dashboardTransactionPanel.getRoot()
    );

    leftSide.setSpacing(20);

    DashboardTopMoversPanel dashboardTopMoversPanel = new DashboardTopMoversPanel();
    DashboardCashStatsSection dashboardCashStatsSection = new DashboardCashStatsSection();

    HBox rightBottom = new HBox();
    rightBottom.getChildren().addAll(
        dashboardTopMoversPanel.getRoot()
    );

    HBox rightTop = new HBox();
    rightTop.getChildren().addAll(
        dashboardCashStatsSection.getRoot()
    );


    VBox rightSide = new VBox();
    rightSide.setAlignment(Pos.CENTER);
    rightSide.getChildren().addAll(
        rightTop,
        rightBottom
    );

    root.setStyle("-fx-background-color: black;");
    root.setPadding(new Insets(0, 0, 0, 46));

    ColumnConstraints left = new ColumnConstraints();
    left.setPercentWidth(35);

    ColumnConstraints right = new ColumnConstraints();
    right.setPercentWidth(65);

    root.getColumnConstraints().addAll(left, right);
    root.add(leftSide, 0, 1);
    root.add(rightSide, 1, 1);

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
