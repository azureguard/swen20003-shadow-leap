import org.newdawn.slick.SlickException;

/**
 * The Turtle WaterObstacle which disappears and reappears at set intervals.
 */
public class Turtle extends WaterObstacle {
  private int time;

  /**
   * Instantiates a new Turtle.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @param directionRight  If the Turtle starts by moving to the right
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Turtle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    time = 0;
  }

  @Override
  public void update(int delta) {
    super.update(delta);
    // Increments the time steps from the delta.
    time += delta;
    if (time > 9000) {
      // Appear again after 2 seconds
      enable();
      time = 0;
    } else if (time > 7000) {
      // Disappear after 7 seconds
      disable();
    } else {
      // Make fully visible (enable fades in the image)
      enable();
    }
  }
}
