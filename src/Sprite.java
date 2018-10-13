import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

/**
 * The Sprite super class for all drawable objects in the game.
 */
public abstract class Sprite {

  private Image sprite, spriteFlipped;
  private boolean drawFlipped;
  private float xPos, yPos;
  private BoundingBox boundingBox;

  /**
   * Get the Image object of the Sprite.
   *
   * @return The Image object of the Sprite.
   */
  public Image getSprite() {
    return sprite;
  }

  /**
   * Instantiates a new Sprite.
   *
   * @param imageSrc        The image src
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Sprite(String imageSrc, float x, float y) throws SlickException {
    sprite = new Image("assets/" + imageSrc + ".png");
    xPos = x;
    yPos = y;
    drawFlipped = false;
    spriteFlipped = null;
    this.boundingBox = new BoundingBox(this.sprite, x, y);
  }


  /**
   * Instantiates a new Sprite.
   *
   * @param imageSrc        The name of the PNG in assets/ without the extension
   * @param x               The x position on creation
   * @param y               The y position on creation
   * @param directionRight  If the sprite starts by moving to the right
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Sprite(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    this(imageSrc, x, y);
    spriteFlipped = sprite.getFlippedCopy(true, false);
    if (!directionRight && !imageSrc.equals("turtle") || (directionRight && imageSrc.equals("turtle"))) {
      drawFlipped = true;
    }
  }

  /**
   * Gets the x position.
   *
   * @return The x position.
   */
  public float getxPos() {
    return xPos;
  }

  /**
   * Sets the x Position.
   *
   * @param xPos The new x position.
   */
  public void setxPos(float xPos) {
    this.xPos = xPos;
  }

  /**
   * Gets the y position.
   *
   * @return The y position.
   */
  public float getyPos() {
    return yPos;
  }

  /**
   * Sets the y position.
   *
   * @param yPos The new y position.
   */
  public void setyPos(float yPos) {
    this.yPos = yPos;
  }

  /**
   * Gets the width of the loaded image asset.
   *
   * @return The width of the image.
   */
  public float getWidth() {
    return sprite.getWidth();
  }

  /**
   * Gets the BoundingBox of the Sprite.
   *
   * @return The BoundingBox of the Sprite.
   */
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  /**
   * Fades in the Sprite and creates its BoundingBox.
   */
  public void enable() {
    if (sprite.getAlpha() < 1) {
      sprite.setAlpha(sprite.getAlpha() + 0.1f);
    } else if (boundingBox == null) {
      boundingBox = new BoundingBox(sprite, xPos, yPos);
    }
  }

  /**
   * Fades out the Sprite and remove its BoundingBox.
   */
  public void disable() {
    if (sprite.getAlpha() > 0) {
      sprite.setAlpha(sprite.getAlpha() - 0.1f);
    } else if (boundingBox != null) {
      boundingBox = null;
    }
  }

  /**
   * Flip the image in the x axis.
   */
  public void changeDirection() {
    drawFlipped = !drawFlipped;
  }

  /**
   * Draws the sprite given its properties and coordinates.
   */
  public void render() {
    if (drawFlipped) {
      spriteFlipped.drawCentered(xPos, yPos);
    } else {
      sprite.drawCentered(xPos, yPos);
    }
  }

  /**
   * Check if the Player has contacted an obstacle. isHazard check already
   * done.
   *
   * @param player the player
   * @return the boolean
   */
  // Called between player and any dangerous sprite
  public boolean contactHazard(Player player) {
    if (boundingBox.intersects(player.getBoundingBox())) {
      player.onDeath();
      return true;
    }
    return false;
  }
}
