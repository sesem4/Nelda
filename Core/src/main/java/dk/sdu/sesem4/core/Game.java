package dk.sdu.sesem4.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.MapTransitionEvent;
import dk.sdu.sesem4.common.event.MapTransitionEventType;
import dk.sdu.sesem4.map.MapPlugin;
import dk.sdu.sesem4.player.Player;
import dk.sdu.sesem4.player.PlayerPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class, where all process is handled and the game is rendered.
 */
public class Game extends ApplicationAdapter implements InputProcessor {

	/**
	 * The gameData, which is used to store all the data for the game.
	 */
	private GameData gameData;

	/**
	 * The camera, which is used to render the game.
	 */
	private OrthographicCamera camera;

	/**
	 * The textures, which is used to load an image with a specific width and height.
	 */
	private ArrayList<Texture> textures;

	/**
	 * The tiledMapRenderer, which is used to render the map.
	 */
	private TiledMapRenderer tiledMapRenderer;

	/**
	 * The eventManager, which is used to handle events.
	 */
	private EventManager eventManager;

	/**
	 * The counter, which is used to change the texture of the sprite.
	 */
	private int counter;

	/**
	 *  The spriteBatch, which is used to render the sprite.
	 */
	private SpriteBatch sb;
	/**
	 * The sprite, which is used to render the sprite.
	 */
	private Sprite sprite;

	/**
	 * The booleans, which is used to check if a key is pressed.
	 */
	private boolean up, down, left, right = false;

	/**
	 * The moveSpeed, which is used to set the speed of the sprite.
	 */
	private float moveSpeed = 1.3f;

	/**
	 * The width and height, which is used to set the size of the camera.
	 */
	private float w, h;

	/**
	 * The mapPlugin, which is used to start the map plugin.
	 */
//	private MapPlugin mapPlugin = new MapPlugin();

	/**
	 * Arraylist of all PostProcessingServices
	 */
	private final List<PostProcessingServiceSPI> postProcessingServiceSPIList = new ArrayList<>();

	private final List<ProcessingServiceSPI> processingServiceSPIList = new ArrayList<>();

	private List<PluginServiceSPI> pluginServiceSPIList = new ArrayList<>();

	private List<Entity> entities = new ArrayList<>();

	/**
	 * This method is responsible for setting up the game, where the different plugins are started and the gameData is created, as well as the eventManager.
	 */
	@Override
	public void create() {
		gameData = new GameData();
		eventManager = EventManager.getInstance();

		this.textures = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			textures.add(new Texture(Gdx.files.local("Core/src/main/resources/Zelda" + i + ".png")));
		}

		this.w = 16 * 16;
		this.h = 11 * 16;

//		entities.add(new Player(EntityType.Player));
//		sb = new SpriteBatch();
//		sprite = new Sprite(this.textures.get(2));
//		sprite = new Sprite(this.textures.get(2));
//		sprite.setSize(16, 16);
//		sprite.setPosition(this.w/2-sprite.getWidth()/2, this.h/2-sprite.getHeight()/2);
//

		Gdx.input.setInputProcessor(this);

		this.camera = new OrthographicCamera();
		this.camera.update();
		this.camera.setToOrtho(false, w, h);

		MapPlugin mapPlugin = new MapPlugin();
		PlayerPlugin playerPlugin = new PlayerPlugin();

		pluginServiceSPIList = List.of(mapPlugin, playerPlugin);

		processingServiceSPIList.add(mapPlugin);
		processingServiceSPIList.add(playerPlugin);

		postProcessingServiceSPIList.add(mapPlugin);
		postProcessingServiceSPIList.add(playerPlugin);

