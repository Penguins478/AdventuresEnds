import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyBullet extends Actor implements Impact{

	private ImageView iv;
	
	private double sideLength;
	
	private double dx;
	private double dy;
	
	private boolean added;
	
	private boolean bounce;
	
	private boolean isHit;
	
	public EnemyBullet(Enemy g) {
		sideLength = 10;
		iv = new ImageView();
		iv.setImage(new Image("enemy_bullet.png", sideLength, sideLength, false, false));
		if(g.getImageView().getRotate() == 0) {
			iv.setX(g.getImageView().getX() + g.getImageView().getImage().getWidth());
		}else {
			iv.setX(g.getImageView().getX());
		}
		iv.setY(g.getImageView().getY() + g.getImageView().getImage().getHeight() / 2);
		dx = g.getXDist() * 10;
		dy = g.getYDist() * 10;
		bounce = false;
		isHit = false;
		added = false;
	}
	
	public EnemyBullet(Enemy g, double angle, double speed) {
		sideLength = 10;
		iv = new ImageView();
		iv.setImage(new Image("enemy_bullet.png", sideLength, sideLength, false, false));
		if(g.getImageView().getRotate() == 0) {
			iv.setX(g.getImageView().getX() + g.getImageView().getImage().getWidth());
		}else {
			iv.setX(g.getImageView().getX());
		}
		iv.setY(g.getImageView().getY() + g.getImageView().getImage().getHeight() / 2);
		dx = speed * Math.cos(angle) * 2;
		dy = speed * Math.sin(angle) * 2;
		bounce = false;
		isHit = false;
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
		if(!isHit) {
			iv.setX(iv.getX() + dx);
			iv.setY(iv.getY() + dy);
			if(getOneIntersectingObject(Wall.class) != null && !bounce) {
				Wall wall = getOneIntersectingObject(Wall.class);
				bounce = true;
	            if(wall.getImageView().getY() <= iv.getY() + iv.getImage().getHeight() / 2 && iv.getY() + iv.getImage().getHeight() / 2 <= wall.getImageView().getY() + wall.getImageView().getImage().getHeight()){
	                dx *= -1;
	            }else if(wall.getImageView().getX() <= iv.getX() + iv.getImage().getWidth() / 2 && iv.getX() + iv.getImage().getWidth() / 2 <= wall.getImageView().getX() + wall.getImageView().getImage().getWidth()){
	                dy *= -1;
	            }else {
	            	dx *= -1;
	            	dy *= -1;
	            }
			}else if(getOneIntersectingObject(Wall.class) != null && bounce) {
				isHit = true;
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}else if(getOneIntersectingObject(Player.class) != null) {
				getWorld().getPlayer().setHealth(getWorld().getPlayer().getHealth() - 1);
				isHit = true;
				iv.setImage(new Image("explosion.png", sideLength, sideLength, true, false));
			}
		}else {
			impactExplode(1);
		}
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
