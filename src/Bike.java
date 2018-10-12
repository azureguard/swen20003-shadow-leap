import org.newdawn.slick.SlickException;

/**
 * The Bike obstacle which changes direction at the screen edge.
 */
public class Bike extends Obstacle {
  // For floating point comparison
  private static final float EPSILON = 0.0000001f;
  // Account for tendency to get stuck in flip loop
  private int time;

  /**
   * Instantiates a new Bike.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @param directionRight  If the Bike starts by moving to the right
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Bike(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    time = 0;
  }

  @Override
  public void update(int delta) {
    super.update(delta);

    // Bike flip direction, timer and epsilon comparison to account for
    // hysteresis when at the edges and stuck in a flip loop
    float currXPos = getxPos();
    if (time == 0 && (currXPos - 24 < EPSILON || 1000 - currXPos < EPSILON)) {
      flipDirection();
      time += delta;
    } else if (time > 250) {
      time = 0;
    } else if (time > 0) {
      time += delta;
    }
  }
}
