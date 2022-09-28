import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;

public class Gun extends Actor{
	
	private long prev;
	private long prev2;
	
	private int alt;
	
	private long reload;
	
	private ImageView iv;
	
	private boolean added;
	
	private double imgWidth;
	private double imgHeight;
	
	private AudioClip sound;
	private AudioClip power;
	
	private boolean specialAbility;
	private int countSpecialHits;

	public Gun(double width, double height) {
		prev = 0;
		prev2 = 0;
		alt = 0;
		reload = 0;
		iv = new ImageView();
		iv.setImage(new Image("gun.png", width, height, true, false));
		imgWidth = width;
		imgHeight = height;
		
		iv.setRotationAxis(Rotate.Z_AXIS);
		iv.setRotate(0);
		
		sound = new AudioClip(getClass().getResource("shoot.mp3").toString());
		sound.setVolume(0.1);
		
		power = new AudioClip(getClass().getResource("power.mp3").toString());
		power.setVolume(0.1);
		
		specialAbility = false;
		
		countSpecialHits = 0;
		
		added = false;
		
	}
	
	@Override
	public void act(long now) {
		iv.setX(getWorld().getPlayer().getXPos() + getWorld().getPlayer().getImageWidth() / 2.5);
		iv.setY(getWorld().getPlayer().getYPos() + getWorld().getPlayer().getImageHeight() / 1.75);
		if(!added) {
			getWorld().getChildren().add(iv);
		}
		added = true;
		if(getWorld().isKeyDown(KeyCode.LEFT)) {
			if(315 <= iv.getRotate() && iv.getRotate() < 360) {
				iv.setRotate(180 + (360 - iv.getRotate()));
			}else if(0 <= iv.getRotate() && iv.getRotate() <= 45){
				iv.setRotate(180 - iv.getRotate());
			}	
		}
		if(getWorld().isKeyDown(KeyCode.RIGHT)) {
			if(180 <= iv.getRotate() && iv.getRotate() <= 225) {
				iv.setRotate(360 - (iv.getRotate() - 180));
			}else if(135 <= iv.getRotate() && iv.getRotate() < 180){
				iv.setRotate(180 - iv.getRotate());
			}
		}
		if(iv.getRotate() >= 360) iv.setRotate(iv.getRotate() - 360);
		if(iv.getRotate() < 0) iv.setRotate(360 + iv.getRotate());
		if(getWorld().isKeyDown(KeyCode.W)) {
			if(getWorld().getPlayer().getRot() == 0 && ((315 < iv.getRotate() && iv.getRotate() <= 359) || (0 <= iv.getRotate() && iv.getRotate() <= 45))) {
				iv.setRotate(iv.getRotate() - 3);
				if(iv.getRotate() < 0) iv.setRotate(360 + iv.getRotate());
			}else if(getWorld().getPlayer().getRot() == 180 && 135 <= iv.getRotate() && iv.getRotate() < 225){
				iv.setRotate(iv.getRotate() + 3);
				if(iv.getRotate() >= 360) iv.setRotate(iv.getRotate() - 360);
			}
		}
		if(getWorld().isKeyDown(KeyCode.S)) {
			if(getWorld().getPlayer().getRot() == 0 && ((315 <= iv.getRotate() && iv.getRotate() <= 359) || (0 <= iv.getRotate() && iv.getRotate() < 45))) {
				iv.setRotate(iv.getRotate() + 3);
				if(iv.getRotate() >= 360) iv.setRotate(iv.getRotate() - 360);
			}else if(getWorld().getPlayer().getRot() == 180 && 135 < iv.getRotate() && iv.getRotate() <= 225){
				iv.setRotate(iv.getRotate() - 3);
				if(iv.getRotate() < 0) iv.setRotate(360 + iv.getRotate());
			}
		}
		if(now - prev >= 1e9 / 2) {
			alt++;
			if(alt % 2 == 1) {
				iv.setY(iv.getY() + 1);
			}else {
				iv.setY(iv.getY() - 1);
			}
			prev = now;
		}
		int mult = 1;
		if(specialAbility) {
			mult = 3;
		}
		if(getWorld().isKeyDown(KeyCode.SPACE) && getWorld().getPlayer().getHealth() > 0 && now - reload >= 1e9 * 3 / 4 / mult) {
			getWorld().getChildren().add(new Bullet(this));
			sound.play();
			countSpecialHits++;
			if(countSpecialHits >= 5) {
				specialAbility = false;
			}
			reload = now;
		}
		if(getWorld().isKeyDown(KeyCode.SHIFT) && now - prev2 >= 1e9 * 5) {
			specialAbility = true;
			power.play();
			countSpecialHits = 0;
			prev2 = now;
		}
	}

	public double getImageWidth() {
		return imgWidth;
	}
	
	public double getImageHeight() {
		return imgHeight;
	}

	public double getXPos() {
		return iv.getX();
	}
	
	public double getYPos() {
		return iv.getY();
	}
	
	public double getGunRotation() {
		return iv.getRotate();
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}
}