package dk.sdu.sesem4.keyboardcontrol;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

public class KeyBoardMovementController implements MovementControllerSPI{
        @Override
        public Direction getMovement(GameData gameData, Entity entity) {
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                return Direction.UP;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                return Direction.DOWN;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                return Direction.LEFT;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                return Direction.RIGHT;
            }
             return null;
        }


}
