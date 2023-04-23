package dk.sdu.sesem4.common.data.gamedata;

/**
 * Data for the game
 *
 * @author The0Mikkel
 */
public class GameData {
	/**
	 * Game world data
	 */
	private GameWorld gameWorld;

	/**
	 * Game data entities
	 */
	private GameEntities gameEntities;
	/**
	 * Delta time, which is the time between the last process loop and the current one.
	 */
	private float deltaTime;

	public GameData() {
		gameEntities = new GameEntities();
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public GameEntities getGameEntities() {
		return gameEntities;
	}

	public void setGameEntities(GameEntities gameEntities) {
		this.gameEntities = gameEntities;
	}

	public float getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(float deltaTime) {
		this.deltaTime = deltaTime;
	}
}
