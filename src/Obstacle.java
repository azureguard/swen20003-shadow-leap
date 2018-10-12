import org.newdawn.slick.SlickException;

/**
 * The type Obstacle.
 */
public class Obstacle extends Sprite {
  private boolean directionRight;
  private final float speed;
  private final boolean isHazard;
  private final boolean isSolid;

  private enum OBSTACLE {
    /**
     * Bike obstacle.
     */
    BIKE(0.2f, true, false),
    /**
     * Bulldozer obstacle.
     */
    BULLDOZER(0.05f, false, true),
    /**
     * Bus obstacle.
     */
    BUS(0.15f, true, false),
    /**
     * Racecar obstacle.
     */
    RACECAR(0.5f, true, false),
    /**
     * The Log.
     */
    // Water obstacles
    LOG(0.1f, false, false),
    /**
     * Longlog obstacle.
     */
    LONGLOG(0.07f, false, false),
    /**
     * Turtle obstacle.
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
   * @param imageSrc       the image src
   * @param x              the x
   * @param y              the y
   * @param directionRight the direction right
   * @throws SlickException the slick exception
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
   * Is hazard boolean.
   *
   * @return the boolean
   */
  public boolean isHazard() {
    return isHazard;
  }

  /**
   * Is solid boolean.
   *
   * @return the boolean
   */
  public boolean isSolid() {
    return isSolid;
  }

  /**
   * Is direction right boolean.
   *
   * @return the boolean
   */
  public boolean isDirectionRight() {
    return directionRight;
  }

  /**
   * Gets velocity.
   *
   * @return the velocity
   */
  public float getVelocity() {
    return speed * (isDirectionRight() ? 1 : -1);
  }

  /**
   * Flip direction right.
   */
  public void flipDirectionRight() {
    directionRight = !directionRight;
    changeDirection();
  }

  /**
   * Update.
   *
   * @param delta the delta
   */
  public void update(int delta) {
    int direction = isDirectionRight() ? 1 : -1;
    float newPosX = getPosX() + delta * direction * speed;

    // Reset obstacle to opposite end of screen
    if (newPosX > App.SCREEN_WIDTH + getWidth() / 2) {
      newPosX = -(getWidth() / 2);
    } else if (newPosX < -getWidth() / 2) {
      newPosX = App.SCREEN_WIDTH + (getWidth() / 2);
    }

    // Update position and bounding box
    setPosX(newPosX);
    if (getBoundingBox() != null) {
      getBoundingBox().setX(newPosX);
    }
  }
}
