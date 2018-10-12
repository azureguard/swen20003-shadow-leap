import org.newdawn.slick.SlickException;

/**
 * The type Bulldozer.
 */
public class Bulldozer extends Obstacle implements Rideable {
  /**
   * Instantiates a new Bulldozer.
   *
   * @param imageSrc       the image src
   * @param x              the x
   * @param y              the y
   * @param directionRight the direction right
   * @throws SlickException the slick exception
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
