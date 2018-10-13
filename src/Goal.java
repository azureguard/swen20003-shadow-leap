import org.newdawn.slick.SlickException;

/**
 * The Goal that a Player has to reach to progress in the game.
 */
public class Goal extends Sprite {
  /**
   * The constant GOAL_LINE where Goals are to be created.
   */
  public static final float GOAL_LINE = 48;
  /**
   * The constant GOAL_WIDTH that goals need to have.
   */
  public static final float GOAL_WIDTH = 144;
  private static final String GOAL_IMAGE = "frog";
  private boolean achieved;

  /**
   * Instantiates a new Goal.
   *
   * @param x The x position on creation
   * @param y The y position on creation
   * @throws SlickException Indicates a failure to load an image asset
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
   * Check if the goal has been achieved.
   *
   * @return True is the Goal has been achieved.
   */
  public boolean isAchieved() {
    return achieved;
  }

  /**
   * Check if the Player has reached a goal.
   *
   * @param player the Player
   * @return True if the Player has reached the Goal.
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
