package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest {
	private static GameData gameData;
    private Player player;
	private PlayerPlugin playerPlugin;

	@BeforeAll
	static void beforeAll() {
		gameData = new GameData();
	}

	@BeforeEach
	void beforeEach() {
		this.playerPlugin = new PlayerPlugin();
		this.playerPlugin.start(gameData);
		player = gameData.getGameEntities().getEntities(Player.class).get(0);
	}

	@AfterEach
	void afterEach() {
		this.playerPlugin.stop(gameData);
	}

	@Test
	@DisplayName("Test that the playerPlugin is added to GameData")
	void isPlayerPluginAddedToGameDataTest() {
		// Check if the number of Entities of Player.class is 1, which means that the playerPlugin is added to gameData
		assertEquals(1, gameData.getGameEntities().getEntities(Player.class).size());
	}

	@Test
	@DisplayName("Test if player has all needed parts")
	void entityPartOnPlayerTest() {
		assertNotNull(player.getEntityPart(PositionPart.class));
		assertNotNull(player.getEntityPart(MovingPart.class));
		assertNotNull(player.getEntityPart(LifePart.class));
		assertNotNull(player.getEntityPart(SpritePart.class));
	}

	@Test
	@DisplayName("Test that the playerPlugin can be removed from GameData")
	void deletePlayerTest() {
		this.playerPlugin.stop(gameData);
		// Check if the number of Entities of Player.class is 0, which means that the playerPlugin is removed from gameData
		assertEquals(0, gameData.getGameEntities().getEntities(Player.class).size());
	}

	@Test
	@DisplayName("Test that the paths from loadTextures() exist")
	void loadPathsTest() {
		List<Path> textures = playerPlugin.loadTextures();

		// Check if the number of paths is 5, which means that the playerPlugin load all paths
		assertEquals(5, textures.size());

		// Loop through all files, and check that the file exists
		for (Path texture : textures) {
			System.out.println(texture);
			assertTrue(Files.exists(texture));
		    new Texture(Gdx.files.local(filePath));
		}
	}
}