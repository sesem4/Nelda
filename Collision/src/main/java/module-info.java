import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;

module Collision {
	requires Common;

	provides PostProcessingServiceSPI with dk.sdu.sesem4.collision.CollisionSystem;
}