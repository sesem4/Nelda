package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.Entity;
import dk.sdu.sesem4.common.data.GameData;

public interface ControlSPI {
    /**
     * Move around on the map
     * <br><br>
     * Pre-condition: An entity has to be existing, with position and direction data.<br>
     * post-condition: The entity has moved in the given direction.
     *
     * @param gameData The game data
     * @param entity The entity to move on the map
     *
     * @return dX and dY change in float array, that the entity has to move
     */
    float[] move(GameData gameData, Entity entity);
}
