import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public class Player extends Sprite {
  private static final int PLAYER_INIT_X = 512;
  private static final int PLAYER_INIT_Y = 720;
  private static final String PLAYER_SPRITE = "frog";
  private static final int INIT_LIVES = 3;
  private int lives;

  public Player() throws SlickException {
    super(PLAYER_SPRITE, PLAYER_INIT_X, PLAYER_INIT_Y);
    lives = INIT_LIVES;
  }

  public void render() {
    super.render();

  }

  public void update(Input input) {
    if (lives < 0) {
      System.exit(0);
    }
    float newPosX = getPosX(), newPosY = getPosY();
    if (input.isKeyPressed(Input.KEY_UP)) {
      newPosY -= getWidth();
    }
    if (input.isKeyPressed(Input.KEY_DOWN)) {
      newPosY += getWidth();
    }

    if (input.isKeyPressed(Input.KEY_LEFT)) {
      newPosX -= getWidth();
    }
    if (input.isKeyPressed(Input.KEY_RIGHT)) {
      newPosX += getWidth();
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

  public void onBonus() {
    lives += 1;
  }

  public void onDeath() {
    setPosX(PLAYER_INIT_X);
    setPosY(PLAYER_INIT_Y);
    lives -= 1;
  }
}
