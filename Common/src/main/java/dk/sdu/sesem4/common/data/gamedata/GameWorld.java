package dk.sdu.sesem4.common.data.gamedata;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class GameWorld {
	TiledMap map;

	public GameWorld(TiledMap map) {
		this.map = map;
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
}
