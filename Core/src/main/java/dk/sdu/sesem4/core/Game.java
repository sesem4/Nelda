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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.sesem4.map.MapPlugin;
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

    int currentMapIndex = 118;

    boolean up, down, left, right = false;

    float w, h;
    @Override
    public void create () {

        textures = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            textures.add(new Texture(Gdx.files.internal("Zelda" + i + ".png")));
        }


        //Load the map into a array of maps save in the core module.
        MapProcessingService mapProcessingService = new MapProcessingService();
        world = mapProcessingService.loadWorld();


        sb = new SpriteBatch();
        sprite = new Sprite(textures.get(0));

        tiledMapRenderer = new OrthogonalTiledMapRenderer(world[currentMapIndex]);

        Gdx.input.setInputProcessor(this);

        w = world[currentMapIndex].getProperties().get("width", Integer.class) * world[currentMapIndex].getProperties().get("tilewidth", Integer.class);
        h = world[currentMapIndex].getProperties().get("height", Integer.class) * world[currentMapIndex].getProperties().get("tileheight", Integer.class);
        camera = new OrthographicCamera();

        camera.setToOrtho(false,w,h);
    }



    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        System.out.println(currentMapIndex);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

//		System.out.println(h);
//		System.out.println("The view" + camera.viewportHeight);
//		System.out.println("The fucker is here" + sprite.getY());

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
            if (counter < 30){
                sprite.setFlip(false, false);
            } else {
                sprite.setFlip(true, false);
            }
            sprite.translateY(1);
            sprite.setTexture(textures.get(1));
        }
        if (down) {
            if (counter < 30){
                sprite.setFlip(true, false);
            } else {
                sprite.setFlip(false, false);
            }
            sprite.translateY(-1);
            sprite.setTexture(textures.get(0));

        }
        sb.end();
//		System.out.println(sprite.getX() + " " + sprite.getY());
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){

            left = true;

        }
        if(keycode == Input.Keys.RIGHT){

            right = true;

        }
        if(keycode == Input.Keys.UP){

            up = true;

        }
        if(keycode == Input.Keys.DOWN){

            down = true;


        }
        return false;
    }



    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT){
            left = false;}
        if (keycode == Input.Keys.RIGHT){
            right = false;
        }
        if (keycode == Input.Keys.UP){
            up = false;
        }
        if (keycode == Input.Keys.DOWN){
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
