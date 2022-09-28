import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOverState extends State{

	private boolean win;
	
	public GameOverState(World w, boolean win) {
		super(w);
		this.win = win;
	}

	@Override
	public void onSet() {
		
		String text = "";
		int wrap;
		
		if(win) {
			text = "You Win!";
			wrap = 170;
		}else {
			text = "Game Over";
			wrap = 210;
		}
		
		Text gameOverText = new Text(text);
		
		// position
		gameOverText.setWrappingWidth(wrap);
		gameOverText.setX(getWorld().getWidth() / 2 - (gameOverText.getWrappingWidth() / 2));
		gameOverText.setY(getWorld().getHeight() * 1.5 / 4);
		
		// style
		gameOverText.setFill(Color.RED);
		gameOverText.setFont(new Font("Arial", 40));
		
		getWorld().getChildren().add(gameOverText);
		
		Button reset = new Button("Return to Title Screen");
		
		// position
		reset.setMinWidth(150);
		reset.setMinHeight(25);
		
		reset.setLayoutX(getWorld().getWidth() / 2 - (reset.getMinWidth() / 2));
		reset.setLayoutY(getWorld().getHeight() * 4.5 / 7 - (reset.getMinHeight() / 2));
		
		// adding to the world
		getWorld().getChildren().add(reset);
		
		// when clicked
		reset.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getWorld().setState(new TitleState(getWorld()));
				
			}
			
		});
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
