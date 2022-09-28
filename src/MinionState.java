import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class MinionState extends State{
	
	private int level;
	private Player player;
	
	private boolean newState;
	
	private ArrayList<Enemy> enemies;
	private int enemyIdx;
	
	public MinionState(World w) {
		super(w);
		level = 1;
		player = new Player(Wall.sideLength * 2, getWorld().getHeight() / 2 - 28, 50, 50);
		newState = true;
		enemies = new ArrayList<Enemy>();
		enemyIdx = 0;
	}
	
	public MinionState(World w, int level, Player p) {
		super(w);
		this.level = level;
		player = p;
		newState = false;
		enemies = new ArrayList<Enemy>();
	}
	
	@Override
	public void onSet() {
		
		getWorld().setStyle("-fx-background-color: dimgray");
		
		for(int i = 0; i < getWorld().getWidth() / Wall.sideLength; i++) {
			getWorld().getChildren().add(new Wall(Wall.sideLength * i, 0));
			getWorld().getChildren().add(new Wall(Wall.sideLength * i, getWorld().getHeight() - Wall.sideLength));
		}
		for(int j = 0; j < getWorld().getHeight() / Wall.sideLength - 2; j++) {
			getWorld().getChildren().add(new Wall(0, Wall.sideLength + Wall.sideLength * j));
			getWorld().getChildren().add(new Wall(getWorld().getWidth() - Wall.sideLength, Wall.sideLength + Wall.sideLength * j));
		}
		
		PlayerHealth bar = new PlayerHealth();
		getWorld().getChildren().add(bar);
		
		if(newState) {
			getWorld().getChildren().add(player);
		}else {
			getWorld().getChildren().add(player);
			getWorld().getChildren().add(player.getImageView());
		}

		player.setXPos(Wall.sideLength + getWorld().getPlayer().getImageWidth());
		player.setYPos((getWorld().getHeight() / 2) - (getWorld().getPlayer().getImageHeight() / 2));
		
		Gun g = new Gun(40, 30);
		
		getWorld().getChildren().add(g);
		
		for(int x = 0; x < level + 1; x++) {
			Enemy enemy;
			int rand = ((int)(Math.random() * 2));
			if(rand == 0) {
				enemy = new Bomber((Math.random() * ((getWorld().getWidth() / 2) - Wall.sideLength) - 50) + (getWorld().getWidth() / 2), (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - 50)) + Wall.sideLength);
			}else {
				enemy = new Gunner((Math.random() * ((getWorld().getWidth() / 2) - Wall.sideLength) - 50) + (getWorld().getWidth() / 2), (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - 50)) + Wall.sideLength, level);
			}
			enemies.add(enemy);
		}
		
		int amountToAdd;
		
		if(enemies.size() > 2) {
			amountToAdd = 3;
		}else {
			amountToAdd = enemies.size();
		}
		
		for(int y = 0; y < amountToAdd; y++) {
			getWorld().getChildren().add(enemies.get(y));
			enemyIdx++;
		}
		
		getWorld().start();
		getWorld().requestFocus();
		
		getWorld().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				getWorld().addKeyCode(event.getCode());
			}});
		
		getWorld().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				getWorld().removeKeyCode(event.getCode());
			}});
		
		
	}

	@Override
	public void onRemove() {
		getWorld().getChildren().removeAll(getWorld().getChildren());
	}

	@Override
	public void onAct(long now) {
		if(enemyIdx < enemies.size() && getWorld().getObjects(Gunner.class).size() + getWorld().getObjects(Bomber.class).size() == 0) {
			int count = 0;
			while(count < 3 && enemyIdx < enemies.size()) {
				getWorld().getChildren().add(enemies.get(enemyIdx));
				enemyIdx++;
				count++;
			}
		}
		if(getWorld().getObjects(Hole.class).size() == 0 && getWorld().getObjects(Bomber.class).size() == 0 && getWorld().getObjects(Gunner.class).size() == 0) {
			int randX = (int) (Math.random() * (getWorld().getWidth() - (Wall.sideLength * 2) - Hole.sideLength) + Wall.sideLength);
			int randY = (int) (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - Hole.sideLength) + Wall.sideLength);
			getWorld().getChildren().add(new Hole(randX, randY));
		}
	}

}
