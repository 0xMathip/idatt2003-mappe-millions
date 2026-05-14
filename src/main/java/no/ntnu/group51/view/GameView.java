package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.view.Dashboard.DashboardView;

public class GameView implements View {

  BorderPane root = new BorderPane();

  public GameView() {
    SidebarView sidebar = new SidebarView();
    DashboardView dashboard = new DashboardView();

    root.setLeft(sidebar.getRoot());
    root.setCenter(dashboard.getRoot());
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
