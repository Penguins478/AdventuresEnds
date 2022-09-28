import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AcidTrap extends Actor{

	private ImageView iv;
	
	private boolean maxed;
	
	private double sideLength;
	
	private boolean added;
	
	public AcidTrap(double x, double y) {
		sideLength = 30;
		iv = new ImageView();
		iv.setImage(new Image("acid_trap.png", sideLength, sideLength, false, true));
		iv.setX(x);
		iv.setY(y);
		iv.setOpacity(0);
		maxed = false;
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
		if(maxed) {
			iv.setOpacity(iv.getOpacity() - 0.01);
		}else {
			iv.setOpacity(iv.getOpacity() + 0.01);
			if(iv.getOpacity() >= 1) {
				maxed = true;
			}
		}
		if(iv.getOpacity() <= 0) {
			getWorld().getChildren().remove(this);
		}
	}
	
}
