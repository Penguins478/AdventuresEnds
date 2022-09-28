import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomingBullet extends Enemy implements Impact{

	private ImageView iv;
	
	private boolean added;
	
	private boolean isHit;
	
	private double sideLength;
	
	private final double speed = 3.5;
	
	private double distance;
	
	private Robot robot;
	
	public HomingBullet(Robot r) {
		sideLength = 10;
		iv = new ImageView();
		iv.setImage(new Image("enemy_bullet.png", sideLength, sideLength, true, false));
		if(r.getImageView().getRotate() == 0) {
			iv.setX(r.getImageView().getX() + r.getImageView().getImage().getWidth());
		}else {
			iv.setX(r.getImageView().getX());
		}
		iv.setY(r.getImageView().getY() + r.getImageView().getImage().getHeight() / 2);
		setX(iv.getX());
		setY(iv.getY());
		added = false;
		isHit = false;
		robot = r;
		setSpeed(speed);
		distance = 0;
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
			}else if(distance >= 1200){
				isHit = true;
				robot.getBullets().remove(this);
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else {
				super.act(now);
				iv.setX(getX());
				iv.setY(getY());
				distance += speed;
			}
		}else {	
			impactExplode(1);
		}
		
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
			getWorld().getChildren().removeAll(iv, this);
		}
	}
	
}
