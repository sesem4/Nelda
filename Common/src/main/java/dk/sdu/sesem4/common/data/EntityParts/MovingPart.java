package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;

import java.util.*;

/**
 * Moving part, that handles and represents moving in the game
 */
public class MovingPart implements EntityPart {

    /**
     * The Knockback of the Entity
     */
    private Knockback knockback;

    /**
     * The speed that the entity should move.
     */
    private int moveSpeed;

    /**
     * the list of string path for the sprites of each direction
     */
    private final Map<Direction, List<SpriteData>> movementSpriteList;

    /**
     * Handle the movement of the Entity
     */

    private final MovementControllerSPI movementController;

    /**
     * The amount of frames that should be shown per second
     */
    private final int spriteFrameRate;

    /**
     * The constructor for the MovingPart class.
     * 
     * @param moveSpeed the speed which the Entity should move with
     */
    public MovingPart(int moveSpeed, int spriteFrameRate, MovementControllerSPI movementController) {
        this.moveSpeed = moveSpeed;
        this.spriteFrameRate = spriteFrameRate;
        this.movementController = movementController;

        // Setup sprite list
        this.movementSpriteList = new HashMap<>();
        for(Direction spriteListDirection: Direction.values()){
            this.movementSpriteList.put(spriteListDirection, new ArrayList<>());
        }
    }

    /**
     *
     * @param moveSpeed
     *
     * @deprecated
     */
    public MovingPart(int moveSpeed) {
        this(moveSpeed, 1, null);
    }
    /**
     * Get the speed that the entity should move.
     * 
     * @return The speed that the entity should move.
     */
    public int getMoveSpeed() {
        return this.moveSpeed;
    }

    /**
     * Set the speed that the entity should move.
     * 
     * @param moveSpeed The speed of which the entity should move.
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Sets the Knockback of the Entity
     * 
     * @param knockback the Knockback which should be applied to the Entity's
     *                  MovingPart
     */
    public void setKnockback(Knockback knockback) {
        this.knockback = knockback;
    }

    /**
     * Gets the Knockback of the Entity
     * 
     * @return knockback
     */
    public Knockback getKnockback() {
        return this.knockback;
    }

    /**
     * Check if the entity is knocked back.
     * 
     * @return True if the entity is knocked back, false otherwise.
     */
    public boolean isKnockedBack() {
        return this.knockback != null;
    }

    /**
     * Replace the sprites for the given direction.
     *
     * @param direction The direction to replace the sprites for
     * @param sprites The list of sprites to replaced with. The content should be the string path of the sprite
     */
    public void setSprites(Direction direction, List<SpriteData> sprites) {
        this.movementSpriteList.replace(direction, sprites);
    }

    /**
     * Add a sprite to the current list of sprites for the provided direction
     *
     * @param direction The direction to add sprite to
     * @param sprite The string path of sprite
     */
    public void addSprite(Direction direction, SpriteData sprite) {
        this.movementSpriteList.get(direction).add(sprite);
    }

    /**
     * Get the list of sprites for the provided direction
     *
     * @param direction The direction to get sprites from
     * @return The list of sprites. The content is a list of string path for the sprites
     */
    public List<SpriteData> getSprites(Direction direction) {
        return this.movementSpriteList.get(direction);
    }

    /**
     * A method that processes the MovingPart.
     * 
     * @param gameData The GameData object.
     * @param entity   The Entity object.
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        // Get the current position if the Entity
        PositionPart positionPart = entity.getEntityPart(PositionPart.class);

        // Check if the Entity is knocked back
        if (this.isKnockedBack()) {
            // If so, check whether the Knockback duration as run out
            if (this.knockback.hasRunOut()) {
                this.knockback = null;
            } else {
                // Otherwise, move in the direction set in the Knockback, with the
                // knockback.speed and decrement the knockback duration
                Vector2 deltaPosition = new Vector2(this.knockback.getDirection()).times(this.knockback.getSpeed());
                positionPart.move(deltaPosition);
                this.knockback.decrementDuration();
                return;
            }
        }
        Direction direction = null;

        // Handle actual movement
        if (this.movementController != null) {
            direction = this.movementController.getMovement(gameData, entity);
        } else {
            direction = Direction.UP;
        }
        if (direction == null) {
            return;
        }

        positionPart.setDirection(direction);

        // Update Entity position
        float x = positionPart.getPosition().getX();
        float y = positionPart.getPosition().getY();
        switch (direction) {
            case UP:
                y += this.getMoveSpeed();
                break;
            case DOWN:
                y -= this.getMoveSpeed();
                break;
            case LEFT:
                x += this.getMoveSpeed();
                break;
            case RIGHT:
                x -= this.getMoveSpeed();
                break;
        }
        positionPart.getPosition().setX(x);
        positionPart.getPosition().setY(y);

        setSprite(gameData, entity, positionPart);
    }

    /**
     * Set the current sprite of the entity based on the direction and the elapsed time of the game, since the game started
     *
     * @param gameData
     * @param entity
     * @param positionPart
     */
    protected void setSprite(GameData gameData, Entity entity, PositionPart positionPart) {
        SpritePart spritePart = entity.getEntityPart(SpritePart.class);
        if (spritePart == null) {
            return;
        }

        List<SpriteData> spriteList = this.movementSpriteList.get(positionPart.getDirection());
        int listSize = spriteList.size();
        if (listSize > 0) {
            spritePart.setSprite(spriteList.get((int) (gameData.getElapsedTime() / this.spriteFrameRate) % listSize));
        }
    }

    public void undoMovement(Entity entity) {

    }
}
