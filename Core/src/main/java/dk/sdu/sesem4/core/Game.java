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
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.util.SPILocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * The tiledMapRenderer, which is used to render the map.
	 */
	private TiledMapRenderer tiledMapRenderer;

	/**
	 * The spriteBatch, which is used to render the sprite.
	 */
	private SpriteBatch spriteBatch;

	/**
	 * Cache for sprites based on file path.
	 */
	private Map<String, Texture> textureCache;

	public Game() {
		this.textureCache = new HashMap<>();
	}

	/**
	 * This method is responsible for setting up the game, where the different plugins are started and the gameData is created, as well as the eventManager.
	 */
	@Override
	public void create() {
		gameData = new GameData();

		// Locate all plugin services and start plugins
		List<PluginServiceSPI> pluginCreators = SPILocator.locateAll(PluginServiceSPI.class);
		pluginCreators.forEach((plugin) -> plugin.start(gameData));

		spriteBatch = new SpriteBatch();

		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

		this.camera = new OrthographicCamera();
		this.camera.update();
		float w = 16 * 16;
		float h = 11 * 16;
		this.camera.setToOrtho(false, w, h);
	}

	/**
	 * This method is responsible for rendering the game, where the map is rendered
	 * and the different entities are drawn.
	 */
	@Override
	public void render() {
		// Set deltaTime
		this.gameData.setDeltaTime(Gdx.graphics.getDeltaTime());
		this.gameData.processElapsedTime();

		// Locate all plugin processors and run the process
		List<ProcessingServiceSPI> pluginProcessors = SPILocator.locateAll(ProcessingServiceSPI.class);
		pluginProcessors.forEach((plugin) -> plugin.process(gameData, new Priority()));

		// OpenGL stuff
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.graphics.getDeltaTime();

		this.camera.update();
		
		// render map
		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		this.tiledMapRenderer.setView(camera);
		this.tiledMapRenderer.render();

		// render sprites
		this.spriteBatch.setProjectionMatrix(camera.combined);
		this.spriteBatch.begin();
		
		List<Entity> entities = gameData.getGameEntities().getEntities(Entity.class);

		for (Entity entity : entities) {
			// Get parts used for rendering
			SpritePart spritePart = entity.getEntityPart(SpritePart.class);
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			if (spritePart == null || positionPart == null) {
				continue;
			}

			// Create sprite
			Sprite sprite = new Sprite(getSprite(spritePart.getSprite().getTexture().toString()));

			// Set sprite size and position from entity parts
			sprite.setSize(16, 16); // TODO: Size has to be set from entity, but this is included in a later update
			sprite.setPosition(positionPart.getPosition().getX(), positionPart.getPosition().getY());

			// Draw sprite
			sprite.draw(spriteBatch);
		}

		this.spriteBatch.end();

		// Locate all plugin post processors and run the post process
		List<PostProcessingServiceSPI> pluginPostProcessors = SPILocator.locateAll(PostProcessingServiceSPI.class);
		pluginPostProcessors.forEach((plugin) -> plugin.postProcess(gameData, new Priority()));
	}

	/**
	 * Get sprite with caching
	 *
	 * @param filePath Path for sprite
	 * @return Sprite texture
	 */
	private Texture getSprite(String filePath) {
		if (this.textureCache.containsKey(filePath)) {
			return this.textureCache.get(filePath);
		}

		Texture texture = new Texture(Gdx.files.local(filePath));
		this.textureCache.put(filePath, texture);

		return texture;
	}
}
