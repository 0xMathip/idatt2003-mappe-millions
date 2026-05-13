package no.ntnu.group51.view.components;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import no.ntnu.group51.view.View;

public class StockChartCard implements View {
  private final StackPane root = new StackPane();

  @Override
  public Parent getRoot() {
    return root;
  }
}
