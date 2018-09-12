import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.IOException;

public class World {
  private Level level;
  private int currLevel;

  // Perform initialisation logic
  public World() throws SlickException {
    currLevel = 1;
    try {
      level = new Level(currLevel + ".lvl");
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1039);
    }
  }

  // Update all of the sprites in the game
  public void update(Input input, int delta) throws SlickException {
    level.update(input, delta);
    if (level.isComplete()) {
      ++currLevel;
      try {
        level = new Level(currLevel + ".lvl");
      } catch (IOException e) {
        System.err.println("Game complete");
        System.exit(0);
      }
    }
  }

  // Draw all of the sprites in the game
  public void render() {
    level.render();
  }
}
