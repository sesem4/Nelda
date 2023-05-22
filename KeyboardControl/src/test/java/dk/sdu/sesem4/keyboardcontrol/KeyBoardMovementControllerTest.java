package dk.sdu.sesem4.keyboardcontrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
class KeyBoardMovementControllerTest {
	@Test
	void getMovementVerifyKeyCheck() {
		Input inputMock = mock(Input.class);
		Gdx.input = inputMock;

		GameData gameData = mock(GameData.class);
		Entity entity = mock(Entity.class);

		KeyBoardMovementController keyBoardMovementController = new KeyBoardMovementController();
		keyBoardMovementController.getMovement(gameData, entity);

		verify(inputMock).isKeyPressed(Input.Keys.W);
		verify(inputMock).isKeyPressed(Input.Keys.S);
		verify(inputMock).isKeyPressed(Input.Keys.A);
		verify(inputMock).isKeyPressed(Input.Keys.D);

		verify(inputMock).isKeyPressed(Input.Keys.UP);
		verify(inputMock).isKeyPressed(Input.Keys.DOWN);
		verify(inputMock).isKeyPressed(Input.Keys.LEFT);
		verify(inputMock).isKeyPressed(Input.Keys.RIGHT);
	}

	@Test
	void getMovementCheckDirectionForKeypress() {
		Input inputMock = mock(Input.class);
		Gdx.input = inputMock;

		GameData gameData = mock(GameData.class);
		Entity entity = mock(Entity.class);

		KeyBoardMovementController keyBoardMovementController = new KeyBoardMovementController();

		HashMap<Integer, Direction> inputMap = new HashMap<>();

		inputMap.put(Input.Keys.W, Direction.UP);
		inputMap.put(Input.Keys.UP, Direction.UP);
		inputMap.put(Input.Keys.S, Direction.DOWN);
		inputMap.put(Input.Keys.DOWN, Direction.DOWN);
		inputMap.put(Input.Keys.A, Direction.LEFT);
		inputMap.put(Input.Keys.LEFT, Direction.LEFT);
		inputMap.put(Input.Keys.D, Direction.RIGHT);
		inputMap.put(Input.Keys.RIGHT, Direction.RIGHT);

		inputMap.forEach((key, direction) -> {
			// Set input mock
			when(inputMock.isKeyPressed(key)).thenReturn(true);

			// Get direction
			Direction movementDirection = keyBoardMovementController.getMovement(gameData, entity);

			// Validate direction
			assertEquals(direction, movementDirection);

			// Clean up after test
			when(inputMock.isKeyPressed(key)).thenReturn(false);
		});
	}
}