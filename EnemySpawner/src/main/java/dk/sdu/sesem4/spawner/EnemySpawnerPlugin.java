package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;

/**
 * Enemy spawner plugin, that handles the loading and unloading of the module
 *
 * @author Mikkel Albrechtsen
 */
public class EnemySpawnerPlugin implements PluginServiceSPI {
	@Override
	public void start(GameData gameData) {
		EventManager.getInstance().subscribe(MapTransitionDoneEventType.class, EnemySpawner.getInstance());
	}

	@Override
	public void stop(GameData gameData) {
		EventManager.getInstance().unsubscribe(MapTransitionDoneEventType.class, EnemySpawner.getInstance());
	}
}
