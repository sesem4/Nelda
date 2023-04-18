package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

/**
 * A class that represents the LifePart.
 * 
 * @author Anne Lærke & Muhammed & Jakob
 */
public class LifePart implements EntityPart {
     /**
      * The life of the entity.
      */
     private int life;

     /**
      * The constructor for the LifePart class.
      * 
      * @param life The life of the entity.
      */
     public LifePart(int life) {
          this.life = life;
     }

     /**
      * Get the life of the entity.
      * 
      * @return The number of life that the entity has.
      */
     public int getLife() {
          return this.life;
     }

     /**
      * Sets life value of variable life
      * 
      * @param life the value life should be set to
      */
     public void setLife(int life) {
          this.life = life;
     }

     /**
      * Check if the entity is alive.
      * 
      * @return True if the entity has more than 0 life, false otherwise.
      */
     public boolean isAlive() {
          return this.getLife() > 0;
     }

     /**
      * Check if the entity is dead.
      * 
      * @return True if the entity has 0 life, false otherwise.
      */
     public boolean isDead() {
          return !this.isAlive();
     }

     /**
      * Kill the Entity, regardless of previous life value
      */
     public void kill() {
          this.setLife(0);
     }

     /**
      * Do damage to the entity by reducing its life with the given amount.
      * 
      * @param damage The amount of damage that the entity will take.
      */
     public void doDamage(int damage) {
          this.setLife(this.getLife() - damage);
     }

     /**
      * Process the LifePart.
      * 
      * @param gameData The GameData object.
      * @param entity   The Entity object.
      */
     @Override
     public void process(GameData gameData, Entity entity) {
     }
}
