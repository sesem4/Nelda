package dk.sdu.sesem4.melda.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Player() {


    }

    private Texture playerImage() {
        Texture texture = new Texture(Gdx.files.internal("image-path"));
        return texture;

    }
    private void render(){
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.draw(playerImage(),0,0);
        spriteBatch.end();
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }
    public void update(float dt) {

    }
    public void draw(ShapeRenderer sr) {

    }

    @Override
    Texture loadImage() {
        return null;
    }
}
