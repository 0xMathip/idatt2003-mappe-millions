package no.ntnu.group51.view.components;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import no.ntnu.group51.view.View;

public class StockSelectorCard implements View {
  private final HBox root = new HBox();

  @Override
  public Parent getRoot() {
    return root;
  }
}
