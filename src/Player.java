import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

import java.util.ArrayList;

/**
 * The user controlled Player object.
 */
public class Player extends Sprite {
  private static final int PLAYER_INIT_X = 512;
  private static final int PLAYER_INIT_Y = 720;
  private static final String PLAYER_SPRITE = "frog";
  private static final int INIT_LIVES = 3;
  private int lives;
  private Rideable riding;
  private boolean isRiding;
  private float rideSpeed;

  /**
   * Instantiates a new Player.
   *
   * @throws SlickException Indicates a failure to load the player image asset
   */
  public Player() throws SlickException {
    super(PLAYER_SPRITE, PLAYER_INIT_X, PLAYER_INIT_Y);
    lives = INIT_LIVES;
    isRiding = false;
    rideSpeed = 0;
  }

  /**
   * Gets lives.
   *
   * @return The number of lives the player has left
   */
  public int getLives() {
    return lives;
  }

  /**
   * Allows the player to make valid movement given the game state of obstacles
   * and tiles.
   *
   * @param input     Input from interface devices
   * @param delta     The time taken to render the last frame
   * @param obstacles The obstacles in the Level object
   * @param tiles     The tiles in the Level object
   */
  public void update(Input input, int delta, ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    if (lives < 0) {
      System.exit(0);
    }
    // Determine valid movement directions
    boolean[] validMoves = solidDetection(obstacles, tiles);

    float newPosX = getxPos(), newPosY = getyPos();
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

    // When riding a Riadeable, keep relative position
    newPosX += rideSpeed * delta;

    // Movement limiting
    if (newPosX + getWidth() / 2 > App.SCREEN_WIDTH || newPosX - getWidth() / 2 < 0) {
      // Player pushed to screen edge by bulldozer
      if (isRiding && riding instanceof Bulldozer) {
        onDeath();
        return;
      }
      newPosX = getxPos();
    }
    if (newPosY + getWidth() / 2 > App.SCREEN_HEIGHT || newPosY - getWidth() / 2 < 0) {
      newPosY = getyPos();
    }

    disembark();

    // Update position and bounding box
    setxPos(newPosX);
    setyPos(newPosY);

    BoundingBox boundingBox = getBoundingBox();
    boundingBox.setX(newPosX);
    boundingBox.setY(newPosY);
  }

  /**
   * Called when Player is in contact with a Rideable.
   *
   * @param other The Rideable object to embark on.
   */
  public void embark(Rideable other) {
    this.isRiding = true;
    this.riding = other;
    this.rideSpeed = ((Obstacle) other).getVelocity();
  }

  /**
   * Check if the Player is riding a Rideable.
   *
   * @return True if the Player is riding a Rideable.
   */
  public boolean isRiding() {
    return isRiding;
  }

  /**
   * Disembark from a Rideable.
   */
  public void disembark() {
    this.isRiding = false;
    this.rideSpeed = 0;
  }

  private boolean[] solidDetection(ArrayList<Obstacle> obstacles, ArrayList<Tile> tiles) {
    // Offset boxes for movement validation
    BoundingBox[] possiblePositions = new BoundingBox[4];
    possiblePositions[0] = new BoundingBox(getSprite(), getxPos(), getyPos() - getWidth());
    possiblePositions[1] = new BoundingBox(getSprite(), getxPos(), getyPos() + getWidth());
    possiblePositions[2] = new BoundingBox(getSprite(), getxPos() - getWidth(), getyPos());
    possiblePositions[3] = new BoundingBox(getSprite(), getxPos() + getWidth(), getyPos());

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

  /**
   * Grants the Player an extra life.
   */
  public void onBonus() {
    lives += 1;
  }

  /**
   * Resets the Player position.
   */
  public void reset() {
    setxPos(PLAYER_INIT_X);
    setyPos(PLAYER_INIT_Y);
  }

  /**
   * Takes a life from the Player.
   */
  public void onDeath() {
    reset();
    lives -= 1;
  }
}
