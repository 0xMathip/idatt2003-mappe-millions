package no.ntnu.group51.view.Dashboard;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import no.ntnu.group51.view.View;

public class DashboardView implements View {

  private final Pane root =  new Pane();

  public DashboardView() {

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
