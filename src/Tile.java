import org.newdawn.slick.SlickException;

/**
 * The type Tile.
 */
public class Tile extends Sprite {
  private final boolean isHazard;
  private final boolean isSolid;

  private enum TILE {
    /**
     * Water tile.
     */
    WATER(true, false),
    /**
     * Grass tile.
     */
    GRASS(false, false),
    /**
     * Tree tile.
     */
    TREE(false, true),
    /**
     * Goal tile.
     */
    FROG(false, false);

    private boolean isHazard;
    private boolean isSolid;

    TILE(boolean isHazard, boolean isSolid) {
      this.isHazard = isHazard;
      this.isSolid = isSolid;
    }
  }

  /**
   * Instantiates a new Tile.
   *
   * @param imageSrc the image src
   * @param x        the x
   * @param y        the y
   * @throws SlickException the slick exception
   */
  public Tile(String imageSrc, float x, float y) throws SlickException {
    super(imageSrc, x, y);
    this.isHazard = TILE.valueOf(imageSrc.toUpperCase()).isHazard;
    this.isSolid = TILE.valueOf(imageSrc.toUpperCase()).isSolid;
  }

  /**
   * Is solid boolean.
   *
   * @return the boolean
   */
  public boolean isSolid() {
    return isSolid;
  }

  /**
   * Is hazard boolean.
   *
   * @return the boolean
   */
  public boolean isHazard() {
    return isHazard;
  }
}
