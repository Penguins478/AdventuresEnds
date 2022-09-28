import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;

public class TitleState extends State{
	
	private long prev;
	
	/**
	 * Makes a title state
	 * 
	 * @param w
	 */
	public TitleState(World w) {
		super(w);
		prev = 0;
	}
	
	/**
	 * What the title state does when first set up
	 */
	@Override
	public void onSet() {
		
		// getting the root node of the scene
		BorderPane pane = (BorderPane) getWorld().getParent();
		
		/* ------ the title text ------ */
		// text
		Text name = new Text("Adventure\'s Ends");
		
		// position
		name.setWrappingWidth(320);
		name.setX(pane.getWidth() / 2 - (name.getWrappingWidth() / 2));
		name.setY(pane.getHeight() * 1.5 / 4);
		
		// style
		name.setFill(Color.STEELBLUE);
		name.setFont(new Font("Arial", 40));
		
		// effects
		Light.Distant light = new Light.Distant();
		light.setAzimuth(200.0);
		light.setElevation(30.0);
		
		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(8.0);
		
		Reflection reflection = new Reflection();
		reflection.setFraction(0);
		reflection.setInput(lighting);
		
		name.setEffect(reflection);
		
		// adding to scene
		getWorld().getChildren().add(name);
		
		/* ------ the button to start the game ------ */
		Button startButton = new Button("Start Game");
		
		// position
		startButton.setMinWidth(100);
		startButton.setMinHeight(25);
		
		startButton.setLayoutX(pane.getWidth() / 2 - (startButton.getMinWidth() / 2));
		startButton.setLayoutY(pane.getHeight() * 4.5 / 7 - (startButton.getMinHeight() / 2));
		
		// adding to the world
		getWorld().getChildren().add(startButton);
		
		// when clicked
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getWorld().setState(new MinionState(getWorld()));
			}
			
		});
		
		/* ------ the button to start the game ------ */
		Button infoButton = new Button("Instructions");
		
		// position
		infoButton.setMinWidth(100);
		infoButton.setMinHeight(25);
		
		infoButton.setLayoutX(pane.getWidth() / 2 - (infoButton.getMinWidth() / 2));
		infoButton.setLayoutY(pane.getHeight() * 4 / 7 - (infoButton.getMinHeight() / 2));
		
		// adding to the world
		getWorld().getChildren().add(infoButton);
		
		// when clicked
		infoButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getWorld().setState(new InfoState(getWorld()));
				
			}
			
		});
		
		Player disp = new Player(-50, pane.getHeight() * 4 / 5, 50, 50, true);
		getWorld().getChildren().add(disp);
		
		Gun dispGun = new Gun(45, 40);
		getWorld().getChildren().add(dispGun);
		
		getWorld().start();
	}
	
	/**
	 * What the state does when removed
	 */
	@Override
	public void onRemove() {
		getWorld().getChildren().removeAll(getWorld().getChildren());
	}
	
	/**
	 * What the state does on each act
	 */
	@Override
	public void onAct(long now) {
		if(now - prev >= 1e6) {
			getWorld().getPlayer().setXPos(getWorld().getPlayer().getXPos() + 1);
			if(getWorld().getPlayer().getXPos() > getWorld().getWidth()) {
				getWorld().getPlayer().setXPos(-getWorld().getPlayer().getWidth());
			}
			prev = now;
		}
	}

}
