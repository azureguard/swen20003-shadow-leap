import org.newdawn.slick.SlickException;

public class Bike extends Obstacle {
  private static final float EPSILON = 0.0000001f;
  private int time;

  public Bike(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    time = 0;
  }

  @Override
  public void update(int delta) {
    super.update(delta);

    // Bike flip direction, timer and epsilon comparison to account for hysteresis
    float currXPos = getPosX();
    if (time == 0 && (currXPos - 24 < EPSILON || 1000 - currXPos < EPSILON)) {
      flipDirectionRight();
      time += delta;
    } else if (time > 250) {
      time = 0;
    } else if (time > 0) {
      time += delta;
    }
  }
}
