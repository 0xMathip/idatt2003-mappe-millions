package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

public class DashboardView implements View {

  private final GridPane root =  new GridPane();

  public DashboardView() {
    DashboardPortfolioView dashboardPortfolioView = new DashboardPortfolioView();
    DashboardTransactionPanel dashboardTransactionPanel = new DashboardTransactionPanel();
    Label dashboardTitle = new Label("Dashboard");
    dashboardTitle.getStyleClass().add("dashboard-title");
    dashboardTitle.setPadding(new Insets(30, 0, 30, 0));
    VBox leftSide = new VBox();
    leftSide.getChildren().addAll(
        dashboardTitle,
        dashboardPortfolioView.getRoot(),
        dashboardTransactionPanel.getRoot()
    );

    leftSide.setSpacing(20);

    root.setStyle("-fx-background-color: black;");
    root.setPadding(new Insets(0, 0, 0, 46));

    ColumnConstraints left = new ColumnConstraints();
    left.setPercentWidth(35);

    ColumnConstraints right = new ColumnConstraints();
    right.setPercentWidth(65);

    root.getColumnConstraints().addAll(left, right);
    root.add(leftSide, 0, 0, 2, 1);

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
