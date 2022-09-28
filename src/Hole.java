import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hole extends Actor{
	
	private ImageView iv;
	
	private boolean added;
	
	public static final double sideLength = 30;
	
	private boolean fake;
	
	private long prev;
	
	private Image holeImage;
	private Image trapImage;
	
	public Hole(double x, double y) {
		iv = new ImageView();
		holeImage = new Image("hole.png", sideLength, sideLength, false, false);
		trapImage = new Image("acid_trap.png", sideLength, sideLength, false, true);
		iv.setImage(holeImage);
		iv.setX(x);
		iv.setY(y);
		setX(x);
		setY(y);
		prev = 0;
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
		if(fake && getOneIntersectingObject(Player.class) != null && now - prev >= 1e9) {
			iv.setImage(trapImage);
			getWorld().getPlayer().setHealth(getWorld().getPlayer().getHealth() - 1);
			prev = now;
		}else if(now - prev >= 1e9 && !fake){
			iv.setImage(holeImage);
		}
	}

	public boolean isFake() {
		return fake;
	}
	
	public void setIsFake(boolean b) {
		fake = b;
	}
}
