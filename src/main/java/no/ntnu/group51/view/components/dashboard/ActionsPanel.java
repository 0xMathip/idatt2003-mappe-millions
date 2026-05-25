package no.ntnu.group51.view.components.dashboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

public class ActionsPanel implements View {

  VBox root = new VBox();
  Button advanceButton = new Button("Advance week");

  public ActionsPanel() {

    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.DASHBOARD_ACTIONS_PANEL);

    advanceButton.getStyleClass().add(StyleClass.DASHBOARD_ACTIONS_ADVANCE_WEEK);

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
