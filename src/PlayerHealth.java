import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerHealth extends Actor{

	private ImageView base;
	private ImageView disp;
	
	private int currentHealth;
	
	private boolean added;
	
	public PlayerHealth() {
		base = new ImageView();
		base.setImage(new Image("health_bar_base.png", 60, 10, false, false));
		disp = new ImageView();
		disp.setImage(new Image("health_bar_disp.png", 60, 10, false, false));
		currentHealth = 3;
		added = false;
	}
	
	public PlayerHealth(Player p) {
		base = new ImageView();
		currentHealth = p.getHealth();
		base.setImage(new Image("health_bar_base.png", 60, 10, false, false));
		disp = new ImageView();
		disp.setImage(new Image("health_bar_disp.png", 20 * currentHealth, 10, false, false));
		added = false;
	}
	
	@Override
	public void act(long now) {
		if(!added) {
			getWorld().getChildren().addAll(base, disp);
			added = true;
		}
		if(getWorld().getPlayer().getHealth() > 0) {
			if(getWorld().getPlayer().getHealth() != currentHealth) {
				disp.setFitWidth(20 * getWorld().getPlayer().getHealth());
				currentHealth = getWorld().getPlayer().getHealth();
			}
		}else if(getWorld().getPlayer().getHealth() == 0){
			getWorld().getChildren().remove(disp);
		}
		
	}

	@Override
	public ImageView getImageView() {
		return disp;
	}

}
