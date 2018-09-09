import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public abstract class Sprite {
  public static final int SPRITE_DIM = 48;

  private Image sprite;
  private float posX, posY;
  private BoundingBox boundingBox;

  public Sprite(String imageSrc, float x, float y) throws SlickException {
    sprite = new Image(imageSrc);
    posX = x;
    posY = y;
    this.boundingBox = new BoundingBox(this.sprite, x, y);
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

  // How can this one method deal with different types of sprites?
  // public void update(Input input, int delta) {}

  public void render() {
    sprite.drawCentered(posX, posY);
  }

  // Called between player and any dangerous sprite
  public void contactSprite(Sprite other) {
    if (boundingBox.intersects(other.getBoundingBox())) {
      System.exit(0);
    }
  }
}
