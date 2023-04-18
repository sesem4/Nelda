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
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.MapTransitionEvent;
import dk.sdu.sesem4.common.event.MapTransitionEventType;
import dk.sdu.sesem4.map.MapPlugin;

import java.util.ArrayList;

/**
 * The Game class, where all process is handled and the game is rendered.
 */
public class Game extends ApplicationAdapter implements InputProcessor {
	private GameData gameData;
	private OrthographicCamera camera;
	private ArrayList<Texture> textures;
	private TiledMapRenderer tiledMapRenderer;
	private EventManager eventManager;
	private int counter;

	private SpriteBatch sb;
	private Sprite sprite;

	private boolean up, down, left, right = false;
	private float moveSpeed = 1.3f;

	private float w, h;
	private MapPlugin mapPlugin = new MapPlugin();

	/**
	 * This method is responsible for setting up the game, where the different plugins are started and the gameData is created, as well as the eventManager.
	 */
	@Override
	public void create() {
		gameData = new GameData();
		eventManager = EventManager.getInstance();

		textures = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			textures.add(new Texture(Gdx.files.local("Core/src/main/resources/Zelda" + i + ".png")));
		}

		w = 16 * 16;
		h = 11 * 16;

		mapPlugin.start(gameData);

		sb = new SpriteBatch();
		sprite = new Sprite(textures.get(2));
		sprite.setSize(16, 16);
		sprite.setPosition(w/2-sprite.getWidth()/2, h/2-sprite.getHeight()/2);

		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera();
		camera.update();
		camera.setToOrtho(false, w, h);
	}

	/**
	 * This method is responsible for rendering the game, where the map is rendered and the different entities are drawn.
	 */
	@Override
	public void render() {
		mapPlugin.process(gameData, new Priority());

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		TiledMap map = Utils.loadMap(gameData.getGameWorld().getMap());
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sprite.draw(sb);

		float bottomEdge = 0;
		float topEdge = h;
		float leftEdge = 0;
		float rightEdge = w;
		if (sprite.getY() + sprite.getHeight()/2 < bottomEdge) {
			changeMap(Direction.DOWN);
			sprite.setY(topEdge - sprite.getHeight()/2);
		}
		if (sprite.getY() + sprite.getHeight()/2 > topEdge) {
			changeMap(Direction.UP);
			sprite.setY(bottomEdge - sprite.getHeight()/2);
		}
		if (sprite.getX() + sprite.getWidth()/2 < leftEdge) {
			changeMap(Direction.LEFT);
			sprite.setX(rightEdge - sprite.getWidth()/2);
		}
		if (sprite.getX() + sprite.getWidth()/2 > rightEdge) {
			changeMap(Direction.RIGHT);
			sprite.setX(leftEdge - sprite.getWidth()/2);
		}

		counter = (counter + 1) % 16;

		if (left) {
			sprite.setFlip(true, false);
			sprite.setTexture(textures.get(counter < 8 ? 3 : 4));
			sprite.translateX(-moveSpeed);
		}
		if (right) {
			sprite.setFlip(false, false);
			sprite.setTexture(textures.get(counter < 8 ? 3 : 4));
			sprite.translateX(moveSpeed);
		}
		if (up) {
			sprite.setFlip(counter < 8, false);
			sprite.setTexture(textures.get(2));
			sprite.translateY(moveSpeed);
		}
		if (down) {
			sprite.setFlip(false, false);
			sprite.setTexture(textures.get(counter < 8 ? 0 : 1));
			sprite.translateY(-moveSpeed);
		}
		sb.end();
	}

	/**
	 * This method is responsible for changing the map, where the eventManager is notified.
	 * @param direction
	 */
	private void changeMap(Direction direction) {
		System.out.println("Change map");
		eventManager.notify(MapTransitionEventType.class, new MapTransitionEvent(direction));
//		this.mapPlugin.changeMap(gameData, deltaIndex);
	}

	/**
	 * Checks if a key is pressed, and if so, sets the boolean to true.
	 * @param keycode The key that is pressed.
	 * @return Returns true if the key is pressed. Return false otherwise.
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			left = true;
		}
		if (keycode == Input.Keys.RIGHT) {
			right = true;
		}
		if (keycode == Input.Keys.UP) {
			up = true;
		}
		if (keycode == Input.Keys.DOWN) {
			down = true;
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
			left = false;
		}
		if (keycode == Input.Keys.RIGHT) {
			right = false;
		}
		if (keycode == Input.Keys.UP) {
			up = false;
		}
		if (keycode == Input.Keys.DOWN) {
			down = false;
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
//		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
//		Vector3 position = camera.unproject(clickCoordinates);
//		sprite.setPosition(position.x, position.y);
		return true;
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
