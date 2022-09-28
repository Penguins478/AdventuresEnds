import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InfoState extends State{

	public InfoState(World w) {
		super(w);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSet() {
		
		getWorld().setStyle("-fx-background-color: slategray");
		
		Text header = new Text();
		header.setFont(new Font(22));
		header.setWrappingWidth(150);
		header.setText("How To Play");
		header.setX((getWorld().getWidth() - header.getWrappingWidth()) / 2);
		header.setY(getWorld().getHeight() / 8.5);
		
		Text text = new Text();
		text.setFont(new Font(13));
		text.setWrappingWidth(400);
		text.setText("Welcome to Adventure\'s Ends! In this game, your player's movement is controlled by using the arrow keys. "
				+ "The player wields a gun that can be tilted upwards and downwards using the W and S keys repectively. To fire this gun, "
				+ "press the space bar to shoot bullets. In addition, every 5 seconds, the player can activate their special ability "
				+ "which allows the player to rapid fire 5 bullets. When the player kills an enemy, there is a small chance that the enemy will "
				+ "drop a power-up. Power-ups can be collected by pressing the up key. However, power-ups cannot be collected when that trait is maxed. "
				+ "This means that if you are at max health, you can no longer restore your health. "
				+ "After eliminating all the monsters on the map, a hole that transports you to the next room will spawn randomly on the ground. Players must press the down key to enter."
				+ "\n\n\n"
				+ "This dungeon that you will have to conquer consists of 8 rooms. Rooms "
				+ "1 through 6 contain gunners and kamikazes. Room 7 contains gunners "
				+ "and kamikazes, however, it also includes fake exits that deal damage to the "
				+ "player. Thus, the player must enter the exit respective to the number gunner that "
				+ "died and made a certain unique sound. Finally, Room 8 consists of two parts. The first part is "
				+ "the robot boss that shoots homing bullets. The second part is the experiment boss "
				+ "that shoots lots of bullets and leaves acid traps on the floor. One last tip: "
				+ "Enemies always spawn to the right side of the map, so try to stand on the left side. Good luck!");
		
		text.setX((getWorld().getWidth() - text.getWrappingWidth()) / 2);
		text.setY(getWorld().getHeight() / 5);
		
		Button startButton = new Button("Play Game!");
		
		// position
		startButton.setMinWidth(100);
		startButton.setMinHeight(25);
		
		startButton.setLayoutX(getWorld().getWidth() / 2 - (startButton.getMinWidth() / 2));
		startButton.setLayoutY(getWorld().getHeight() * 6.25 / 7 - (startButton.getMinHeight() / 2));
		
		// when clicked
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				getWorld().setState(new MinionState(getWorld()));
				
			}
			
		});
		
		getWorld().getChildren().addAll(header, text, startButton);
	}

	@Override
	public void onRemove() {
		
		getWorld().getChildren().removeAll(getWorld().getChildren());
		
	}

	@Override
	public void onAct(long now) {
		// TODO Auto-generated method stub
		
	}

}
