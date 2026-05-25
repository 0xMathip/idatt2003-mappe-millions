package no.ntnu.group51.view.components.shared;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Reusable grid-based row used in searchable menus.
 */
public class SearchRow extends GridPane {
  private static final double[] COLUMN_WIDTHS = {
      15, 35, 15, 15, 15, 5
  };

  private static final PseudoClass SELECTED =
      PseudoClass.getPseudoClass("selected");

  /**
   * Creates a search row with default column widths.
   */
  public SearchRow() {
    this(COLUMN_WIDTHS);
  }

  /**
   * Creates a search row with custom column widths.
   *
   * @param columnWidths the column widths in percent
   */
  public SearchRow(double... columnWidths) {
    getStyleClass().addAll(StyleClass.CARD, StyleClass.SEARCH_ROW);
    setAlignment(Pos.CENTER_LEFT);
    setHgap(8);
    setVgap(2);
    setPadding(new Insets(0, 20, 0, 20));

    addColumnConstraints(columnWidths);
  }

  /**
   * Adds a node to a specific cell.
   *
   * @param node   the node to add
   * @param column the target column
   * @param row    the target row
   */
  public void addToCell(Node node, int column, int row) {
    add(node, column, row);
  }

  /**
   * Adds a node to a specific cell with span.
   *
   * @param node       the node to add
   * @param column     the target column
   * @param row        the target row
   * @param columnSpan the number of columns to span
   * @param rowSpan    the number of rows to span
   */
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

  /**
   * Updates the selected visual state of the row.
   *
   * @param selected true if the row should appear selected
   */
  public void setSelected(boolean selected) {
    pseudoClassStateChanged(SELECTED, selected);
  }

}
