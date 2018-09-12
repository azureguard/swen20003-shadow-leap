import org.newdawn.slick.SlickException;

public class Bike extends Obstacle {
  private static final float EPSILON = 0.0000001f;

  public Bike(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
  }

  @Override
  public void update(int delta) {
    super.update(delta);
    float currXPos = getPosX();
    if (currXPos - 24 < EPSILON || 1000 - currXPos < EPSILON) {
      flipDirectionRight();
    }
  }
}
