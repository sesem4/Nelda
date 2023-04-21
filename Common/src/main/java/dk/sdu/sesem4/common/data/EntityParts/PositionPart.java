package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * A class that represents the PositionPart.
 */
public class PositionPart implements EntityPart {
     private Rectangle boundingBox;
     private Direction direction;

     public PositionPart(Vector2 position, Vector2 size, Direction direction) {
          this.boundingBox = new Rectangle(position, size);
          this.direction = direction;
     }

     public PositionPart(Rectangle boundingBox, Direction direction) {
          this.boundingBox = boundingBox;
          this.direction = direction;
     }

     public Rectangle getBoundingBox() {
          return boundingBox;
     }

     public void setBoundingBox(Rectangle boundingBox) {
          this.boundingBox = boundingBox;
     }

     public Vector2 getPosition() {
          return this.getBoundingBox().getPosition();
     }

     /**
      * Set the position of the entity.
      * 
      * @param position the Vector2 (x and y coordinates) the Entity should be
      *                 positioned at
      */
     public void setPosition(Vector2 position) {
          boundingBox.setPosition(position);
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

     public Vector2 getSize() {
          return boundingBox.getSize();
     }

     public void setSize(Vector2 size) {
          boundingBox.setSize(size);
     }

     /**
      * Get the direction of the entity.
      * 
      * @return The direction of the entity.
      */
     public Direction getDirection() {
          return this.direction;
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
