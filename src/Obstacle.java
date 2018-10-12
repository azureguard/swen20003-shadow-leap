import org.newdawn.slick.SlickException;

/**
 * The super class of all Obstacles given their same general behaviour.
 */
public class Obstacle extends Sprite {
  private boolean directionRight;
  private final float speed;
  private final boolean isHazard;
  private final boolean isSolid;

  // Defined constants for obstacles given in project specifications
  private enum OBSTACLE {
    /**
     * Bike obstacle constants.
     */
    BIKE(0.2f, true, false),
    /**
     * Bulldozer obstacle constants.
     */
    BULLDOZER(0.05f, false, true),
    /**
     * Bus obstacle constants.
     */
    BUS(0.15f, true, false),
    /**
     * Racecar obstacle constants.
     */
    RACECAR(0.5f, true, false),
    /**
     * Log obstacle constants.
     */
    LOG(0.1f, false, false),
    /**
     * Longlog obstacle constants.
     */
    LONGLOG(0.07f, false, false),
    /**
     * Turtle obstacle constants.
     */
    TURTLE(0.085f, false, false);

    private float speed;
    private boolean isHazard;
    private boolean isSolid;

    OBSTACLE(float speed, boolean isHazard, boolean isSolid) {
      this.speed = speed;
      this.isHazard = isHazard;
      this.isSolid = isSolid;
    }
  }

  /**
   * Instantiates a new Obstacle.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @param directionRight  If the obstacle starts by moving to the right
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Obstacle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    OBSTACLE obs = OBSTACLE.valueOf(imageSrc.toUpperCase());
    this.directionRight = directionRight;
    this.speed = obs.speed;
    this.isHazard = obs.isHazard;
    this.isSolid = obs.isSolid;
  }

  /**
   * Check if the obstacle is hazardous.
   *
   * @return True if the obstacle is hazardous.
   */
  public boolean isHazard() {
    return isHazard;
  }

  /**
   * Check if the obstacle is solid.
   *
   * @return True if the obstacle is solid.
   */
  public boolean isSolid() {
    return isSolid;
  }

  /**
   * Check if the obstacle is moving to the right.
   *
   * @return True if the obstacle is moving to the right.
   */
  public boolean isDirectionRight() {
    return directionRight;
  }

  /**
   * Gets the velocity of the obstalce.
   *
   * @return The velocity in the x-axis.
   */
  public float getVelocity() {
    return speed * (isDirectionRight() ? 1 : -1);
  }

  /**
   * Flips the direction of the obstacle
   */
  public void flipDirection() {
    directionRight = !directionRight;
    changeDirection();
  }

  /**
   * Updates the obstacle for the given delta.
   *
   * @param delta The time taken to render the last frame
   */
  public void update(int delta) {
    float newPosX = getxPos() + delta * getVelocity();

    // Reset obstacle to opposite end of screen
    if (newPosX > App.SCREEN_WIDTH + getWidth() / 2) {
      newPosX = -(getWidth() / 2);
    } else if (newPosX < -getWidth() / 2) {
      newPosX = App.SCREEN_WIDTH + (getWidth() / 2);
    }

    // Update position and bounding box
    setxPos(newPosX);
    if (getBoundingBox() != null) {
      getBoundingBox().setX(newPosX);
    }
  }
}
