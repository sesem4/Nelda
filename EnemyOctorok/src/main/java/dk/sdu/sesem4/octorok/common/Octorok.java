package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Octorok extends Entity {
	public Octorok() {
		super(EntityType.Enemy);
	}
}
