import java.util.ArrayList;

import javafx.scene.image.ImageView;

/**
 * Actor is an abstract base class for general sprites in an arcade style game. 
 * Because Actor extends ImageView, you have access to all the ImageView commands 
 * such as:
 * - getX(), getY(), setX(), setY()
 * - setImage()
 * - getFitHeight(), getFitWidth()
 */
public abstract class Actor extends javafx.scene.image.ImageView{

	/**
	 * Makes an Actor
	 */
	public Actor() {

	}

	/**
	 * Moves this actor by the given dx and dy.
	 * 
	 * @param dx the amount to move horizontally (change in x)
	 * @param dy the amount to move vertically (change in y)
	 */
	public void move(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);

	}

	/**
	 * Returns the World this actor is in, or null if it is not in a world. 
	 * 
	 * @return the World this actor is in, or null if it is not in a world
	 */
	public World getWorld() {
		return (World) getParent();

	}

	/**
	 * Returns The width of the current image of this actor.
	 * 
	 * @return the width of the current image of this actor, taking into account any transformations.
	 */
	public double getWidth() {
		return getBoundsInLocal().getWidth();
	}

	/**
	 * Returns The height of the current image of this actor. 
	 * 
	 * @return the height of the current image of this actor, taking into account any transformations.
	 */
	public double getHeight() {
		return getBoundsInLocal().getHeight();
	}

	/**
	 * Returns a list of the actors of a given type intersecting this actor.
	 * 
	 * @param <A> the class of intersecting actors that will be in the returned list
	 * @param cls The type of intersecting actors that should be in the list
	 * @return a list of all actors of the given type intersecting this actor
	 */
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
		ArrayList<A> list = new ArrayList<A>();

		for(int i = 0; i < getWorld().getChildren().size(); i++) {
			if(getWorld().getChildren().get(i) instanceof Actor
					&& ((getImageView().getX() <= ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth() / 2 <= getImageView().getX() + getImageView().getImage().getWidth()
					&& getImageView().getY() <= ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight() / 2 <= getImageView().getY() + getImageView().getImage().getHeight())
					|| ((Actor) getWorld().getChildren().get(i)).getImageView().getX() <= getImageView().getX() + getImageView().getImage().getWidth()
					&& getImageView().getX() + getImageView().getImage().getWidth() / 2 <= ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getY() <= getImageView().getY() + getImageView().getImage().getHeight() / 2
					&& getImageView().getY() + getImageView().getImage().getHeight() / 2 <= ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight())	
					&& getWorld().getChildren().get(i) != this
					&& getWorld().getChildren().get(i).getClass().equals(cls)) {

				list.add((A)getWorld().getChildren().get(i));

			}
		}
		
		return list;
	}

	/**
	 * Returns one (the first) actor of the given class that is intersecting this actor. 
	 * 
	 * @param <A> the class of intersecting actor that will be in the returned
	 * @param cls the type of actor to return
	 * @return an intersecting actor of the given class, or null if no such actor
	 */

	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		for(int i = 0; i < getWorld().getChildren().size(); i++) {
			if(getWorld().getChildren().get(i) instanceof Actor
					&& ((getImageView().getX() <= ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth() / 2 <= getImageView().getX() + getImageView().getImage().getWidth()
					&& getImageView().getY() <= ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight() / 2 <= getImageView().getY() + getImageView().getImage().getHeight())
					|| ((Actor) getWorld().getChildren().get(i)).getImageView().getX() <= getImageView().getX() + getImageView().getImage().getWidth()
					&& getImageView().getX() + getImageView().getImage().getWidth() / 2 <= ((Actor) getWorld().getChildren().get(i)).getImageView().getX() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getWidth()
					&& ((Actor) getWorld().getChildren().get(i)).getImageView().getY() <= getImageView().getY() + getImageView().getImage().getHeight() / 2
					&& getImageView().getY() + getImageView().getImage().getHeight() / 2 <= ((Actor) getWorld().getChildren().get(i)).getImageView().getY() + ((Actor) getWorld().getChildren().get(i)).getImageView().getImage().getHeight())	
					&& getWorld().getChildren().get(i) != this
					&& getWorld().getChildren().get(i).getClass().equals(cls)) {

				return (A) getWorld().getChildren().get(i);

			}
		}
		return null;
	}
	
	public abstract ImageView getImageView();
	
	/**
	 * This method is called every frame once start has been called on the world.
	 * 
	 * @param now the current time in nanoseconds
	 */
	public abstract void act(long now);
}



