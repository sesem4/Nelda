package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.GameData;
import dk.sdu.sesem4.common.data.map.Map;
import dk.sdu.sesem4.common.data.map.Region;

public interface RegionSPI {
    /**
     * Map loads a region
     * <br><br>
     * Pre-condition: Map requires region to be loaded in.<br>
     * post-condition: Region object has been created for map.
     *
     * @param gameData The game data
     * @param map The current map that is being loaded
     * @return Region to load
     */
    Region loadRegion(GameData gameData, Map map);
}
