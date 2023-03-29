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
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.sesem4.map.MapProcessingService;

import java.util.ArrayList;

public class Game extends ApplicationAdapter implements InputProcessor {


	OrthographicCamera camera;
	TiledMap[] world;
	ArrayList<Texture> textures;
	TiledMapRenderer tiledMapRenderer;
	int counter;

	SpriteBatch sb;
	Sprite sprite;

	int currentMapIndex = 119;

	boolean up, down, left, right = false;

	float w, h;

	float moveSpeed = 1;
	Viewport viewport;

	private int tileWidth;
	private int tileHeight;
	private int mapHeightInTiles;
	private int mapWidthInTiles;

	@Override
	public void create() {
		textures = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			textures.add(new Texture(Gdx.files.internal("Zelda" + i + ".png")));
		}

		//Load the map into an array of maps saved in the core module.
		MapProcessingService mapProcessingService = new MapProcessingService();
		world = mapProcessingService.loadWorld("overworld", 16, 8);

		sb = new SpriteBatch();
		sprite = new Sprite(textures.get(0));
		sprite.setSize(16, 16);

		tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

		Gdx.input.setInputProcessor(this);
		mapWidthInTiles = world[currentMapIndex].getProperties().get("width", Integer.class);
		mapHeightInTiles = world[currentMapIndex].getProperties().get("height", Integer.class);
		tileWidth = world[currentMapIndex].getProperties().get("tilewidth", Integer.class);
		tileHeight = world[currentMapIndex].getProperties().get("tileheight", Integer.class);
		w = mapWidthInTiles * tileWidth;
		h = mapHeightInTiles * tileHeight;
		camera = new OrthographicCamera();
		camera.update();

		camera.setToOrtho(false, w, h);
		viewport = new FitViewport(w, (mapHeightInTiles + 3) * tileHeight, camera);
	}


	@Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		sb.setProjectionMatrix(camera.combined);
		sb.begin();
        sprite.draw(sb);

		float bottomEdge = 0;
		float topEdge = h;
		float leftEdge = 0;
		float rightEdge = w;
		if (sprite.getY() + sprite.getHeight() < bottomEdge) {
			changeMap(currentMapIndex+16);
			sprite.setY(topEdge - sprite.getHeight());
		}
		if (sprite.getY() > topEdge) {
			changeMap(currentMapIndex-16);
			sprite.setY(bottomEdge);
		}
		if (sprite.getX() + sprite.getWidth() < leftEdge) {
			changeMap(currentMapIndex-1);
			sprite.setX(rightEdge - sprite.getWidth());
		}
		if (sprite.getX() > rightEdge) {
			changeMap(currentMapIndex+1);
			sprite.setX(leftEdge);
		}

		counter++;
		if (counter >= 60) {
			counter = 0;
		}
		if (left) {
			if (counter < 30) {
				sprite.setTexture(textures.get(2));
			} else {
				sprite.setTexture(textures.get(3));
			}
			sprite.setFlip(true, false);
			sprite.translateX(-moveSpeed);
		}
		if (right) {
			if (counter < 30) {
				sprite.setTexture(textures.get(2));
			} else {
				sprite.setTexture(textures.get(3));
			}
			sprite.translateX(moveSpeed);
			sprite.setFlip(false, false);
		}
		if (up) {
			sprite.setFlip(counter < 30, false);
			sprite.translateY(moveSpeed);
			sprite.setTexture(textures.get(1));
		}
		if (down) {
			sprite.setFlip(counter < 30, false);
			sprite.translateY(-moveSpeed);
			sprite.setTexture(textures.get(0));
		}
		sb.end();
	}

	private void changeMap(int newMapIndex) {
		currentMapIndex = newMapIndex;
		tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);
	}

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

	@Override
	public void resize(int width, int height) {
		viewport.update(width,height,false);
		viewport.getCamera().position.set(16*16/2f,16*11/2f+tileHeight*3/2f,0);
		viewport.getCamera().update();
	}

}
