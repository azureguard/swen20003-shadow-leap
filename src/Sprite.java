import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public abstract class Sprite {

  private Image sprite, spriteFlipped;
  private boolean drawFlipped;
  private float posX, posY;
  private BoundingBox boundingBox;

  public Image getSprite() {
    return sprite;
  }

  public Sprite(String imageSrc, float x, float y) throws SlickException {
    sprite = new Image("assets/" + imageSrc + ".png");
    posX = x;
    posY = y;
    drawFlipped = false;
    spriteFlipped = null;
    this.boundingBox = new BoundingBox(this.sprite, x, y);
  }


  public Sprite(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    this(imageSrc, x, y);
    spriteFlipped = sprite.getFlippedCopy(true, false);
    if (!directionRight && !imageSrc.equals("turtle") || (directionRight && imageSrc.equals("turtle"))) {
      drawFlipped = true;
    }
  }

  public float getPosX() {
    return posX;
  }

  public void setPosX(float posX) {
    this.posX = posX;
  }

  public float getPosY() {
    return posY;
  }

  public void setPosY(float posY) {
    this.posY = posY;
  }

  public float getWidth() {
    return sprite.getWidth();
  }

  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void enable() {
    if (sprite.getAlpha() < 1) {
      sprite.setAlpha(sprite.getAlpha() + 0.1f);
    } else if (boundingBox == null) {
      boundingBox = new BoundingBox(sprite, posX, posY);
    }
  }

  public void disable() {
    if (sprite.getAlpha() > 0) {
      sprite.setAlpha(sprite.getAlpha() - 0.1f);
    } else if (boundingBox != null) {
      boundingBox = null;
    }
  }

  public void changeDirection() {
    drawFlipped = !drawFlipped;
  }

  public void render() {
    if (drawFlipped) {
      spriteFlipped.drawCentered(posX, posY);
    } else {
      sprite.drawCentered(posX, posY);
    }
  }

  // Called between player and any dangerous sprite
  public boolean contactHazard(Player player) {
    if (boundingBox.intersects(player.getBoundingBox())) {
      player.onDeath();
      return true;
    }
    return false;
  }
}
