import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class World {
  // Tile definitions
  private static final int WATER_START = 96;
  private static final int GRASS_NEXT = 672;
  private static final int WATER_ROWS = 6;
  private static final int GRASS_ROWS = 2;
  private static final int TILE_COLS = App.SCREEN_WIDTH / Sprite.SPRITE_DIM + 1;
  // Player definitions
  private static final int PLAYER_X = 512;
  private static final int PLAYER_Y = 720;
  // Bus definitions
  private static final int BUS_ROWS = 5;
  private static final int BUS_START = 432;
  private static final float OFFSET[] = {48f, 0f, 64f, 128f, 250f};
  private static final float SEPARATION[] = {6.5f, 5f, 12f, 5f, 6.5f};
  private Player player;
  private ArrayList<Bus> busArray;
  private ArrayList<Tile> tileArray;

  // Perform initialisation logic
  public World() throws SlickException {
    // Initialise sprites and array lists
    player = new Player("assets/frog.png", PLAYER_X, PLAYER_Y);
    busArray = new ArrayList<>();
    tileArray = new ArrayList<>((WATER_ROWS + GRASS_ROWS) * TILE_COLS);

    // Busses
    float x, y = BUS_START;
    int direction = -1;
    for (int row = 0; row < BUS_ROWS; ++row) {
      int newWidth = (int) Math.ceil((App.SCREEN_WIDTH - OFFSET[row]) / (SEPARATION[row] * Sprite.SPRITE_DIM));
      x = OFFSET[row];
      for (int width = 0; width < newWidth; ++width) {
        x = width > 0 ? x + (SEPARATION[row] * Sprite.SPRITE_DIM) : x;
        busArray.add(new Bus("assets/bus.png", x, y, direction));
      }
      direction *= -1;
      y += Sprite.SPRITE_DIM;
    }

    // Water tiles
    x = 0;
    y = WATER_START;
    for (int row = 0; row < WATER_ROWS; ++row) {
      for (int col = 0; col < TILE_COLS; ++col) {
        tileArray.add(new Tile("assets/water.png", x, y, true));
        x += Sprite.SPRITE_DIM;
      }
      x = 0;
      y += Sprite.SPRITE_DIM;
    }

    // Grass tiles
    for (int row = 0; row < GRASS_ROWS; ++row) {
      for (int col = 0; col < TILE_COLS; ++col) {
        tileArray.add(new Tile("assets/grass.png", x, y, false));
        x += Sprite.SPRITE_DIM;
      }
      x = 0;
      y = GRASS_NEXT;
    }
  }

  // Update all of the sprites in the game
  public void update(Input input, int delta) {

    for (Tile tile : tileArray) {
      if (tile.isDangerTile()) {
        tile.contactSprite(player);
      }
    }

    for (Bus bus : busArray) {
      bus.update(delta);
      bus.contactSprite(player);
    }

    player.update(input);
  }

  // Draw all of the sprites in the game
  public void render() {
    for (Tile tile : tileArray) {
      tile.render();
    }

    for (Bus bus : busArray) {
      bus.render();
    }

    player.render();
  }
}
