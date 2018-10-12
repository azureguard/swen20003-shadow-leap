import org.newdawn.slick.SlickException;

/**
 * The type Water obstacle.
 */
public class WaterObstacle extends Obstacle implements Rideable {
  /**
   * Instantiates a new Water obstacle.
   *
   * @param imageSrc       the image src
   * @param x              the x
   * @param y              the y
   * @param directionRight the direction right
   * @throws SlickException the slick exception
   */
  public WaterObstacle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
  }

  @Override
  public void ride(Player player) {
    if (getBoundingBox() != null && getBoundingBox().intersects(player.getBoundingBox())) {
      player.embark(this);
    }
  }
}
