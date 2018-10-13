import org.newdawn.slick.SlickException;

/**
 * The Tile object for all background tiles in the game world.
 */
public class Tile extends Sprite {
  private final boolean isHazard;
  private final boolean isSolid;

  // Enumerate for all tiles their hazard and solid properties.
  private enum TILE {
    /**
     * Water tile constants.
     */
    WATER(true, false),
    /**
     * Grass tile constants.
     */
    GRASS(false, false),
    /**
     * Tree tile constants.
     */
    TREE(false, true);

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
   * @param imageSrc The name of the PNG in assets/ without the extension
   * @param x        The x position on creation
   * @param y        The y position on creation
   * @throws SlickException Indicates a failure to load an image asset
   */
  public Tile(String imageSrc, float x, float y) throws SlickException {
    super(imageSrc, x, y);
    this.isHazard = TILE.valueOf(imageSrc.toUpperCase()).isHazard;
    this.isSolid = TILE.valueOf(imageSrc.toUpperCase()).isSolid;
  }

  /**
   * Check if a Tile is solid.
   *
   * @return True if the Tile is solid.
   */
  public boolean isSolid() {
    return isSolid;
  }

  /**
   * Check if a Tile is hazardous.
   *
   * @return True if the Tile is hazardous.
   */
  public boolean isHazard() {
    return isHazard;
  }
}
