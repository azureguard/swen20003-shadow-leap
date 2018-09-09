import org.newdawn.slick.SlickException;

public class Bus extends Sprite {
  private static final float SPEED = 0.15f;
  private int direction;

  public Bus(String imageSrc, float x, float y, int direction)
          throws SlickException {
    super(imageSrc, x, y);
    this.direction = direction;
  }

  public void update(int delta) {
    float newPosX = getPosX() + delta * direction * SPEED;

    // Reset bus to opposite end of screen
    if (newPosX > App.SCREEN_WIDTH) {
      newPosX = -SPRITE_DIM;
    } else if (newPosX < -SPRITE_DIM) {
      newPosX = App.SCREEN_WIDTH;
    }

    // Update position and bounding box
    setPosX(newPosX);
    getBoundingBox().setX(newPosX);
  }
}
