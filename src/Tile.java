import org.newdawn.slick.SlickException;

public class Tile extends Sprite {
  private final boolean isHazard;
  private final boolean isSolid;

  private enum TILE {
    WATER(true, false),
    GRASS(false, false),
    TREE(false, true);

    private boolean isHazard;
    private boolean isSolid;

    TILE(boolean isHazard, boolean isSolid) {
      this.isHazard = isHazard;
      this.isSolid = isSolid;
    }
  }

  public Tile(String imageSrc, float x, float y) throws SlickException {
    super(imageSrc, x, y);
    TILE tile = TILE.valueOf(imageSrc.toUpperCase());
    this.isHazard = tile.isHazard;
    this.isSolid = tile.isSolid;
  }

  public boolean isSolid() {
    return isSolid;
  }

  public boolean isHazard() {
    return isHazard;
  }
}
