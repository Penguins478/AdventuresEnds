import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Game extends Application{
	
	public static void main(String[] args) {
		
		launch(args);
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: lightsteelblue");
		Scene scene = new Scene(root, 650, 650);
		
		AdventuresEnds ae = new AdventuresEnds();
		root.setCenter(ae);
		ae.setState(new TitleState(ae));
		
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("char.png").toString()));
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Adventure\'s Ends");	
		
		primaryStage.show();
		
	}

}
