package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.EntityParts.EntityPart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.map.World;

public abstract class Entity {
//    private Texture texture;
//
//    public Texture getTexture() {
//        return texture;
//    }
//
//    public void setTexture(Texture texture) {
//        this.texture = texture;
//    }
    abstract void process(GameData gameData, World world);
    abstract  <E extends EntityPart>E getPrt(Class part);
}
