package no.ntnu.group51.view.Dashboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

public class ActionsPanel implements View {

  VBox root = new VBox();
  Button advanceButton = new Button("Advance week");

  public ActionsPanel() {

    root.getStyleClass().addAll("dashboard-actions-panel");

    advanceButton.getStyleClass().add("dashboard-actions-advance-week");

    root.getChildren().addAll(advanceButton);
    root.setAlignment(Pos.CENTER);
  }

  public void setOnAdvanceWeek(EventHandler<ActionEvent> action) {
    advanceButton.setOnAction(action);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
