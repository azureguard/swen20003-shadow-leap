import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Level {
  private static final String[] WATER_OBSTACLES = new String[]{"turtle", "log", "longLog"};

  private ArrayList<Tile> tiles;
  private ArrayList<Obstacle> obstacles;
  private Player player;

  public Level(String lvl) throws SlickException, IOException {
    player = new Player();
    tiles = new ArrayList<>();
    obstacles = new ArrayList<>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader("assets/levels/" + lvl))) {
      while ((line = br.readLine()) != null) {
        String[] props = line.split(",");
        float xPos = Float.parseFloat(props[1]);
        float yPos = Float.parseFloat(props[2]);
        if (props.length != 4) {
          tiles.add(new Tile(props[0], xPos, yPos));
        } else {
          boolean direction = Boolean.parseBoolean(props[3]);
          if (Arrays.asList(WATER_OBSTACLES).contains(props[0])) {
            switch (props[0]) {
              case "turtle":
                obstacles.add(new Turtle(props[0], xPos, yPos, direction));
                break;
              default:
                obstacles.add(new WaterObstacle(props[0], xPos, yPos, direction));
            }
          } else {
            switch (props[0]) {
              case "bike":
                obstacles.add(new Bike(props[0], xPos, yPos, direction));
                break;
              case "bulldozer":
                obstacles.add(new Bulldozer(props[0], xPos, yPos, direction));
                break;
              default:
                obstacles.add(new Obstacle(props[0], xPos, yPos, direction));
            }
          }
        }
      }
    }
  }

  public boolean isComplete() {
    return false;
  }

  public void update(Input input, int delta) {

    for (Tile tile : tiles) {
      if (tile.isHazard()) {
        tile.contactHazard(player);
      }
    }

    for (Obstacle obstacle : obstacles) {
      obstacle.update(delta);
      if (obstacle.isHazard()) {
        obstacle.contactHazard(player);
      }
    }

    player.update(input);
  }

  public void render() {
    for (Tile tile : tiles) {
      tile.render();
    }
    for (Obstacle obstacle : obstacles) {
      obstacle.render();
    }
    player.render();
  }
}
