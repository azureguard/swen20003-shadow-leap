import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

/**
 * The type Sprite.
 */
public abstract class Sprite {

  private Image sprite, spriteFlipped;
  private boolean drawFlipped;
  private float posX, posY;
  private BoundingBox boundingBox;

  /**
   * Gets sprite.
   *
   * @return the sprite
   */
  public Image getSprite() {
    return sprite;
  }

  /**
   * Instantiates a new Sprite.
   *
   * @param imageSrc the image src
   * @param x        the x
   * @param y        the y
   * @throws SlickException the slick exception
   */
  public Sprite(String imageSrc, float x, float y) throws SlickException {
    sprite = new Image("assets/" + imageSrc + ".png");
    posX = x;
    posY = y;
    drawFlipped = false;
    spriteFlipped = null;
    this.boundingBox = new BoundingBox(this.sprite, x, y);
  }


  /**
   * Instantiates a new Sprite.
   *
   * @param imageSrc       the image src
   * @param x              the x
   * @param y              the y
   * @param directionRight the direction right
   * @throws SlickException the slick exception
   */
  public Sprite(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    this(imageSrc, x, y);
    spriteFlipped = sprite.getFlippedCopy(true, false);
    if (!directionRight && !imageSrc.equals("turtle") || (directionRight && imageSrc.equals("turtle"))) {
      drawFlipped = true;
    }
  }

  /**
   * Gets pos x.
   *
   * @return the pos x
   */
  public float getPosX() {
    return posX;
  }

  /**
   * Sets pos x.
   *
   * @param posX the pos x
   */
  public void setPosX(float posX) {
    this.posX = posX;
  }

  /**
   * Gets pos y.
   *
   * @return the pos y
   */
  public float getPosY() {
    return posY;
  }

  /**
   * Sets pos y.
   *
   * @param posY the pos y
   */
  public void setPosY(float posY) {
    this.posY = posY;
  }

  /**
   * Gets width.
   *
   * @return the width
   */
  public float getWidth() {
    return sprite.getWidth();
  }

  /**
   * Gets bounding box.
   *
   * @return the bounding box
   */
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  /**
   * Enable.
   */
  public void enable() {
    if (sprite.getAlpha() < 1) {
      sprite.setAlpha(sprite.getAlpha() + 0.1f);
    } else if (boundingBox == null) {
      boundingBox = new BoundingBox(sprite, posX, posY);
    }
  }

  /**
   * Disable.
   */
  public void disable() {
    if (sprite.getAlpha() > 0) {
      sprite.setAlpha(sprite.getAlpha() - 0.1f);
    } else if (boundingBox != null) {
      boundingBox = null;
    }
  }

  /**
   * Change direction.
   */
  public void changeDirection() {
    drawFlipped = !drawFlipped;
  }

  /**
   * Render.
   */
  public void render() {
    if (drawFlipped) {
      spriteFlipped.drawCentered(posX, posY);
    } else {
      sprite.drawCentered(posX, posY);
    }
  }

  /**
   * Contact hazard boolean.
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
