package dk.sdu.sesem4.keyboardcontrol;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

/**
 * This class is responsible for the movement of the entity, which is controlled by the keyboard keys.
 */
public class KeyBoardMovementController implements MovementControllerSPI{
        @Override
        public Direction getMovement(GameData gameData, Entity entity) {
            if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
                return Direction.UP;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                return Direction.DOWN;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                return Direction.LEFT;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                return Direction.RIGHT;
            }
            return null;
        }


}
