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
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.map.MapPlugin;

import java.util.ArrayList;

public class Game extends ApplicationAdapter implements InputProcessor {
	GameData gameData;
	OrthographicCamera camera;
	ArrayList<Texture> textures;
	TiledMapRenderer tiledMapRenderer;
	int counter;

	SpriteBatch sb;
	Sprite sprite;

	boolean up, down, left, right = false;
	float moveSpeed = 1.3f;

	float w, h;
	MapPlugin mapPlugin = new MapPlugin();

	@Override
	public void create() {
		gameData = new GameData();

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

		tiledMapRenderer = new OrthogonalTiledMapRenderer(gameData.getGameWorld().getMap());

		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera();
		camera.update();
		camera.setToOrtho(false, w, h);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		tiledMapRenderer = new OrthogonalTiledMapRenderer(gameData.getGameWorld().getMap());
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		//
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sprite.draw(sb);

		float bottomEdge = 0;
		float topEdge = h;
		float leftEdge = 0;
		float rightEdge = w;
		if (sprite.getY() + sprite.getHeight()/2 < bottomEdge) {
			changeMap(16);
			sprite.setY(topEdge - sprite.getHeight()/2);
		}
		if (sprite.getY() + sprite.getHeight()/2 > topEdge) {
			changeMap(-16);
			sprite.setY(bottomEdge - sprite.getHeight()/2);
		}
		if (sprite.getX() + sprite.getWidth()/2 < leftEdge) {
			changeMap(-1);
			sprite.setX(rightEdge - sprite.getWidth()/2);
		}
		if (sprite.getX() + sprite.getWidth()/2 > rightEdge) {
			changeMap(1);
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

	private void changeMap(int deltaIndex) {
//		this.mapPlugin.changeMap(gameData, deltaIndex);
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
