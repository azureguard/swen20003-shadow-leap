/**
 * The interface Rideable, used mainly for upcasting.
 */
public interface Rideable {
  /**
   * Allows Obstacles to be ridden.
   *
   * @param player the Player
   */
  void ride(Player player);
}
