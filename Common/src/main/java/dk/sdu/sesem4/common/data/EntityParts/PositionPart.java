package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * A class that represents the PositionPart.
 * 
 * @author @AnneLærke & Muhammed & Jakob
 */
public class PositionPart implements EntityPart {
     // The position of the entity.
     private Vector2 position;
     // The direction of the entity.
     private Direction direction;

     /**
      * The constructor for the PositionPart class.
      * 
      * @param position  The position of the entity.
      * @param direction The direction of the vector
      */
     public PositionPart(Vector2 position, Direction direction) {
          this.position = position;
          this.direction = direction;
     }

     /**
      * Get the position of the entity.
      * 
      * @return The position of the entity.
      */
     public Vector2 getPosition() {
          return position;
     }

     /**
      * Set the position of the entity.
      * 
      * @param position the Vector2 (x and y coordinates) the Entity should be
      *                 positioned at
      */
     public void setPosition(Vector2 position) {
          this.position = position;
     }

     /**
      * Moves the entity by adding the values of deltaPosition to current position
      * 
      * @param deltaPosition Vector2 x and y values that should be added to the
      *                      current position
      */
     public void move(Vector2 deltaPosition) {
          this.setPosition(this.getPosition().plus(deltaPosition));
     }

     /**
      * Get the direction of the entity.
      * 
      * @return The direction of the entity.
      */
     public Direction getDirection() {
          return direction;
     }

     /**
      * Sets the Direction of the Entity
      * 
      * @param direction The direction the Entity should be going in
      */
     public void setDirection(Direction direction) {
          this.direction = direction;
     }

     /**
      * A method that processes the PositionPart.
      * 
      * @param gameData The GameData object.
      * @param entity   The Entity object.
      */
     @Override
     public void process(GameData gameData, Entity entity) {
     }
}
