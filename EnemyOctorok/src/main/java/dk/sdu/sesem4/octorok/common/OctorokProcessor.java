package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

abstract public class OctorokProcessor implements ProcessingServiceSPI {
	@Override
	public void process(GameData gameData, Priority priority) {

	}
}
