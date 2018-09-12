import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public abstract class Sprite {
  public static final int DEFAULT_WIDTH = 48;

  private Image sprite;
  private float posX, posY;
  private BoundingBox boundingBox;

  public Image getSprite() {
    return sprite;
  }

  public Sprite(String imageSrc, float x, float y) throws SlickException {
    sprite = new Image("assets/" + imageSrc + ".png");
    posX = x;
    posY = y;
    this.boundingBox = new BoundingBox(this.sprite, x, y);
  }


  public Sprite(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    this(imageSrc, x, y);
    if (!directionRight && !imageSrc.equals("turtle") || (directionRight && imageSrc.equals("turtle"))) {
      sprite = sprite.getFlippedCopy(true, false);
    }
    sprite.setAlpha(1.0f);
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

  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void enable() {
    sprite.setAlpha(sprite.getAlpha() + 0.1f);
  }

  public void disable() {
    sprite.setAlpha(sprite.getAlpha() - 0.1f);
  }

  public void flipImage() {
    sprite = sprite.getFlippedCopy(true, false);
  }

  // How can this one method deal with different types of sprites?
  // public void update(Input input, int delta) {}

  public void render() {
    sprite.drawCentered(posX, posY);
  }

  public void contactSolid(Sprite other) {

  }

  // Called between player and any dangerous sprite
  public void contactHazard(Sprite other) {
    if (boundingBox.intersects(other.getBoundingBox())) {
      System.exit(0);
    }
  }
}