		// Calls the method startPluginServices, this will start all the pluginServices
		startPluginServices();

	}

	/**
	 * This method is responsible for rendering the game, where the map is rendered and the different entities are drawn.
	 */
	@Override
	public void render() {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		this.camera.update();

		updateProcessingServices();

		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		this.tiledMapRenderer.setView(camera);
		this.tiledMapRenderer.render();


//		Sprite sprite = new Sprite();
//		this.sb.setProjectionMatrix(camera.combined);
//		this.sb.begin();

//		this.sprite.draw(sprite);
//
//		float bottomEdge = 0;
//		float topEdge = this.h;
//		float leftEdge = 0;
//		float rightEdge = this.w;
//		if (this.sprite.getY() + this.sprite.getHeight()/2 < bottomEdge) {
//			changeMap(Direction.DOWN);
//			this.sprite.setY(topEdge - this.sprite.getHeight()/2);
//		}
//		if (this.sprite.getY() + this.sprite.getHeight()/2 > topEdge) {
//			changeMap(Direction.UP);
//			this.sprite.setY(bottomEdge - this.sprite.getHeight()/2);
//		}
//		if (this.sprite.getX() + this.sprite.getWidth()/2 < leftEdge) {
//			changeMap(Direction.LEFT);
//			this.sprite.setX(rightEdge - this.sprite.getWidth()/2);
//		}
//		if (this.sprite.getX() + this.sprite.getWidth()/2 > rightEdge) {
//			changeMap(Direction.RIGHT);
//			this.sprite.setX(leftEdge - this.sprite.getWidth()/2);
//		}
//
//		this.counter = (this.counter + 1) % 16;
//
//		if (this.left) {
//			this.sprite.setFlip(true, false);
//			this.sprite.setTexture(this.textures.get(this.counter < 8 ? 3 : 4));
//			this.sprite.translateX(-this.moveSpeed);
//		}
//		if (this.right) {
//			this.sprite.setFlip(false, false);
//			this.sprite.setTexture(this.textures.get(this.counter < 8 ? 3 : 4));
//			this.sprite.translateX(this.moveSpeed);
//		}
//		if (this.up) {
//			this.sprite.setFlip(this.counter < 8, false);
//			this.sprite.setTexture(this.textures.get(2));
//			this.sprite.translateY(this.moveSpeed);
//		}
//		if (this.down) {
//			this.sprite.setFlip(false, false);
//			this.sprite.setTexture(this.textures.get(counter < 8 ? 0 : 1));
//			this.sprite.translateY(-this.moveSpeed);
//		}
//		this.sb.end();

		updatePostProcessingServices();

	}

	private void startPluginServices() {
		for (PluginServiceSPI pluginServiceSPI : pluginServiceSPIList) {
			pluginServiceSPI.start(gameData);

			System.out.println("Plugin started: " + pluginServiceSPI.getClass().getName());

		}
	}

	private void updateProcessingServices() {
		for (ProcessingServiceSPI processingServiceSPI : processingServiceSPIList) {
			processingServiceSPI.process(gameData, new Priority());
		}
	}

	private void updatePostProcessingServices() {

		// Update PostEntityProcessingService
		for (PostProcessingServiceSPI postProcessingServiceSPI : this.postProcessingServiceSPIList) {
			postProcessingServiceSPI.postProcess(gameData, new Priority());
		}
	}

	/**
	 * This method is responsible for changing the map, where the eventManager is notified.
	 * @param direction The direction in which the map should be changed.
	 */
	private void changeMap(Direction direction) {
		eventManager.notify(MapTransitionEventType.class, new MapTransitionEvent(direction));
	}

	/**
	 * Checks if a key is pressed, and if so, sets the boolean to true.
	 * @param keycode The key that is pressed.
	 * @return Returns true if the key is pressed. Return false otherwise.
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			this.left = true;
		}
		if (keycode == Input.Keys.RIGHT) {
			this.right = true;
		}
		if (keycode == Input.Keys.UP) {
			this.up = true;
		}
		if (keycode == Input.Keys.DOWN) {
			this.down = true;
		}
		return false;
	}

	/**
	 * Checks if a key is released, and if so, sets the boolean to false.
	 * @param keycode The key that is released.
	 * @return true if the key is released.
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			this.left = false;
		}
		if (keycode == Input.Keys.RIGHT) {
			this.right = false;
		}
		if (keycode == Input.Keys.UP) {
			this.up = false;
		}
		if (keycode == Input.Keys.DOWN) {
			this.down = false;
		}
		return false;
	}

	/**
	 * Check if a specific key is typed.
	 * @param character the key that is typed.
	 * @return true if the key is typed.
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
