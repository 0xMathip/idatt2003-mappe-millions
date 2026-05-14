package no.ntnu.group51.view.Dashboard;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import no.ntnu.group51.view.View;

public class DashboardView implements View {

  private final GridPane root =  new GridPane();

  public DashboardView() {

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
