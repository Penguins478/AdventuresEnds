import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class SpeedIncrease extends Actor{
	
	private ImageView iv;
	
	private boolean added;
	
	public SpeedIncrease(double x, double y) {
		iv = new ImageView();
		iv.setImage(new Image("spdup.png", 35, 35, true, false));
		iv.setX(x);
		iv.setY(y);
		added = false;
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}

	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
		}
		if(getOneIntersectingObject(Player.class) != null && getWorld().getPlayer().getSpeed() < 7 && getWorld().isKeyDown(KeyCode.UP)) {
			getWorld().getPlayer().setSpeed(getWorld().getPlayer().getSpeed() + 1);
			getWorld().getChildren().removeAll(iv, this);
		}
	}

}
