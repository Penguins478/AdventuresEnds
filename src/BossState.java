

public class BossState extends State{

	private Player player;
	
	private boolean robotAdded;
	private boolean experimentAdded;
	
	public BossState(World w, Player p) {
		super(w);
		this.player = p;
		robotAdded = false;
		experimentAdded = false;
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
		
		Robot boss1 = new Robot(getWorld().getWidth() * 4 / 5, getWorld().getHeight() / 2);
		getWorld().getChildren().add(boss1);
	}

	@Override
	public void onRemove() {
		getWorld().getChildren().removeAll(getWorld().getChildren());
		
		
	}

	@Override
	public void onAct(long now) {
		if(!robotAdded && getWorld().getObjects(Robot.class).isEmpty()) {
			robotAdded = true;
			getWorld().getChildren().add(new Experiment(getWorld().getWidth() * 4 / 5, getWorld().getHeight() / 2));
		}else if(robotAdded && !experimentAdded && getWorld().getObjects(Experiment.class).isEmpty()) {
			experimentAdded = true;
			int randX = (int) (Math.random() * (getWorld().getWidth() - (Wall.sideLength * 2) - Hole.sideLength) + Wall.sideLength);
			int randY = (int) (Math.random() * (getWorld().getHeight() - (Wall.sideLength * 2) - Hole.sideLength) + Wall.sideLength);
			getWorld().getChildren().add(new Hole(randX, randY));
		}
	}
}
