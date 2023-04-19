package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

/**
 * The PoisonPart is a CollisionPart that is applied to an Entity upon collision with another Entity.
 */
public class PoisonPart implements CollisionPart {
    /**
     * The amount of damage that should be dealt to another Entity, every tic.
     */
    private int ticDamage;
    /**
     * The duration of how long the poison will affect the Entity.
     */
    private int poisonDuration;
    /**
     * The counter that keeps track of how many tics the poison has been active.
     */
    private int poisonCounter;
    /**
     * The boolean that keeps track of whether the Entity is poisoned or not.
     */
    private boolean poisoned;

    /**
     * Constructs a PoisonPart
     * @param ticDamage the amount of damage that should be dealt to another Entity, every tic
     * @param poisonDuration the duration of how long the poison will affect the Entity
     */
    public PoisonPart(int ticDamage, int poisonDuration) {
        this.ticDamage = ticDamage;
        this.poisonDuration = poisonDuration;
        this.poisonCounter = 0;
        this.poisoned = false;
    }

    /**
     * Sets the poisoned boolean to true.
     * @param poisoned the boolean that keeps track of whether the Entity is poisoned or not
     */
    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    /**
     * Gets the poisoned boolean.
     * @return poisoned
     */
    public boolean isPoisoned() {
        return this.poisoned;
    }

    /**
     * Process the PoisonPart. Here the Entity is poisoned for a set duration if it collides with another Entity.
     * @param gameData the GameData
     * @param entity the Entity
     */
    public void process(GameData gameData, Entity entity) {
        if (this.poisoned) {
            LifePart lifePart = entity.getEntityPart(LifePart.class);
            int life = lifePart.getLife();

            lifePart.setLife(life - this.ticDamage);

            this.poisonCounter++;
            if (this.poisonCounter == this.poisonDuration) {
                this.poisonCounter = 0;
                this.poisoned = false;
            }
        }
    }
}
