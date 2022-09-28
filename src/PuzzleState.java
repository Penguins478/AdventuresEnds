import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class PuzzleState extends State{

	private Player player;
	
	private ArrayList<Enemy> enemies;
	
	private int enemyIdx;
	
	private int randIdx;
	
	private boolean eliminated;
	
	private boolean added;

	public PuzzleState(World w, Player p) {
		super(w);
		this.player = p;
		enemies = new ArrayList<Enemy>();
		enemies.add(new Gunner(getWorld().getWidth() - Wall.sideLength * 4, getWorld().getHeight() / 2, 2));
		enemies.add(new Gunner(getWorld().getWidth() - Wall.sideLength * 4, getWorld().getHeight() / 2, 2));
		enemies.add(new Gunner(getWorld().getWidth() - Wall.sideLength * 4, getWorld().getHeight() / 2, 2));
		enemyIdx = 0;
		randIdx = (int)(Math.random() * 3);
		((Gunner)(enemies.get(randIdx))).setSound(true);
		eliminated = false;
		added = false;
	}
	
	@Override
	public void onSet() {
		
		for(int i = 0; i < getWorld().getWidth() / Wall.sideLength; i++) {
			getWorld().getChildren().add(new Wall(Wall.sideLength * i, 0));
			getWorld().getChildren().add(new Wall(Wall.sideLength * i, getWorld().getHeight() - Wall.sideLength));
		}
		for(int j = 0; j < getWorld().getHeight() / Wall.sideLength - 2; j++) {
			getWorld().getChildren().add(new Wall(0, Wall.sideLength + Wall.sideLength * j));
			getWorld().getChildren().add(new Wall(getWorld().getWidth() - Wall.sideLength, Wall.sideLength + Wall.sideLength * j));
		}
		
		
		
		
		PlayerHealth bar = new PlayerHealth(player);
		getWorld().getChildren().add(bar);
		
		
		
		getWorld().getChildren().addAll(player.getImageView(), player);
		
		player.setXPos(Wall.sideLength + getWorld().getPlayer().getImageWidth());
		player.setYPos((getWorld().getHeight() / 2) - (getWorld().getPlayer().getImageHeight() / 2));
		

		Gun g = new Gun(40, 30);
		
		getWorld().getChildren().add(g);
		
		//
		getWorld().getChildren().addAll(enemies.get(enemyIdx), new Bomber(enemies.get(enemyIdx).getX(), (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - 50)) + Wall.sideLength));
		
		//
		
		
	}

	@Override
	public void onRemove() {
		getWorld().getChildren().removeAll(getWorld().getChildren());
		
	}

	@Override
	public void onAct(long now) {
		if(!eliminated) {
			if(!getWorld().getChildren().contains(enemies.get(enemyIdx))) {
				enemyIdx++;
				if(enemyIdx < enemies.size()) {
					getWorld().getChildren().addAll(enemies.get(enemyIdx), new Bomber(enemies.get(enemyIdx).getX(), (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - 50)) + Wall.sideLength));
				}else {
					eliminated = true;
				}	
			}
		}else if(!added){
			for(int i = 0; i < 3; i++) {
				Hole hole = new Hole(getWorld().getWidth() / 2 + (80 * i) - 80, getWorld().getHeight() / 5);
				if(i == randIdx) {
					hole.setIsFake(false);
				}else {
					hole.setIsFake(true);
				}
				getWorld().getChildren().add(hole);
				added = true;
			}
		}
	}

}
