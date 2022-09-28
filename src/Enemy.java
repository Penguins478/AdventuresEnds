import javafx.scene.image.ImageView;

public class Enemy extends Actor{

	private double speed;
	
	private double xDist;
	private double yDist;

	@Override
	public void act(long now) {
		double y1 = getWorld().getPlayer().getYPos();
		double y2 = getY();
		double x1 = getWorld().getPlayer().getXPos();
		double x2 = getX();
		double angle = Math.atan2(y2 - y1, x2 - x1);
		xDist = getSpeed() * -Math.cos(angle);
		yDist = getSpeed() * -Math.sin(angle);
		if(xDist > 0) {
			getImageView().setRotate(0);
		}else {
			getImageView().setRotate(180);
		}
		setX(getX() + xDist);
		setY(getY() + yDist);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public ImageView getImageView() {
		return null;
	}
	
	public double getXDist() {
		return xDist;
	}
	
	public double getYDist() {
		return yDist;
	}
	
	public void dropPower() {
		int chance = (int) (Math.random() * 100);
		if(chance < 5) {
			getWorld().getChildren().add(new Heal(getX(), getY()));
		}else if(chance < 10) {
			getWorld().getChildren().add(new SpeedIncrease(getX(), getY()));
		}
	}
	
}
