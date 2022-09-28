import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;

public class Gunner extends Enemy{
	
	private ImageView iv;

	private boolean added;
	
	private boolean isHit;
	
	private double sideLength;
	
	private double speed = 0.05;
	
	private long prev;
	
	private AudioClip sound;
	
	private final long wait = (long) 1e8;
	
	public Gunner(double x, double y, int level) {
		sound = null;
		sideLength = 50;
		iv = new ImageView();
		iv.setImage(new Image("gunner.png", sideLength, sideLength, true, false));
		iv.setRotationAxis(Rotate.Y_AXIS);
		iv.setX(x);
		iv.setY(y);
		setX(x);
		setY(y);
		added = false;
		isHit = false;
		setSpeed(speed + (level * 0.1));
		prev = 0;
	}
	
	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
			prev += (now + wait);
		}
		if(!isHit) {
			Player p = getWorld().getPlayer();
			if(getOneIntersectingObject(Player.class) != null) {
				getWorld().getPlayer().setHealth(getWorld().getPlayer().getHealth() - 1);
				p.setSpeed(p.getSpeed() * -1);
				isHit = true;
			}else if(getOneIntersectingObject(Bullet.class) != null){
				isHit = true;
				getOneIntersectingObject(Bullet.class).setIsHit(true);
				getOneIntersectingObject(Bullet.class).getImageView().setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else {
				super.act(now);
				iv.setX(getX());
				iv.setY(getY());
				double angle = Math.toDegrees(Math.atan2(getYDist(), getXDist()));
				if(angle >= 360) angle -= 360;
				if(angle < 0) angle += 360;
				if(now - prev >= 1e9 * 3 / 2 &&  ((Math.abs(angle - 180)) <= 20 || ((0 <= angle && angle <= 20) || (340 <= angle && angle < 360)))) {
					getWorld().getChildren().add(new EnemyBullet(this));
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
				if(sound != null) {
					sound.play();
				}
				super.dropPower();
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
	
	public void setSound(boolean b) {
		if(b) {
			sound = new AudioClip(getClass().getResource("unique.mp3").toString());
			sound.setVolume(0.5);
		}
	}
}
