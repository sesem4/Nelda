package dk.sdu.sesem4.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerProcessingService implements ProcessingServiceSPI, PostProcessingServiceSPI {

    List<Texture> textures;
    Player player;

    @Override
    public void postProcess(GameData gameData, Priority priority) {

    }

    @Override
    public void process(GameData gameData, Priority priority) {
        this.player = new Player(EntityType.Player);
        player.setTextures(textures);
        player.setSprites(new Sprite(textures.get(0)));

    }

    public void loadEntity() {
        for (int i = 0; i <= 4 ; i++) {
            try {
                this.textures.add(new Texture(getFileNameForPlayer(i)));
            } catch (Exception e) {
                System.out.println("Could not load texture: " + getFileNameForPlayer(i));
            }
        }
    }

    private String getFileNameForPlayer(int i) {
        return getResourceDirectory() + "nelda/" + "Nelda" + i + ".png";
    }

    protected String getResourceDirectory() {
        return "Player/src/main/resources/";
    }

    public Entity getPlayer() {
        return new Player(EntityType.Player);
    }
}
