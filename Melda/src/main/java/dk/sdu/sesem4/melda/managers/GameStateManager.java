package dk.sdu.sesem4.melda.managers;

import dk.sdu.sesem4.melda.gamestate.GameState;
import dk.sdu.sesem4.melda.gamestate.PlayState;

public class GameStateManager {

    // current game state
    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 893746;

    public GameStateManager() {
        setState(PLAY);
    }

    public void setState(int state) {
        if(gameState != null) gameState.dispose();
        if(state == MENU) {
            // gameState = new MenuState(this);
        }
        if(state == PLAY) {
            gameState = new PlayState(this);
        }
    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
