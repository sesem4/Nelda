package dk.sdu.sesem4.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.util.SPILocator;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class, where all process is handled and the game is rendered.
 */
public class Game extends ApplicationAdapter {

	/**
	 * The gameData, which is used to store all the data for the game.
	 */
	private GameData gameData;

	/**
	 * The camera, which is used to render the game.
	 */
	private OrthographicCamera camera;

	/**
	 * The textures, which is used to load an image with a specific width and
	 * height.
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
	 * The spriteBatch, which is used to render the sprite.
	 */
	private SpriteBatch spriteBatch;
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
	// private MapPlugin mapPlugin = new MapPlugin();

	/**
	 * This method is responsible for setting up the game, where the different plugins are started and the gameData is created, as well as the eventManager.
	 */
	@Override
	public void create() {

		gameData = new GameData();
		eventManager = EventManager.getInstance();

		// Locate all plugin services and start plugins
		List<PluginServiceSPI> pluginCreators = SPILocator.locateAll(PluginServiceSPI.class);
		pluginCreators.forEach((plugin) -> plugin.start(gameData));

		this.textures = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			textures.add(new Texture(Gdx.files.local("Core/src/main/resources/Zelda" + i + ".png")));
		}

		this.w = 16 * 16;
		this.h = 11 * 16;

		spriteBatch = new SpriteBatch();
		sprite = new Sprite(this.textures.get(2));
		sprite.setSize(16, 16);
		sprite.setPosition(this.w/2-sprite.getWidth()/2, this.h/2-sprite.getHeight()/2);

		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

		this.camera = new OrthographicCamera();
		this.camera.update();
		this.camera.setToOrtho(false, w, h);
	}

	/**
	 * This method is responsible for rendering the game, where the map is rendered
	 * and the different entities are drawn.
	 */
	@Override
	public void render() {
		eventManager = EventManager.getInstance();

		// Locate all plugin processors and run the process
		List<ProcessingServiceSPI> pluginProcessors = SPILocator.locateAll(ProcessingServiceSPI.class);
		pluginProcessors.forEach((plugin) -> plugin.process(gameData, new Priority()));

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.camera.update();
		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		this.tiledMapRenderer.setView(camera);
		this.tiledMapRenderer.render();

		this.spriteBatch.setProjectionMatrix(camera.combined);
		this.spriteBatch.begin();
		this.sprite.draw(spriteBatch);

		float bottomEdge = 0;
		float topEdge = this.h;
		float leftEdge = 0;
		float rightEdge = this.w;
		if (this.sprite.getY() + this.sprite.getHeight() / 2 < bottomEdge) {
			changeMap(Direction.DOWN);
			this.sprite.setY(topEdge - this.sprite.getHeight() / 2);
		}
		if (this.sprite.getY() + this.sprite.getHeight() / 2 > topEdge) {
			changeMap(Direction.UP);
			this.sprite.setY(bottomEdge - this.sprite.getHeight() / 2);
		}
		if (this.sprite.getX() + this.sprite.getWidth() / 2 < leftEdge) {
			changeMap(Direction.LEFT);
			this.sprite.setX(rightEdge - this.sprite.getWidth() / 2);
		}
		if (this.sprite.getX() + this.sprite.getWidth() / 2 > rightEdge) {
			changeMap(Direction.RIGHT);
			this.sprite.setX(leftEdge - this.sprite.getWidth() / 2);
		}

		this.counter = (this.counter + 1) % 16;

		if (this.left) {
			this.sprite.setFlip(true, false);
			this.sprite.setTexture(this.textures.get(this.counter < 8 ? 3 : 4));
			this.sprite.translateX(-this.moveSpeed);
		}
		if (this.right) {
			this.sprite.setFlip(false, false);
			this.sprite.setTexture(this.textures.get(this.counter < 8 ? 3 : 4));
			this.sprite.translateX(this.moveSpeed);
		}
		if (this.up) {
			this.sprite.setFlip(this.counter < 8, false);
			this.sprite.setTexture(this.textures.get(2));
			this.sprite.translateY(this.moveSpeed);
		}
		if (this.down) {
			this.sprite.setFlip(false, false);
			this.sprite.setTexture(this.textures.get(counter < 8 ? 0 : 1));
			this.sprite.translateY(-this.moveSpeed);
		}
		this.spriteBatch.end();

		// Locate all plugin post processors and run the post process
		List<PostProcessingServiceSPI> pluginPostProcessors = SPILocator.locateAll(PostProcessingServiceSPI.class);
		pluginPostProcessors.forEach((plugin) -> plugin.postProcess(gameData, new Priority()));
	}

	/**
	 * This method is responsible for changing the map, where the eventManager is
	 * notified.
	 * 
	 * @param direction The direction in which the map should be changed.
	 */
	private void changeMap(Direction direction) {
		/*eventManager.notify(MapTransitionEventType.class, new MapTransitionEvent(direction));*/
	}

}
