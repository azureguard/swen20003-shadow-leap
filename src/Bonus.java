import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

/**
 * The Bonus object which grants the player an extra life.
 */
public class Bonus extends Sprite {
  private WaterObstacle attachedTo;
  private int time;
  private boolean directionRight;
  private float offsetX;

  /**
   * Instantiates a new Bonus object.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param attachedTo      The (WaterObstacle) log which it is attached to
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Bonus(String imageSrc, WaterObstacle attachedTo) throws SlickException {
    super(imageSrc, attachedTo.getxPos(), attachedTo.getyPos());
    this.attachedTo = attachedTo;
    time = 0;
    directionRight = true;
    offsetX = 0;
  }

  /**
   * Updates the position of the Bonus relative to the WaterObstacle it is
   * attached to for the given delta.
   *
   * @param delta The time taken to render the last frame
   */
  public void update(int delta) {
    time += delta;
    int direction = directionRight ? 1 : -1;

    BoundingBox nextPos = new BoundingBox(getBoundingBox());
    nextPos.setX(getxPos() + getWidth() * direction);

    if (!nextPos.intersects(attachedTo.getBoundingBox())) {
      directionRight = !directionRight;
      direction *= -1;
    }

    if (time > 2000) {
      offsetX += direction * getWidth();
      time = 0;
    }
    setxPos(attachedTo.getxPos() + offsetX);

    getBoundingBox().setX(getxPos());
  }
}
