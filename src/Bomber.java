import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class Bomber extends Enemy implements Impact{

	private ImageView iv;
	
	private boolean added;
	
	private boolean isHit;
	
	private double sideLength;
	
	private final double speed = 3;
	
	public Bomber(double x, double y) {
		sideLength = 50;
		iv = new ImageView();
		iv.setImage(new Image("bomber.png", sideLength, sideLength, true, false));
		iv.setRotationAxis(Rotate.Y_AXIS);
		iv.setX(x);
		iv.setY(y);
		setX(x);
		setY(y);
		added = false;
		isHit = false;
		setSpeed(speed);
	}
	
	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
		}
		if(!isHit) {
			Player p = getWorld().getPlayer();
			if(getOneIntersectingObject(Player.class) != null) {
				getWorld().getPlayer().setHealth(getWorld().getPlayer().getHealth() - 1);
				p.setSpeed(p.getSpeed() * -1);
				isHit = true;
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else if(getOneIntersectingObject(Bullet.class) != null){
				isHit = true;
				getOneIntersectingObject(Bullet.class).setIsHit(true);
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else {
				super.act(now);
				iv.setX(getX());
				iv.setY(getY());
			}
		}else {	
			impactExplode(5);
		}
		
	}

	public void setIsHit(boolean b) {
		isHit = b;
	}
	
	public boolean isHit() {
		return isHit;
	}
	
	@Override
	public ImageView getImageView() {
		return iv;
	}

	@Override
	public void impactExplode(double decrement) {
		sideLength -= decrement;
		if(sideLength > 0) {
			iv.setFitWidth(sideLength);
			iv.setFitHeight(sideLength);
			iv.setX(iv.getX() + decrement / 2);
			iv.setY(iv.getY() + decrement / 2);
		}else {
			if(getWorld().getPlayer().getSpeed() < 0) {
				getWorld().getPlayer().setSpeed(getWorld().getPlayer().getSpeed() * -1);
			}
			super.dropPower();
			getWorld().getChildren().removeAll(iv, this);
		}
	}
	
}
