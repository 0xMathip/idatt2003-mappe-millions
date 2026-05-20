package no.ntnu.group51.view;

import javafx.scene.Parent;

/**
 * An interface for the view. Has methods to implement in every view class.
 */
public interface View {

  /**
   * Used to get the root of the view.
   *
   * @return the root
   */
  Parent getRoot();
}
