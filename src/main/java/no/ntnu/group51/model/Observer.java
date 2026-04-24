package no.ntnu.group51.model;

/**
 * Represents an observer in the Observer pattern.
 *
 * <p>Classes that implement this interface can register themselves to a subject
 * (i.e. {@link GameModel}) to be notified when its state changes.
 *
 * <p>When the subject updates its internal state, it calls ({@code update()})
 * on all registered observers. The individual observers are then responsible for
 * retrieving the updated data from the subject and updating themselves accordingly
 * (i.e. refreshing the GUI).
 *
 * <p>Mainly used to update GUI (view) components.
 */
public interface Observer {

  /**
   * Called by the subject when its state changes.
   *
   * <p>Implementing classes should update their state based on the
   * current data from the subject.
   */
  void update();
}
