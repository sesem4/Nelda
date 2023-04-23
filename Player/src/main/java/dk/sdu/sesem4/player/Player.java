package dk.sdu.sesem4.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;

import java.util.List;

public class Player extends Entity {

    List<Texture> textures;

    Sprite sprite;

    public Player(EntityType entityType) {
        super(entityType);
    }


    public List<Texture> getTextures() {
        return textures;
    }

    public void setTextures(List<Texture> textures) {
        this.textures = textures;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprites(Sprite sprite) {
        this.sprite = sprite;
    }
}
