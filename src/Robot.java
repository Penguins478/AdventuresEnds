import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class Robot extends Enemy{
	
	private ImageView iv;
	
	private boolean added;
	
	private double speed;
	
	private boolean isHit;
	
	private long prev;
	
	private ArrayList<HomingBullet> bullets;
	
	private int health;
	
	public Robot(double x, double y) {
		iv = new ImageView();
		iv.setImage(new Image("boss1.png", 50, 55, true, false));
		iv.setX(x);
		iv.setY(y);
		setX(x);
		setY(y);
		iv.setRotationAxis(Rotate.Y_AXIS);
		speed = 2;
		prev = 0;
		isHit = false;
		added = false;
		bullets = new ArrayList<HomingBullet>();
		health = 10;
	}
	
	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
		}
		if(!isHit) {
			if(getOneIntersectingObject(Player.class) != null) {
				getWorld().getPlayer().setHealth(0);
			}else if(getOneIntersectingObject(Bullet.class) != null && !getOneIntersectingObject(Bullet.class).getIsHit()){
				Bullet b = getOneIntersectingObject(Bullet.class);
				b.setIsHit(true);
				b.getImageView().setImage(new Image("explosion.png", 50, 50, true, false));
				health--;
				if(health <= 0) {
					isHit = true;
				}
			}else {
				super.act(now);
				iv.setX(getX());
				iv.setY(getY());
				if(bullets.size() < 10 && now - prev >= 1e10 / 5) {
					HomingBullet hb = new HomingBullet(this);
					getWorld().getChildren().add(hb);
					bullets.add(hb);
					prev = now;
				}
			}
		}else {	
			if(iv.getOpacity() > 0) {
				iv.setOpacity(iv.getOpacity() - 0.02);
				if(iv.getOpacity() < 0.95 && getWorld().getPlayer().getSpeed() < 0) {
					getWorld().getPlayer().setSpeed(getWorld().getPlayer().getSpeed() * -1);
				}
			}else {
				getWorld().getChildren().removeAll(iv, this);
			}
		}
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double s) {
		speed = s;
	}
	
	public ArrayList<HomingBullet> getBullets(){
		return bullets;
	}
}
