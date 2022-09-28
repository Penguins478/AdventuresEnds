import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends Actor implements Impact{

	private double dx;
	private double dy;
	
	private double sideLength;
	
	private boolean isHit;
	
	private ImageView iv;
	private boolean added;

	public Bullet(Gun gun) {
		added = false;
		sideLength = 15;
		iv = new ImageView();
		iv.setImage(new Image("bullet.png", sideLength, sideLength, false, false)); 
		int k = 270;
		dx = gun.getXPos() - (gun.getXPos() + (gun.getImageWidth() * Math.sin(Math.toRadians(k - gun.getGunRotation()))));
		dy = gun.getYPos() - (gun.getYPos() + (gun.getImageHeight() * Math.cos(Math.toRadians(k - gun.getGunRotation()))));
		dx /= 4.5;
		dy /= 4.5;
		iv.setX(gun.getXPos() + (gun.getImageWidth() * -Math.sin(Math.toRadians(k - gun.getGunRotation()))));
		iv.setY(gun.getYPos() + (gun.getImageWidth() * -Math.cos(Math.toRadians(k - gun.getGunRotation()))));
		setX(gun.getXPos() + (gun.getImageWidth() * -Math.sin(Math.toRadians(k - gun.getGunRotation()))));
		setY(gun.getYPos() + (gun.getImageWidth() * -Math.cos(Math.toRadians(k - gun.getGunRotation()))));
		isHit = false;
	}

	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().add(iv);
			added = true;
		}
		
		if(!isHit) {
			if(this.getOneIntersectingObject(Wall.class) != null) {
				isHit = true;
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else {
				iv.setX(iv.getX() + dx);
				iv.setY(iv.getY() + dy);
				setX(iv.getX());
				setY(iv.getY());
			}
		}else {
			impactExplode(1);
		}
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}
	
	public boolean getIsHit() {
		return isHit;
	}
	
	public void setIsHit(boolean b) {
		isHit = b;
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
			getWorld().getChildren().removeAll(iv, this);
		}
	}
}
