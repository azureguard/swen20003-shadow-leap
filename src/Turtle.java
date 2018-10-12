import org.newdawn.slick.SlickException;

/**
 * The type Turtle.
 */
public class Turtle extends WaterObstacle {
  private int time;

  /**
   * Instantiates a new Turtle.
   *
   * @param imageSrc       the image src
   * @param x              the x
   * @param y              the y
   * @param directionRight the direction right
   * @throws SlickException the slick exception
   */
  public Turtle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    time = 0;
  }

  @Override
  public void update(int delta) {
    super.update(delta);
    time += delta;
    if (time > 9000) {
      // appear
      enable();
      time = 0;
    } else if (time > 7000) {
      // disappear
      disable();
    } else {
      enable();
    }
  }
}
