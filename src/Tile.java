import org.newdawn.slick.SlickException;

public class Tile extends Sprite {
  private boolean isDangerous;

  public Tile(String imageSrc, float x, float y, boolean isDangerous) throws SlickException {
    super(imageSrc, x, y);
    this.isDangerous = isDangerous;
  }

  public boolean isDangerTile() {
    return isDangerous;
  }
}
