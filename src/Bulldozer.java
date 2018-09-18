import org.newdawn.slick.SlickException;

public class Bulldozer extends Obstacle implements Rideable {
  public Bulldozer(String imageSrc, float x, float y, boolean directionRight) throws SlickException {
    super(imageSrc, x, y, directionRight);
  }

  @Override
  public void update(int delta) {
    super.update(delta);
  }

  public void ride(Player player) {
    if (getBoundingBox() != null && getBoundingBox().intersects(player.getBoundingBox())) {
      player.embark(this);
    }
  }
}
