/**
 * The methods for game state
 */
public abstract class State {

	// keeps track of which world the state is in
	private World world;

	/**
	 * Makes a new state in a world
	 * 
	 * @param w the world
	 */
	public State(World w) {
		world = w;

	}
	
	/**
	 * Gets the world
	 * 
	 * @return the current world the state is in
	 */
	public World getWorld() { 
		return world; 
	}
	
	/** what a state does when initially set */
	public abstract void onSet();
	
	/** what a state does when removed */
	public abstract void onRemove();
	
	/** what a state does on the act call */
	public abstract void onAct(long now);
}
