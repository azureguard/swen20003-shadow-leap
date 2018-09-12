import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public class Player extends Sprite {
  private static final int PLAYER_INIT_X = 512;
  private static final int PLAYER_INIT_Y = 720;

  public Player() throws SlickException {
    super("frog", PLAYER_INIT_X, PLAYER_INIT_Y);
  }

  public void update(Input input) {
    float newPosX = getPosX(), newPosY = getPosY();
    if (input.isKeyPressed(Input.KEY_UP)) {
      newPosY = getPosY() - DEFAULT_WIDTH;
    }
    if (input.isKeyPressed(Input.KEY_DOWN)) {
      newPosY = getPosY() + DEFAULT_WIDTH;
    }

    if (input.isKeyPressed(Input.KEY_LEFT)) {
      newPosX = getPosX() - DEFAULT_WIDTH;
    }
    if (input.isKeyPressed(Input.KEY_RIGHT)) {
      newPosX = getPosX() + DEFAULT_WIDTH;
    }

    // Movement limiting
    if (newPosX > App.SCREEN_WIDTH || newPosX < 0) {
      newPosX = getPosX();
    }
    if (newPosY > App.SCREEN_HEIGHT || newPosY < 0) {
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
