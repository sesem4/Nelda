package dk.sdu.sesem4.mapcollision;

import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

public class MapCollision implements PostProcessingServiceSPI {

    @Override
    public void postProcess(GameData gameData, Priority priority) {
        System.out.println("jaja");
    }
}
