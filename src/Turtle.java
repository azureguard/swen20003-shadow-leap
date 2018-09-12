import org.newdawn.slick.SlickException;

public class Turtle extends WaterObstacle {
  private int time;

  public Turtle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
    time = 0;
  }

  @Override
  public void update(int delta) {
    super.update(delta);
    time += delta;
    //    System.err.println(time);
    if (time > 9000) {
      // appear
      enable();
      time = 0;
    } else if (time > 7000) {
      disable();
      // disappear
    } else {
      enable();
    }
  }
}
