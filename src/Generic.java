import org.newdawn.slick.SlickException;

public class Generic extends Obstacle {
  private static final float SPEED = 0.15f;

  public Generic(String imageSrc, float x, float y, boolean direction)
          throws SlickException {
    super(imageSrc, x, y, direction);
  }

  //  public void update(int delta) {
  //    int direction = isDirectionRight() ? 1 : -1;
  //    float newPosX = getPosX() + delta * direction * SPEED;
  //
  //    // Reset bus to opposite end of screen
  //    if (newPosX > App.SCREEN_WIDTH) {
  //      newPosX = -DEFAULT_WIDTH;
  //    } else if (newPosX < -DEFAULT_WIDTH) {
  //      newPosX = App.SCREEN_WIDTH;
  //    }
  //
  //    // Update position and bounding box
  //    setPosX(newPosX);
  //    getBoundingBox().setX(newPosX);
  //  }
}
