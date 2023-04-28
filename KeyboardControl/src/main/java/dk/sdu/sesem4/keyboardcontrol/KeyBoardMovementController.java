package dk.sdu.sesem4.keyboardcontrol;



public class KeyBoardMovementController {
    private boolean up, down, left, right, space;

    public KeyBoardMovementController(){
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.space = false;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isSpace() {
        return space;
    }

    public boolean isKeyPressed(){
        return up || down || left || right || space;
    }

    public boolean isKeyReleased(){
        return !up && !down && !left && !right && !space;
    }

}
