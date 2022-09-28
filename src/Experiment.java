
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class Experiment extends Enemy{

	private ImageView iv;
	
	private boolean added;
	
	private double speed;
	
	private boolean isHit;
	
	private long prev;
	
	private int health;
	
	private int numBullets;
	
	public Experiment(double x, double y) {
		iv = new ImageView();
		iv.setImage(new Image("boss2.png", 50, 55, true, false));
		iv.setX(x);
		iv.setY(y);
		setX(x);
		setY(y);
		iv.setRotationAxis(Rotate.Y_AXIS);
		speed = 1.5;
		prev = 0;
		isHit = false;
		added = false;
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
				if(now - prev >= 1e9 * 3 / 4 && getWorld().getObjects(HomingBullet.class).size() + getWorld().getObjects(EnemyBullet.class).size() < 5) {
					if(iv.getRotate() == 0) {
						getWorld().getChildren().addAll(new EnemyBullet(this, 330, 2), new EnemyBullet(this, 0, 2), new EnemyBullet(this, 30, 2));
					}else {
						getWorld().getChildren().addAll(new EnemyBullet(this, 150, 2), new EnemyBullet(this, 180, 2), new EnemyBullet(this, 210, 2));
					}
					numBullets += 3;
					if(getWorld().getObjects(AcidTrap.class).size() < 5) {
						int chance = (int)(Math.random() * 100);
						if(chance < 100) getWorld().getChildren().add(new AcidTrap(iv.getX(), iv.getY()));
					}
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
	
}
