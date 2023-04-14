package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.EntityParts.EntityPart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.util.Direction;

import java.util.HashMap;


/**
 * @author Muhammed and Anne Lærke
 */
public abstract class Entity {
    private final HashMap<Class,EntityPart> entityParts = new HashMap<>();
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
    public void process(GameData gameData, GameEntities gameEntities){

    }

    /**
     * Add a new EntityPart to the entity.
     * @param part
     */
    public void addEntityPart(EntityPart part){
        entityParts.put(part.getClass(),part);
    }

    /**
     * Remove an EntityPart from the entity.
     * @param partClass
     */
    public  void removeEntityPart(Class partClass){
        entityParts.remove(partClass);
    }

    /**
     * Get a specific part based on the given ClassPart in the parameter
     * @param partClass
     * @return Class
     */
    public   <E extends EntityPart>E getPrt(Class partClass){
        return (E)entityParts.get(partClass);
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
