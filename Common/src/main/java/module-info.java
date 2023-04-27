import dk.sdu.sesem4.common.SPI.*;

module Common {
	/**
	 * Required packages
	 */
	requires java.desktop;
	requires ShadedLibGDX;

	/**
	 * SPI's
	 */
	uses CombatSPI;
	uses ControlSPI;
	uses EnemySpawnerSPI;
	uses EventServiceSPI;
	uses PickupSPI;
	uses PluginServiceSPI;
	uses PostProcessingServiceSPI;
	uses ProcessingServiceSPI;

	/**
	 * Export of elements
	 */
	exports dk.sdu.sesem4.common.SPI;
	exports dk.sdu.sesem4.common.data.entity;
	exports dk.sdu.sesem4.common.data.EntityParts;
	exports dk.sdu.sesem4.common.data.CollisionParts;
	exports dk.sdu.sesem4.common.data.gamedata;
	exports dk.sdu.sesem4.common.data.math;
	exports dk.sdu.sesem4.common.data.process;
	exports dk.sdu.sesem4.common.data.weapon;
	exports dk.sdu.sesem4.common.data.rendering;
	exports dk.sdu.sesem4.common.util;
	exports dk.sdu.sesem4.common.event;
	exports dk.sdu.sesem4.common.event.events;
}