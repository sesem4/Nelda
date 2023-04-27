package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * Handles data for the position, size and direction of entity
 */
public class PositionPart implements EntityPart {
     /**
      * The Entity's bounding-box rectangle.
      * This includes position and size.
      */
     private Rectangle boundingBox;
     
     /**
      * The direction the entity is facing
      */
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
     
     /**
      * @return the size (width and height) of the entity
      */
     public Vector2 getSize() {
          return boundingBox.getSize();
     }
     
     /**
      * @param size the size (width and height) of the entity
      */
     public void setSize(Vector2 size) {
          boundingBox.setSize(size);
     }
     
     public Direction getDirection() {
          return this.direction;
     }
     
     public void setDirection(Direction direction) {
          this.direction = direction;
     }

     @Override
     public void process(GameData gameData, Entity entity) {

     }
}
