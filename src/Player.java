import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public class Player extends Sprite {
  public Player(String imageSrc, float x, float y) throws SlickException {
    super(imageSrc, x, y);
  }

  public void update(Input input) {
    float newPosX = getPosX(), newPosY = getPosY();
    if (input.isKeyPressed(Input.KEY_UP)) {
      newPosY = getPosY() - SPRITE_DIM;
    }
    if (input.isKeyPressed(Input.KEY_DOWN)) {
      newPosY = getPosY() + SPRITE_DIM;
    }

    if (input.isKeyPressed(Input.KEY_LEFT)) {
      newPosX = getPosX() - SPRITE_DIM;
    }
    if (input.isKeyPressed(Input.KEY_RIGHT)) {
      newPosX = getPosX() + SPRITE_DIM;
    }

    // Movement limiting
    if (newPosX + SPRITE_DIM / 2 > App.SCREEN_WIDTH || newPosX < 0) {
      newPosX = getPosX();
    }
    if (newPosY + SPRITE_DIM / 2 > App.SCREEN_HEIGHT || newPosY < 0) {
      newPosY = getPosY();
    }

    // Update position and bounding box
    setPosX(newPosX);
    setPosY(newPosY);
    BoundingBox boundingBox = getBoundingBox();
    boundingBox.setX(newPosX);
    boundingBox.setY(newPosY);
  }
}
