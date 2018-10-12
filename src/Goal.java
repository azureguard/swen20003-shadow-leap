import org.newdawn.slick.SlickException;

/**
 * The type Goal.
 */
public class Goal extends Sprite {
  /**
   * The constant GOAL_LINE.
   */
  public static final float GOAL_LINE = 48;
  /**
   * The constant GOAL_WIDTH.
   */
  public static final float GOAL_WIDTH = 144;
  private static final String GOAL_IMAGE = "frog";
  private boolean achieved;

  /**
   * Instantiates a new Goal.
   *
   * @param x the x
   * @param y the y
   * @throws SlickException the slick exception
   */
  public Goal(float x, float y) throws SlickException {
    super(GOAL_IMAGE, x, y);
    achieved = false;
  }

  @Override
  public void render() {
    if (achieved) {
      super.render();
    }
  }

  /**
   * Is achieved boolean.
   *
   * @return the boolean
   */
  public boolean isAchieved() {
    return achieved;
  }

  /**
   * Check achieved boolean.
   *
   * @param player the player
   * @return the boolean
   */
  public boolean checkAchieved(Player player) {
    if (!achieved && player.getBoundingBox().intersects(this.getBoundingBox())) {
      achieved = true;
      return true;
    } else {
      return false;
    }
  }
}
