package dk.sdu.sesem4.common.data.gamedata;

import java.nio.file.Path;

public class GameWorld {
	Path map;

	public GameWorld(Path map) {
		this.map = map;
	}

	public Path getMap() {
		return map;
	}

	public void setMap(Path map) {
		this.map = map;
	}
}
