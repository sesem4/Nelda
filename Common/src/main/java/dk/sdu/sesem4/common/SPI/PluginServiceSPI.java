package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.GameData;

public interface PluginServiceSPI {
    /**
     * Start game plugin
     * <br><br>
     * Pre-condition: The game has started and the plugin has not yet been started.<br>
     * post-condition: The plugin has handled the start.
     *
     * @param gameData The game data
     */
    void start(GameData gameData);

    /**
     * stop game plugin
     * <br><br>
     * Pre-condition: The game plugin is to be stopped and the game plugin has been started.<br>
     * post-condition: The plugin has handled the stop.
     *
     * @param gameData The game data
     */
    void stop(GameData gameData);
}
