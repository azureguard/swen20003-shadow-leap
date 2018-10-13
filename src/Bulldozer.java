import org.newdawn.slick.SlickException;

/**
 * The Bulldozer which can push a Player.
 */
public class Bulldozer extends Obstacle implements Rideable {
  /**
   * Instantiates a new Bulldozer.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @param directionRight  If the Bulldozer starts by moving to the right
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Bulldozer(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
  }

  @Override
  public void ride(Player player) {
    if (getBoundingBox() != null && getBoundingBox().intersects(player.getBoundingBox())) {
      player.embark(this);
    }
  }
}
