package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomMovementTest {
    private ControlType controlType;
    private RandomMovement randomMovement;
    private RandomMovementController randomMovementController;

    @Before
    public void setUp(){
        this.randomMovement = new RandomMovement();
    }

    @Test
    public void testGetType() {
        assertEquals(this.randomMovement.getType(), ControlType.AI);
    }

    @Test
    public void testGetMovementController() {
        setRandomMovementController((RandomMovementController) this.randomMovement.getMovementController());
        assertEquals(this.randomMovement.getMovementController(), this.randomMovementController);
    }

    public void setRandomMovementController(RandomMovementController randomMovementController){
        this.randomMovementController = randomMovementController;

    }
}