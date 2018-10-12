import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

/**
 * The type Bonus.
 */
public class Bonus extends Sprite {
  private WaterObstacle attachedTo;
  private int time;
  private boolean directionRight;
  private float offsetX;

  /**
   * Instantiates a new Bonus.
   *
   * @param imageSrc   the image src
   * @param attachedTo the attached to
   * @throws SlickException the slick exception
   */
  public Bonus(String imageSrc, WaterObstacle attachedTo) throws SlickException {
    super(imageSrc, attachedTo.getPosX(), attachedTo.getPosY());
    this.attachedTo = attachedTo;
    time = 0;
    directionRight = true;
    offsetX = 0;
  }

  /**
   * Update.
   *
   * @param delta the delta
   */
  public void update(int delta) {
    time += delta;
    int direction = directionRight ? 1 : -1;

    BoundingBox nextPos = new BoundingBox(getBoundingBox());
    nextPos.setX(getPosX() + getWidth() * direction);

    if (!nextPos.intersects(attachedTo.getBoundingBox())) {
      directionRight = !directionRight;
      direction *= -1;
    }

    if (time > 2000) {
      offsetX += direction * getWidth();
      time = 0;
    }
    setPosX(attachedTo.getPosX() + offsetX);

    getBoundingBox().setX(getPosX());
  }
}
