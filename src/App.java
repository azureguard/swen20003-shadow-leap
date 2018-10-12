/*
  Sample Project for SWEN20003: Object Oriented Software Development 2018
  by Eleanor McMurtry, University of Melbourne
 */

import org.newdawn.slick.*;

import java.io.IOException;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame {
  /**
   * screen width, in pixels
   */
  public static final int SCREEN_WIDTH = 1024;
  /**
   * screen height, in pixels
   */
  public static final int SCREEN_HEIGHT = 768;

  private Level level;
  private int currLevel;


  /**
   * Instantiates a new App.
   */
  public App() {
    super("Shadow Leap");
  }

  /**
   * Start-up method. Creates the game and runs it.
   *
   * @param args Command-line arguments (ignored).
   * @throws SlickException the slick exception
   */
  public static void main(String[] args)
          throws SlickException {
    AppGameContainer app = new AppGameContainer(new App());
    app.setShowFPS(false);
    app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
    app.setTargetFrameRate(60);
    app.start();
  }

  @Override
  public void init(GameContainer gc) throws SlickException {
    currLevel = 0;
    try {
      level = new Level(currLevel + ".lvl", new Player());
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Initial level cannot be loaded");
      System.exit(1039);
    }
  }


  /**
   * Update the game state for a frame.
   *
   * @param gc    The Slick game container object.
   * @param delta Time passed since last frame (milliseconds).
   */
  @Override
  public void update(GameContainer gc, int delta) throws SlickException {
    // Get data about the current input (keyboard state).
    Input input = gc.getInput();
    level.update(input, delta);
    if (level.isComplete()) {
      ++currLevel;
      try {
        level = new Level(currLevel + ".lvl", level.getPlayer());
      } catch (IOException e) {
        gc.exit();
      }
    }
  }

  /**
   * Render the entire screen, so it reflects the current game state.
   *
   * @param gc The Slick game container object.
   * @param g  The Slick graphics object, used for drawing.
   */
  public void render(GameContainer gc, Graphics g) {
    level.render();
  }

}
