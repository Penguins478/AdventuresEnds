import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends Actor{
	
	private ImageView iv;

	public static double sideLength;
	
	private boolean added;
	
	public Wall(double x, double y) {
		sideLength = 25;
		iv = new ImageView();
		iv.setImage(new Image("wall.png", sideLength, sideLength, true, false));
		iv.setX(x);
		iv.setY(y);
		added = false;
	}
	
	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
		}
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}

}
