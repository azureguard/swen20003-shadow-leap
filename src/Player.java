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
  private Rideable riding;
  private boolean isRiding;
  private float rideSpeed;

  public Player() throws SlickException {
    super(PLAYER_SPRITE, PLAYER_INIT_X, PLAYER_INIT_Y);
    lives = INIT_LIVES;
    isRiding = false;
    rideSpeed = 0;
  }

  public int getLives() {
    return lives;
  }

  public void update(Input input, int delta, ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    if (lives < 0) {
      System.exit(0);
    }
    // Determine valid movement directions
    boolean[] validMoves = solidDetection(obstacles, tiles);

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

    newPosX += rideSpeed * delta;

    // Movement limiting
    if (newPosX + getWidth() / 2 > App.SCREEN_WIDTH || newPosX - getWidth() / 2 < 0) {
      // Player pushed to screen edge by bulldozer
      if (isRiding && riding instanceof Bulldozer) {
        onDeath();
        return;
      }
      newPosX = getPosX();
    }
    if (newPosY + getWidth() / 2 > App.SCREEN_HEIGHT || newPosY - getWidth() / 2 < 0) {
      newPosY = getPosY();
    }

    disembark();

    // Update position and bounding box
    setPosX(newPosX);
    setPosY(newPosY);

    BoundingBox boundingBox = getBoundingBox();
    boundingBox.setX(newPosX);
    boundingBox.setY(newPosY);
  }

  public void embark(Rideable other) {
    this.isRiding = true;
    this.riding = other;
    this.rideSpeed = ((Obstacle) other).getVelocity();
  }

  public boolean isRiding() {
    return isRiding;
  }

  public void disembark() {
    this.isRiding = false;
    this.rideSpeed = 0;
  }

  private boolean[] solidDetection(ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    // Offset boxes for movement validation
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
