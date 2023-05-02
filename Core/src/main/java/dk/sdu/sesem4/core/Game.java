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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.SPILocator;

import java.io.*;
import java.util.HashMap;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	private Map<UUID, Sprite> textureCache;

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
		
		renderMap();

		// render sprites
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		
		List<Entity> entities = gameData.getGameEntities().getEntities(Entity.class);

		for (Entity entity : entities) {
			// Get parts used for rendering
			SpritePart spritePart = entity.getEntityPart(SpritePart.class);
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			if (spritePart == null || positionPart == null) {
				continue;
			}

			// Create sprite
			Sprite sprite = getSprite(spritePart);

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
	 * @param spritePart Sprite part to get Sprite from
	 * @return Sprite texture
	 */
	private Sprite getSprite(SpritePart spritePart) {
		SpriteData spriteData = spritePart.getSprite();

		// Load cached version
		if (this.textureCache.containsKey(spriteData.getId())) {
			return this.textureCache.get(spriteData.getId());
		}

		// Ensure ressource class has been set, otherwise crash
		if (spriteData.getRessourceClass() == null) {
			throw new Error("Resource class not set on sprite");
		}

		// Get image data
		InputStream input = spriteData.getRessourceClass().getResourceAsStream(("/" + spriteData.getTexture()).toString());
		File targetFile;
		try	{
			if (input == null) {
				throw new Error("Sprite file is not present");
			}
			// Load file in
			byte[] buffer = input.readAllBytes();
			// Save file in the current directory
			targetFile = new File("Nelda-"+spriteData.getId()+".tmp");
			targetFile.deleteOnExit(); // Auto delete file when game closes
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
		} catch (IOException ioException) {
			System.out.println("IO exception");
			return new Sprite();
		}

		// Use the absolute path from the temporary file, to load into LibGDX
		Texture texture = new Texture(
				Gdx.files.absolute(
						targetFile.getAbsolutePath()
				)
		);
		Sprite sprite = new Sprite(texture);
		sprite.setFlip(spritePart.getSprite().isxFlipped(), spritePart.getSprite().isyFlipped());

		// Cache sprite
		this.textureCache.put(spriteData.getId(), sprite);

		return sprite;
	}

	/**
	 * This method is responsible for rendering the map.
	 */
	private void renderMap() {
		// if there is a map, load it and render it.
		if (gameData.getGameWorld().getMap() != null){
			TiledMap map = loadMap(gameData.getGameWorld().getMap());
			tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
			tiledMapRenderer.setView(camera);
			tiledMapRenderer.render();
		}
	}

	/**
	 * This method is responsible for loading the map.
	 * @param path The path to the map.
	 * @return The map.
	 */
	TiledMap loadMap(Path path) {
		TmxMapLoader tmxMapLoader = new TmxMapLoader();
		return tmxMapLoader.load(path.toString());
	}
}
