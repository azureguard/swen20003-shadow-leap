import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * The Level object which runs the logic of the game and instantiates all the
 * objects given in a .lvl comma-separated file.
 */
public class Level {
  private static final String[] WATER_OBSTACLES = new String[]{"turtle", "log", "longlog"};
  private static final int LIVES_INIT_X = 24;
  private static final int LIVES_INIT_Y = 744;

  private static final Random random = new Random();

  private ArrayList<Goal> goals;
  private ArrayList<Tile> tiles;
  private ArrayList<Obstacle> obstacles;
  private ArrayList<WaterObstacle> logs;
  private Player player;
  private Image lives;
  private Bonus extraLife;
  private int bonusTrigger;

  private int time = 0;

  /**
   * Instantiates a new Level.
   *
   * @param level  The level to be loaded
   * @param player The Player object to be placed in the level
   * @throws SlickException Indicates a failure to load an image asset
   * @throws IOException    Indicates the level file cannot be found
   */
  public Level(String level, Player player) throws SlickException, IOException {
    goals = new ArrayList<>();
    tiles = new ArrayList<>();
    obstacles = new ArrayList<>();
    logs = new ArrayList<>();
    this.player = player;
    lives = new Image("assets/lives.png");
    bonusTrigger = (random.nextInt(5) + 5) * 1000;

    String line;
    try (BufferedReader br = new BufferedReader(new FileReader("assets/levels/" + level))) {
      // Set up goal detection variable
      float lastXPos = 0;
      // Load the level line by line
      while ((line = br.readLine()) != null) {
        // Split lines into individual properties
        String[] props = line.toLowerCase().split(",");
        float xPos = Float.parseFloat(props[1]);
        float yPos = Float.parseFloat(props[2]);
        // Add goals by detection of spaces between tree tiles
        if (yPos == Goal.GOAL_LINE) {
          if (xPos - lastXPos == Goal.GOAL_WIDTH) {
            goals.add(new Goal(xPos - Goal.GOAL_WIDTH / 2, yPos));
          }
          lastXPos = xPos;
        }
        // Tile reached
        if (props.length != 4) {
          tiles.add(new Tile(props[0], xPos, yPos));
        } else {
          // Obstacle reached, get initial direction
          boolean direction = Boolean.parseBoolean(props[3]);
          // Handle special behaviour for turtles
          if (Arrays.asList(WATER_OBSTACLES).contains(props[0])) {
            switch (props[0]) {
              case "turtle":
                obstacles.add(new Turtle(props[0], xPos, yPos, direction));
                break;
              default:
                WaterObstacle log = new WaterObstacle(props[0], xPos, yPos, direction);
                obstacles.add(log);
                logs.add(log);
            }
          } else {
            // Handle special behaviour for bikes and bulldozers
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

  /**
   * Check if the level has been completed.
   *
   * @return True if the level has been completed
   */
  public boolean isComplete() {
    for (Goal goal : goals) {
      if (!goal.isAchieved()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Runs the logic of the game. Allows movement of all objects in the Level.
   *
   * @param input Input from interface devices
   * @param delta The time taken to render the last frame
   * @throws SlickException Indicates a failure to load an image asset
   */
  public void update(Input input, int delta) throws SlickException {
    // Time keeping for extra life object logic
    time += delta;

    // Triggers on and off the extra life Bonus object and selects a log to
    // place the Bonus
    if (extraLife == null && time > bonusTrigger) {
      WaterObstacle log = logs.get(random.nextInt(logs.size() - 1));
      extraLife = new Bonus("extralife", log);
    } else if (time > (bonusTrigger + 14000)) {
      extraLife = null;
      time = 0;
    } else if (extraLife != null) {
      extraLife.update(delta);
      // Grant the Player an extra life when reached
      if (player.getBoundingBox().intersects(extraLife.getBoundingBox())) {
        player.onBonus();
        time = 0;
        extraLife = null;
      }
    }

    // Checks all goals to be completed and takes a life if attempted again
    for (Goal goal : goals) {
      if (goal.checkAchieved(player)) {
        player.reset();
      } else if (goal.isAchieved()) {
        goal.contactHazard(player);
      }
    }

    // Allow player movement
    player.update(input, delta, obstacles, tiles);

    // For all obstacles, run their update logic
    for (Obstacle obstacle : obstacles) {
      obstacle.update(delta);
      // For rideables check if Player can ride and does so
      if (obstacle instanceof Rideable) {
        if (!player.isRiding()) {
          ((Rideable) obstacle).ride(player);
        }
      } else if (obstacle.isHazard()) {
        // Take the life of the Player if a hazard is contacted
        if (obstacle.contactHazard(player)) {
          break;
        }
      }
    }

    // Check all tiles
    for (Tile tile : tiles) {
      if (tile.isHazard()) {
        // Takes the life of the player if not riding a Rideable on water
        if (!player.isRiding() && tile.contactHazard(player)) {
          break;
        }
      }
    }
  }

  /**
   * Render the game world
   */
  public void render() {
    for (Goal goal : goals) {
      goal.render();
    }
    for (Tile tile : tiles) {
      tile.render();
    }
    for (Obstacle obstacle : obstacles) {
      obstacle.render();
    }
    if (extraLife != null) {
      extraLife.render();
    }
    player.render();
    // Show player lives
    for (int i = 0; i < player.getLives(); ++i) {
      lives.drawCentered(LIVES_INIT_X + i * 32, LIVES_INIT_Y);
    }
  }

  /**
   * Gets the Player object.
   *
   * @return the Player object
   */
  public Player getPlayer() {
    return player;
  }
}
