package dk.sdu.sesem4.common.data.controllerParts;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.keyboardcontrol.ControlType;
import dk.sdu.sesem4.keyboardcontrol.KeyBoardController;
import dk.sdu.sesem4.keyboardcontrol.KeyBoardMovementController;

public class ControllerPart implements ControlSPI {
    private KeyBoardController controller;

    public ControllerPart(){
        controller = new KeyBoardController();
    }
    @Override
    public Vector2 move(GameData gameData, Entity entity) {
        MovingPart movingPart = entity.getEntityPart(MovingPart.class);
        if(this.controller.getMovementController().isUp()){
            return new Vector2(0,movingPart.getMoveSpeed());
        }
        if(this.controller.getMovementController().isDown()){
            return new Vector2(0,-movingPart.getMoveSpeed());
        }
        if(this.controller.getMovementController().isLeft()){
            return new Vector2(-movingPart.getMoveSpeed(),0);
        }
        if(this.controller.getMovementController().isRight()){
            return new Vector2(movingPart.getMoveSpeed(),0);
        }
        return new Vector2(0,0);

    }

    @Override
    public ControlType getType() {
       return this.controller.getControlType();
    }

    @Override
    public KeyBoardMovementController getMovementController() {
        this.controller.setMovementController(new KeyBoardMovementController());
        return this.controller.getMovementController();
    }

    @Override
    public boolean isKeyPressed() {
        return this.controller.getMovementController().isKeyPressed();
    }

    @Override
    public boolean isKeyReleased() {
        return this.controller.getMovementController().isKeyReleased();
    }
}
