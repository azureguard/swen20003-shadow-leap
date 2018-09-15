import org.newdawn.slick.SlickException;

public class Obstacle extends Sprite {
  private boolean directionRight;
  private final float speed;
  private final boolean isHazard;
  private final boolean isSolid;

  private enum OBSTACLE {
    BIKE(0.2f, true, false),
    BULLDOZER(0.05f, false, true),
    BUS(0.15f, true, false),
    RACECAR(0.5f, true, false),
    // Water obstacles
    LOG(0.1f, false, false),
    LONGLOG(0.07f, false, false),
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

  public Obstacle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    OBSTACLE obs = OBSTACLE.valueOf(imageSrc.toUpperCase());
    this.directionRight = directionRight;
    this.speed = obs.speed;
    this.isHazard = obs.isHazard;
    this.isSolid = obs.isSolid;
  }

  public boolean isHazard() {
    return isHazard;
  }

  public boolean isSolid() {
    return isSolid;
  }

  public boolean isDirectionRight() {
    return directionRight;
  }

  public void flipDirectionRight() {
    directionRight = !directionRight;
    flipImage();
  }

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
