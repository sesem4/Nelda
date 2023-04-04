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
import dk.sdu.sesem4.map.MapPlugin;

import java.util.ArrayList;

public class Game extends ApplicationAdapter implements InputProcessor {

    GameData gameData;
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
    @Override
    public void create () {
        gameData = new GameData();

        textures = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            textures.add(new Texture(Gdx.files.local("Core/src/main/resources/Zelda" + i + ".png")));
        }

        //Load the map into a array of maps save in the core module.
        MapPlugin mapPlugin = new MapPlugin();
        mapPlugin.start(gameData);

        sb = new SpriteBatch();
        sprite = new Sprite(textures.get(0));

        if (gameData.getGameWorld().getMap() == null) {
            System.out.println("FUUUCK");
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(gameData.getGameWorld().getMap());

        Gdx.input.setInputProcessor(this);

        w = 16 * 16;
        h = 11 * 16;
        camera = new OrthographicCamera();

        camera.setToOrtho(false,w,h);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        sb.begin();
        sprite.setSize(32, 32);
        sb.draw(sprite, sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 16, Gdx.graphics.getHeight() / 11);

        if (sprite.getY()+ sprite.getHeight() < 0){
            currentMapIndex += 16;
            tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

            sprite.setY(Gdx.graphics.getHeight() - sprite.getHeight());
        }
        if (sprite.getY() + sprite.getHeight() > Gdx.graphics.getHeight() && currentMapIndex > 0){
            currentMapIndex -= 16;
            tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

            sprite.setY(0);
        }
        if (sprite.getX() + sprite.getWidth() < 0){
            currentMapIndex -= 1;
            tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

            sprite.setX(Gdx.graphics.getWidth() - sprite.getWidth());
        }
        if (sprite.getX() + sprite.getWidth() > Gdx.graphics.getWidth()){
            currentMapIndex += 1;
            tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

            sprite.setX(0);
        }

        counter++;
        if (counter >= 60) {
            counter = 0;
        }
        if (left) {
            if (counter < 30){
                sprite.setTexture(textures.get(2));
            } else {
                sprite.setTexture(textures.get(3));
            }
            sprite.setFlip(true, false);
            sprite.setX(sprite.getX() - 1);
        }
        if (right) {
            if (counter < 30){
                sprite.setTexture(textures.get(2));
            } else {
                sprite.setTexture(textures.get(3));
            }
            sprite.translateX(1);
            sprite.setFlip(false, false);
        }
        if (up) {
            sprite.setFlip(counter >= 30, false);
            sprite.translateY(1);
            sprite.setTexture(textures.get(1));
        }
        if (down) {
            sprite.setFlip(counter < 30, false);
            sprite.translateY(-1);
            sprite.setTexture(textures.get(0));
        }
        sb.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            left = true;
        }
        if(keycode == Input.Keys.RIGHT) {
            right = true;
        }
        if(keycode == Input.Keys.UP) {
            up = true;
        }
        if(keycode == Input.Keys.DOWN) {
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
