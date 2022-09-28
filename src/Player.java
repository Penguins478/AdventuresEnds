
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;

public class Player extends Actor{

	private int nextImage;
	
	private long prev;
	
	private ImageView iv;
	
	private boolean added;
	
	private double xPos;
	private double yPos;
	
	private double imgWidth;
	private double imgHeight;
	
	private double speed;
	
	private int health;
	
	private ArrayList<State> stateSequence;
	private int idx;
	
	private boolean display;
	
	private MediaPlayer mp;
	
	public Player(double x, double y, double width, double height) {
		prev = 0;
		nextImage = 0;
		Image img = new Image("char.png", width, height, false, false);
		imgWidth = width;
		imgHeight = height;
		iv = new ImageView();
		iv.setImage(img);
		iv.setRotationAxis(Rotate.Y_AXIS);
		xPos = x;
		yPos = y;
		iv.setX(x);
		iv.setY(y);
		speed = 4;
		health = 3;
		added = false;
		
	}
	
	public Player(double x, double y, double width, double height, boolean display) {
		prev = 0;
		nextImage = 0;
		Image img = new Image("char.png", width, height, false, false);
		imgWidth = width;
		imgHeight = height;
		iv = new ImageView();
		iv.setImage(img);
		iv.setRotationAxis(Rotate.Y_AXIS);
		xPos = x;
		yPos = y;
		iv.setX(x);
		iv.setY(y);
		speed = 3;
		health = 3;
		added = false;
		this.display = display;
	}
	
	@Override
	public void act(long now) {
		if(!added && !display) {
			
			getWorld().getChildren().add(iv);
			added = true;
			stateSequence = new ArrayList<State>();
			idx = 0;
			stateSequence.add(new MinionState(getWorld(), 2, this));
			stateSequence.add(new MinionState(getWorld(), 3, this));
			stateSequence.add(new MinionState(getWorld(), 4, this));
			stateSequence.add(new MinionState(getWorld(), 5, this));
			stateSequence.add(new MinionState(getWorld(), 6, this));
			stateSequence.add(new PuzzleState(getWorld(), this));
			stateSequence.add(new BossState(getWorld(), this));
			
			mp = new MediaPlayer(new Media(getClass().getClassLoader().getResource("bgm.mp3").toString()));
			mp.setCycleCount(Integer.MAX_VALUE);
			mp.setVolume(0.1);
			mp.play();
			
		}else if(!added && display) {
			getWorld().getChildren().add(iv);
			added = true;
		}
		
		if(getHealth() > 0) {
			if(now - prev >= 1e9 / 2) {
				nextImage++;
				if(nextImage % 2 == 1) {
					iv.setImage(new Image("char.png", imgWidth, imgHeight + 1, false, false));
				}else {
					iv.setImage(new Image("char.png", imgWidth, imgHeight - 1, false, false));
				}
				prev = now;
			}
			
			if(getWorld().isKeyDown(KeyCode.LEFT) && getWorld().isKeyDown(KeyCode.UP) && iv.getX() > Wall.sideLength && iv.getY() > Wall.sideLength) {
				iv.setRotate(180);
				iv.setX(iv.getX() - (speed / Math.sqrt(2)));
				iv.setY(iv.getY() - (speed / Math.sqrt(2)));
				xPos -= (speed / Math.sqrt(2));
				yPos -= (speed / Math.sqrt(2));
			}else if(getWorld().isKeyDown(KeyCode.RIGHT) && getWorld().isKeyDown(KeyCode.UP) && iv.getX() + iv.getImage().getWidth() < getWorld().getWidth() - Wall.sideLength && iv.getY() > Wall.sideLength) {
				iv.setRotate(0);
				iv.setX(iv.getX() + (speed / Math.sqrt(2)));
				iv.setY(iv.getY() - (speed / Math.sqrt(2)));
				xPos += (speed / Math.sqrt(2));
				yPos -= (speed / Math.sqrt(2));
			}else if(getWorld().isKeyDown(KeyCode.RIGHT) && getWorld().isKeyDown(KeyCode.DOWN) && iv.getX() + iv.getImage().getWidth() < getWorld().getWidth() - Wall.sideLength && iv.getY() + iv.getImage().getHeight() < getWorld().getHeight() - Wall.sideLength) {
				iv.setRotate(0);
				iv.setX(iv.getX() + (speed / Math.sqrt(2)));
				iv.setY(iv.getY() + (speed / Math.sqrt(2)));
				xPos += (speed / Math.sqrt(2));
				yPos += (speed / Math.sqrt(2));
			}else if(getWorld().isKeyDown(KeyCode.LEFT) && getWorld().isKeyDown(KeyCode.DOWN) && iv.getX() > Wall.sideLength && iv.getY() + iv.getImage().getHeight() < getWorld().getHeight() - Wall.sideLength) {
				iv.setRotate(180);
				iv.setX(iv.getX() - (speed / Math.sqrt(2)));
				iv.setY(iv.getY() + (speed / Math.sqrt(2)));
				xPos -= (speed / Math.sqrt(2));
				yPos += (speed / Math.sqrt(2));
			}else if(getWorld().isKeyDown(KeyCode.LEFT) && iv.getX() > Wall.sideLength) {
				iv.setRotate(180);
				iv.setX(iv.getX() - speed);
				xPos -= speed;
			}else if(getWorld().isKeyDown(KeyCode.RIGHT) && iv.getX() + iv.getImage().getWidth() < getWorld().getWidth() - Wall.sideLength) {
				iv.setRotate(0);
				iv.setX(iv.getX() + speed);
				xPos += speed;
			}else if(getWorld().isKeyDown(KeyCode.UP) && iv.getY() > Wall.sideLength) {
				iv.setY(iv.getY() - speed);
				yPos -= speed;
			}else if(getWorld().isKeyDown(KeyCode.DOWN) && iv.getY() + iv.getImage().getHeight() < getWorld().getHeight() - Wall.sideLength) {
				iv.setY(iv.getY() + speed);
				yPos += speed;
			}
			
			if(getOneIntersectingObject(Hole.class) != null && !getOneIntersectingObject(Hole.class).isFake() && getWorld().isKeyDown(KeyCode.DOWN)) {
				if(idx < stateSequence.size()) {
					getWorld().setState(stateSequence.get(idx));
					idx++;
				}else {
					getWorld().setState(new GameOverState(getWorld(), true));
				}
			}
			
			if(getOneIntersectingObject(AcidTrap.class) != null) {
				setHealth(getHealth() - 1);
				getWorld().getChildren().remove(getOneIntersectingObject(AcidTrap.class));
			}
		}else{
			if(iv.getOpacity() > 0) {
				iv.setOpacity(iv.getOpacity() - 0.01);
			}else {
				mp.stop();
				getWorld().setState(new GameOverState(getWorld(), false));
			}
		}
	}
	
	public void setXPos(double x) {
		iv.setX(x);
		xPos = x;
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public void setYPos(double y) {
		iv.setY(y);
		yPos = y;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public double getImageWidth() {
		return imgWidth;
	}
	
	public double getImageHeight() {
		return imgHeight;
	}
	
	public double getRot() {
		return iv.getRotate();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public ImageView getImageView() {
		return iv;
	}
}
