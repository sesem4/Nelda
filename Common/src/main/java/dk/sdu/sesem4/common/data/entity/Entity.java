package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.EntityParts.EntityPart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.util.Direction;


/**
 * @author Muhammed and Anne Lærke
 */
public abstract class Entity {
    private Direction direction;
    private static final int width = 16;
    private static final int height = 16;

//   private Texture texture;
    
//
//    public Texture getTexture() {
//        return texture;
//    } 
//
//    public void setTexture(Texture texture) {
//        this.texture = texture;
//    }

    /**
     * Updates the gameData and gameEntities continuously
     * @param gameData
     * @param gameEntities
     */
    public abstract void process(GameData gameData, GameEntities gameEntities);

    /**
     * Add a new EntityPart to the entity and returns either true or false, based on if the entityPart is added.
     * @param part
     * @return True or False
     */
    public abstract boolean addEntityPart(EntityPart part);

    /**
     * Remove an EntityPart from the entity and returns either true or false, based on if the entityPart is removed
     * @param part
     * @return True or False
     */
    public abstract boolean removeEntityPart(EntityPart part);

    /**
     * Get a specific part based on the given ClassPart in the parameter
     * @param part
     * @return Class
     */
    public abstract  <E extends EntityPart>E getPrt(Class part);


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
