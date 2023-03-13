package dk.sdu.sesem4.melda.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesem4.melda.core.Game;

import java.awt.event.TextEvent;

public abstract class Entity {

    protected float x;
    protected float y;

    protected float dx;
    protected float dy;

    protected float radians;
    protected float speed;
    protected float rotationSpeed;

    protected int width;
    protected int height;
    private Texture texture;
    private SpriteBatch spriteBatch;

    //entities that leave the screen will reappear on the opposite side
    protected void wrap() {
        if(x < 0) x = Game.WIDTH;
        if(x > Game.WIDTH) x = 0;
        if(y < 0) y = Game.HEIGHT;
        if(y > Game.HEIGHT) y = 0;
    }

    abstract Texture loadImage();

}
