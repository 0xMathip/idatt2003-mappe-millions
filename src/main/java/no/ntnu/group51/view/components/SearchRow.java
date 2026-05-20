package no.ntnu.group51.view.components;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class SearchRow extends GridPane {
  private static final double[] COLUMN_WIDTHS = {
      15, 35, 15, 15, 15, 5
  };

  private static final PseudoClass SELECTED =
      PseudoClass.getPseudoClass("selected");

  public SearchRow(){
    this(COLUMN_WIDTHS);
  }

  public SearchRow(double... columnWidths) {
    getStyleClass().addAll("card", "search-row");
    setAlignment(Pos.CENTER_LEFT);
    setHgap(8);
    setVgap(2);
    setPadding(new Insets(0, 20, 0, 20));

    addColumnConstraints(columnWidths);
  }

  public void addToCell(Node node, int column, int row) {
    add(node, column, row);
  }

  public void addToCell(Node node, int column, int row, int columnSpan, int rowSpan) {
    add(node, column, row, columnSpan, rowSpan);
  }

  private void addColumnConstraints(double... columnWidths) {
    for (double width : columnWidths) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(width);
      getColumnConstraints().add(column);
    }
  }

  public void setSelected(boolean selected) {
    pseudoClassStateChanged(SELECTED, selected);
  }

}
