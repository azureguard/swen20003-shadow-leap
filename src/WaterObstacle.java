import org.newdawn.slick.SlickException;

/**
 * The Water Obstacle class for all objects that exist on water.
 */
public class WaterObstacle extends Obstacle implements Rideable {
  /**
   * Instantiates a new Water obstacle.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @throws SlickException Indicates a failure to load an image asset
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
