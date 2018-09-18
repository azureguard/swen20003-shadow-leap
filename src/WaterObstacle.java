import org.newdawn.slick.SlickException;

public class WaterObstacle extends Obstacle implements Rideable {
  public WaterObstacle(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
  }

  @Override
  public void ride(Player player) {
    if (getBoundingBox() != null && getBoundingBox().intersects(player.getBoundingBox())) {
      player.embark(this);
    }
  }
}
