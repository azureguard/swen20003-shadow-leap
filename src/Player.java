import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

import java.util.ArrayList;

public class Player extends Sprite {
  private static final int PLAYER_INIT_X = 512;
  private static final int PLAYER_INIT_Y = 720;
  private static final String PLAYER_SPRITE = "frog";
  private static final int INIT_LIVES = 3;
  private int lives;
  private boolean isRiding;
  private float rideSpeed;

  public Player() throws SlickException {
    super(PLAYER_SPRITE, PLAYER_INIT_X, PLAYER_INIT_Y);
    lives = INIT_LIVES;
    isRiding = false;
    rideSpeed = 0;
  }

  public void render() {
    super.render();

  }

  public int getLives() {
    return lives;
  }

  public void update(Input input, int delta, ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    if (lives < 0) {
      System.exit(0);
    }

    boolean[] validMoves = contactSolid(obstacles, tiles);

    float newPosX = getPosX(), newPosY = getPosY();
    if (input.isKeyPressed(Input.KEY_UP) && validMoves[0]) {
      newPosY -= getWidth();
    }
    if (input.isKeyPressed(Input.KEY_DOWN) && validMoves[1]) {
      newPosY += getWidth();
    }

    if (input.isKeyPressed(Input.KEY_LEFT) && validMoves[2]) {
      newPosX -= getWidth();
    }
    if (input.isKeyPressed(Input.KEY_RIGHT) && validMoves[3]) {
      newPosX += getWidth();
    }
    BoundingBox nextPos = new BoundingBox(getSprite(), newPosX, newPosY);
    for (Obstacle obstacle : obstacles) {
      if (obstacle instanceof WaterObstacle || obstacle instanceof Bulldozer) {
        if (contactRideable(obstacle, nextPos)) {
          embark(obstacle.getVelocity());
          break;
        } else {
          disembark();
        }
      }
    }

    newPosX += rideSpeed * delta;

    // Movement limiting
    if (newPosX + getWidth() / 2 > App.SCREEN_WIDTH || newPosX - getWidth() / 2 < 0) {
      newPosX = getPosX();
    }
    if (newPosY + getWidth() / 2 > App.SCREEN_HEIGHT || newPosY - getWidth() / 2 < 0) {
      newPosY = getPosY();
    }

    // Update position and bounding box
    setPosX(newPosX);
    setPosY(newPosY);

    BoundingBox boundingBox = getBoundingBox();
    boundingBox.setX(newPosX);
    boundingBox.setY(newPosY);
  }

  public void embark(float rideSpeed) {
    this.isRiding = true;
    this.rideSpeed = rideSpeed;
  }

  public boolean isRiding() {
    return isRiding;
  }

  public void disembark() {
    this.isRiding = false;
    this.rideSpeed = 0;
  }

  private static boolean contactRideable(Obstacle other, BoundingBox nextPos) {
    return (other.getBoundingBox() != null && nextPos.intersects(other.getBoundingBox()));
  }

  private boolean[] contactSolid(ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    BoundingBox[] possiblePositions = new BoundingBox[4];
    possiblePositions[0] = new BoundingBox(getSprite(), getPosX(), getPosY() - getWidth());
    possiblePositions[1] = new BoundingBox(getSprite(), getPosX(), getPosY() + getWidth());
    possiblePositions[2] = new BoundingBox(getSprite(), getPosX() - getWidth(), getPosY());
    possiblePositions[3] = new BoundingBox(getSprite(), getPosX() + getWidth(), getPosY());

    boolean[] validMoves = {true, true, true, true};
    for (Obstacle obstacle : obstacles) {
      if (obstacle.isSolid()) {
        for (int i = 0; i < 4; ++i) {
          validMoves[i] = validMoves[i] && !possiblePositions[i].intersects(obstacle.getBoundingBox());
        }
      }
    }
    for (Tile tile : tiles) {
      if (tile.isSolid()) {
        for (int i = 0; i < 4; ++i) {
          validMoves[i] = validMoves[i] && !possiblePositions[i].intersects(tile.getBoundingBox());
        }
      }
    }
    return validMoves;
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
